import {
  Input,
  Output,
  OnChanges,
  EventEmitter,
  SimpleChanges,
  ViewChild,
  ElementRef,
  AfterViewInit,
  OnInit
} from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../util/message.service';
import { MatSort } from '@angular/material';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { PageResponse } from '../service/paging/pageresponse';
import { EntityDataSource } from '../service/entity.datasource';
import { FilterMetadata } from '../service/paging/filtermetadata';
import { Paginator } from '../service/paging/paginator';
import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs/operators';
import { EntityDeleteDialogComponent } from './entity-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';
import { ErrorLayer } from '../service/error/errorlayer';
import { Error } from '../service/error/error';
import { AbstractEnumeratorsService } from '../service/enumerators.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Operator } from '../service/paging/operator';
import { ServiceErrorEvent, ServiceErrorType } from './events/service-error.event';
import { TranslateService } from '@ngx-translate/core';

export abstract class EntityListComponent<E extends BaseEntity, S extends EntityService<E>> implements OnChanges, OnInit, AfterViewInit {

  @Input()
  public header: string;

  @ViewChild(MatSort)
  sort: MatSort;
  @ViewChild('searchInput')
  searchInput: ElementRef;

  public searchForm: FormGroup;

  protected cacheEnums: any[] = [];

  public dataSource: EntityDataSource<E, S>;

  /** Colunas exibidas no datatable. Os IDs de colunas podem ser adicionados, removidos ou reordenados. */
  public displayedColumns = ['id', 'actions'];

  // When 'sub' is true, it means this list is used as a one-to-many list.
  // It belongs to a parent entity, as a result the addNew operation
  // must prefill the parent entity. The prefill is not done here, instead we
  // emit an event.
  // When 'sub' is false, we display basic search criterias
  @Input()
  public sub: boolean;
  @Output()
  public addNewClicked = new EventEmitter();

  @Output()
  public entityDeleted = new EventEmitter<E>();

  @Output()
  public serviceErrorEvent = new EventEmitter<ServiceErrorEvent>();

  public entityToDelete: E;

  public searchFields: { [s: string]: any };

  /**
   * Filtros
   */
  public filters: { [s: string]: FilterMetadata } = {};

  /**
   * Paginação
   */
  public paginator: Paginator = new Paginator();

  /**
   * quantidade máxima de páginas exibidas pelo componente de paginação.
   */
  public paginationMaxSize = 5;
  /**
   * rotação do componente de paginação
   */
  public paginationRotate = true;

  /**
   * Profundidade da serialização dos objetos. Padrão: 0
   * Pode ser sobrescrito na classe concreta.
   */
  protected loadPageDepth = 1;

  /**
   * Nome do EntityGraph a ser utilizado na consulta. Padrão: nenhum.
   * Pode ser sobrescrito na classe concreta.
   */
  protected loadPageEntityGraph: string;

  constructor(
    protected router: Router,
    protected messageService: MessageService,
    protected confirmDeleteDialog: NgbModal,
    protected fb: FormBuilder,
    protected translate: TranslateService,
    protected enumeratorsService: AbstractEnumeratorsService,
    protected entityService: S,
    protected entityName: string
  ) {
     this.searchForm = this.createSearchFormGroup();
     this.setupFilterFields();
     this.setupDatatableFields();
     this.setupEntityDataSource();
  }

  /**
   * Cria os FormGroup para os filtros de pesquisa.
   * Deve ser sobrescrito na classe concreta.
   */
  protected abstract createSearchFormGroup(): FormGroup;

  /**
   * Configura o EntityDataSource
   * Deve ser sobrescrito na classe concreta.
   */
  protected abstract setupEntityDataSource(): void;

  /**
   * Configura as colunas a serem exibidas no datatable.
   * Deve ser sobrescrito na classe concreta.
   */
  protected setupDatatableFields(): void {
    this.displayedColumns = [
      'actions'
    ];
  }

  /**
   * Inicializa um campo do filtro com o respectivo valor do campo no FormGroup.
   * @param filterName nome do filtro
   * @param fieldName nome do campo
   * @param operator operador
   */
  protected setupFilterField(filterName: string, fieldName: string, operator: Operator): void {
    this.filters[fieldName] = new FilterMetadata(this.searchForm.get(filterName).value, operator);
  }

  /**
   * Inicializa os campos do filtro com os valores dos campos no FormGroup.
   * Deve ser sobrescrito na classe concreta.
   */
  protected abstract setupFilterFields(): void;

  /**
   * Retorna o campo padrão de ordenação.
   */
  protected getSortField(): string {
    return 'id';
  }

  /**
   * Retorna a ordenação padrão.
   */
  protected getSortOrder(): string {
    return 'asc';
  }

  /**
   * Retorna o tamanho padrão da página.
   */
  protected getPageSize(): number {
    return 10;
  }

  /**
   * Inicializa o datatable.
   */
  ngOnInit() {
    this.paginator.pageSize = this.getPageSize();
    this.dataSource.loadPage(
      '',
      this.filters,
      this.getSortField(),
      this.getSortOrder(),
      0,
      this.paginator.pageSize,
      this.loadPageDepth,
      this.loadPageEntityGraph
    );
    // Load Enum Values
    this.loadEnumValues();
  }

