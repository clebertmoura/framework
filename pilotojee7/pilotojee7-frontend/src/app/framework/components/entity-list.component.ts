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
import { MessageService } from '../../util/message.service';
import { MatDialog, MatPaginator, MatSort } from '@angular/material';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { PageResponse } from '../service/dto/paging';
import { EntityDataSource } from '../service/entity.datasource';
import { FilterMetadata } from '../service/paging/filtermetadata';
import { fromEvent, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs/operators';
import { ConfirmDeleteDialogComponent } from '../../shared/confirm-delete-dialog/confirm-delete-dialog.component';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';
import { ErrorLayer } from '../service/error/errorlayer';
import { Error } from '../service/error/error';

export abstract class EntityListComponent<E extends BaseEntity, S extends EntityService<E>> implements OnChanges, OnInit, AfterViewInit {

  @Input()
  public header: string;

  @ViewChild(MatPaginator)
  paginator: MatPaginator;
  @ViewChild(MatSort)
  sort: MatSort;
  @ViewChild('searchInput')
  searchInput: ElementRef;

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

  public entityToDelete: E;

  public searchFields: { [s: string]: any };

  // filtros
  public filters: { [s: string]: FilterMetadata } = {};

  // list is paginated
  public currentPage: PageResponse<E> = new PageResponse<E>(0, 0, []);

  constructor(
    protected router: Router,
    protected messageService: MessageService,
    protected confirmDeleteDialog: MatDialog,
    protected entityService: S,
    protected entityName: string
  ) {}

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
      this.paginator.pageSize
    );
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

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(tap(() => this.loadPage()))
      .subscribe();
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
      this.paginator.pageSize
    );
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
    console.log('onClickRemove: ', entity);
    const dialogRef = this.confirmDeleteDialog.open(
      ConfirmDeleteDialogComponent
    );
    dialogRef.afterClosed().subscribe(result => {
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
        this.currentPage.remove(entity);
        this.messageService.info('Sucesso!', 'O registro foi removido.');
        this.loadPage();
      },
      error => this.handleErrorOnDelete(error)
    );
  }

  public handleErrorOnDelete(httpError: HttpErrorResponse) {
    if (httpError.status === 400) {
        if (httpError.error && isArray(httpError.error)) {
            const errors = Error.toArray(httpError.error);
            for (let i = 0; i < errors.length; i++) {
                const e = errors[i];
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
            this.messageService.error('Erro ao salvar!');
        }
    } else {
        this.messageService.error('Erro ao salvar!');
    }
}
}