  /**
   * Trata o erro de requisição.
   * @param httpError erro
   * @param message mensagem default
   * @param title titulo default
   */
  protected handleHttpError(httpError: HttpErrorResponse, message?: string, title?: string) {
    if (httpError.status === 400) {
      if (httpError.error && isArray(httpError.error)) {
        const errors = Error.toArray(httpError.error);
        for (const e of errors) {
          switch (e.layer) {
            case ErrorLayer.BEAN_VALIDATION:
              this.messageService.warning(e.propertyPath + ': ' + e.message);
              break;
            case ErrorLayer.BUSINESS:
              this.messageService.warning(e.message);
              break;
            default:
              this.messageService.error(e.message);
              break;
          }
        }
      } else {
        this.messageService.error(message, title);
      }
    } else {
      this.messageService.error(message, title);
    }
  }

  /**
   * Registra o evento 'keyup' do campo de pesquisa do datasource.
   */
  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadPage();
        })
      )
      .subscribe();

    this.sort.sortChange
      .pipe(tap(() => this.loadPage()))
      .subscribe();
  }

  public onPageChanges(page: number) {
    this.paginator.pageIndex = page - 1;
    this.loadPage();
  }

  /**
   * Faz o carregamento de uma página de resultados do datasource.
   */
  public loadPage() {
    this.dataSource.loadPage(
      this.searchInput.nativeElement.value,
      this.filters,
      this.sort.active,
      this.sort.direction,
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.loadPageDepth,
      this.loadPageEntityGraph
    );
  }

  /**
   * Responsável por fazer o carregamento dos campos enumerados.
   */
  protected loadEnumValues(): void {
  }

  /**
   * Retorna a label de um enum.
   *
   * @param enumName Nome do enum
   * @param enumValue Valor do enum.
   */
  public getEnumLabel(enumName: string, enumValue: string): string {
    const values = this.cacheEnums[enumName].filter(e => e.key === enumValue);
    return values.length > 0 ? values[0].label : enumValue;
  }

  /**
   * When used as a 'sub' component (to display one-to-many list), refreshes the table
   * content when the input changes.
   */
  ngOnChanges(changes: SimpleChanges) {
    this.loadPage();
  }

  /**
   * Executado ao clicar no botão Search
   */
  public onClickSearch(): void {
    if (!this.sub) {
      this.setupFilterFields();
      this.loadPage();
    }
  }

  protected getModulePath(): string {
    return '';
  }

  protected getEntityPath(): string {
    return this.getModulePath() + '/' + this.entityName.toLowerCase();
  }

  protected getEntityId(entity: E): any {
    return entity.id;
  }

  /**
   * Executado ao clicar no botão Edit
   * @param entity registro selecionado.
   */
  public onClickEdit(entity: E): void {
    console.log('onClickEdit: ', entity);
    this.router.navigate([this.getEntityPath() + '/edit/', entity.id]);
  }

  /**
   * Executado ao clicar no botão Edit
   * @param entity registro selecionado.
   */
  public onClickRemove(entity: E): void {
    const dialogRef = this.confirmDeleteDialog.open(EntityDeleteDialogComponent);
    dialogRef.result.then((result) => {
      if (result === 'delete') {
        this.delete(entity);
      }
    });
  }

  /**
   * Executado ao clicar no botão View
   * @param entity registro selecionado.
   */
  public onClickView(entity: E): void {
    console.log('onClickView: ', entity);
    this.router.navigate([this.getEntityPath() + '/view/', entity.id]);
  }

  /**
   * Executado ao clicar em um registro no datatable.
   * @param event registro selecionado.
   */
  public onRowSelect(event: any): void {
    const id = this.getEntityId(event.data);
    this.router.navigate([this.getEntityPath(), id]);
  }

  /**
   * Executado ao clicar no botão Add
   */
  public onClickNew(): void {
    if (this.sub) {
      this.addNewClicked.emit('addNew');
    } else {
      this.router.navigate([this.getEntityPath(), 'new']);
    }
  }

  /**
   * Remove uma entidade.
   * @param entity entidade a ser removida
   */
  public delete(entity: E): void {
    const id = this.getEntityId(entity);
    this.entityService.delete(id).subscribe(
      response => {
        this.entityDeleted.emit(entity);
        let msg = '', title = '';
        this.translate.get('MESSAGE.ITEM_REMOVED').subscribe((res:string) => {
          msg = res;
        });
        this.translate.get('LABEL.SUCCESS').subscribe((res:string) => {
          title = res;
        });
        this.messageService.info(msg, title);
        this.loadPage();
      },
      error => {
        this.serviceErrorEvent.emit(new ServiceErrorEvent(ServiceErrorType.DELETE, error));
      }
    );
  }

  public handleErrorOnDelete(httpError: HttpErrorResponse) {
    let errorMsg = '';
    this.translate.get('MESSAGE.ERROR_WHEN_SAVING').subscribe((res:string) => {
      errorMsg = res;
    });
    if (httpError.status === 400) {
      if (httpError.error && isArray(httpError.error)) {
        const errors = Error.toArray(httpError.error);
        for (const e of errors) {
          switch (e.layer) {
            case ErrorLayer.BEAN_VALIDATION:
              this.messageService.warning(e.propertyPath + ': ' + e.message);
              break;
            case ErrorLayer.BUSINESS:
              this.messageService.warning(e.message);
              break;
            default:
              this.messageService.error(e.message);
              break;
          }
        }
      } else {
        this.messageService.error(errorMsg);
      }
    } else {
      this.messageService.error(errorMsg);
    }
  }
}
