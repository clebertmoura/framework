import { OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from '../util/message.service';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { AbstractEnumeratorsService } from '../service/enumerators.service';
import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';
import { Error } from '../service/error/error';
import { ErrorLayer } from '../service/error/errorlayer';
import { Observable } from 'rxjs';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ServiceErrorEvent, ServiceErrorType } from './events/service-error.event';
import { TranslateService } from '@ngx-translate/core';

export abstract class EntityDetailComponent<E extends BaseEntity, S extends EntityService<E>> implements OnInit, OnDestroy {

  @Input()
  public header: string;

  public entity: E;

  public detailForm: FormGroup;

  public editMode = false;
  public viewMode = false;

  public paramsSubscription$: any;

  protected cacheEnums: any[] = [];

  @Input() public sub = false;

  @Output() public saveClicked = new EventEmitter<E>();
  @Output() public cancelClicked = new EventEmitter();

  @Output()
  public entityInserted = new EventEmitter<E>();
  @Output()
  public entityUpdated = new EventEmitter<E>();
  @Output()
  public serviceErrorEvent = new EventEmitter<ServiceErrorEvent>();

  protected entityDepth = 1;

  constructor(
    protected route: ActivatedRoute,
    protected router: Router,
    protected messageService: MessageService,
    protected location: Location,
    protected translate: TranslateService,
    protected fb: FormBuilder,
    protected enumeratorsService: AbstractEnumeratorsService,
    protected entityService: S,
    protected entityName: string) {
  }

  /**
   * Cria os FormGroup para manutenção da entidade.
   */
  protected createDetailFormGroup(): void {
    this.detailForm = this.fb.group(this.entity);
  }

  /**
   * Retorna a URL da API da Entidade.
   */
  public getApiUrl(): string {
    return this.entityService.getApiUrl();
  }

  /**
   * Responsável por fazer o carregamento dos campos enumerados.
   */
  protected loadEnumValues(): void {
  }

  /**
   * Responsável por fazer o carregamento da Entidade.
   */
  protected loadEntity(entity: E, depth?: number): void {
    this.entity = entity;
    if (this.entity._JSON) {
      this.entity.loadFromJson(this.entity._JSON, depth);
    }
    this.createDetailFormGroup();
    this.loadEnumValues();
    this.loadEntityRelations();
  }

  /**
   * Responsável por fazer o carregamento dos relacionamentos,
   */
  protected loadEntityRelations(): void {
  }

  ngOnInit() {
    if (this.sub) {
      return;
    }
    const obs = Observable.create(observer => {
      this.paramsSubscription$ = this.route.url.subscribe(segments => {
        const routeAction = segments[0].path;
        if (routeAction === 'new') {
          const entity = this.entityService.newEntity(null);
          observer.next(entity);
        } else {
          this.editMode = segments[0].path === 'edit';
          this.viewMode = segments[0].path === 'view';
          const id = segments[1].path;
          this.entityService.getEntity(id)
            .subscribe(
              entity => {
                observer.next(entity);
              },
              error => {
                observer.error(error);
              }
            );
        }
      });
    });
    obs.subscribe((entity: E) => {
      this.loadEntity(entity, this.entityDepth);
    }, (error: any) => {
      this.serviceErrorEvent.emit(new ServiceErrorEvent(ServiceErrorType.GET, error));
    });
  }

  ngOnDestroy() {
    if (!this.sub) {
      this.paramsSubscription$.unsubscribe();
    }
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

  protected getModulePath(): string {
    return '';
  }

  protected getEntityPath(): string {
    return this.getModulePath() + '/' + this.entityName.toLowerCase();
  }

  /**
   * Navega para tela de detalhe da entidade informada.
   */
  public gotoEntity(entityName: string, entityId: any) {
    this.router.navigate([this.getModulePath() + '/' + entityName.toLowerCase(), entityId]);
  }

  protected loadCompositeId(): void {
  }

  /**
   * Navega para pagina de edição de uma entidade.
   */
  public openEdit(entityName: string, entityId: any) {
    this.router.navigate([this.getModulePath() + '/' + entityName.toLowerCase() + '/edit', entityId]);
  }

  /**
   * Navega para pagina de visualização de uma entidade.
   */
  public openView(entityName: string, entityId: any) {
    this.router.navigate([this.getModulePath() + '/' + entityName.toLowerCase() + '/view', entityId]);
  }

  /**
   * Navega para pagina de listagem da entidade..
   */
  public openList() {
    this.router.navigate([this.getModulePath() + '/' + this.entityName.toLowerCase() + '/list']);
  }

  /**
   * Aplica os valores do formulário na entidade.
   */
  protected assignFormValues(): void {
    Object.assign(this.entity, this.detailForm.value);
  }

  /**
   * Executado ao clicar no botão Salvar
   */
  public onClickSave() {
    this.loadCompositeId();
    this.assignFormValues();
    if (!this.entity.id) {
      Object.assign(this.entity, this.detailForm.value);
      this.entityService.insert(this.entity).subscribe(
        entity => {
          this.entity = entity;
          this.entityInserted.emit(entity);
          let msg = '', title = '';
          this.translate.get('MESSAGE.ITEM_CREATED').subscribe((res:string) => {
            msg = res;
          });
          this.translate.get('LABEL.SUCCESS').subscribe((res:string) => {
            title = res;
          });
          this.messageService.info(msg, title);
          if (!this.sub) {
            this.openList();
          }
        },
        error => {
          this.serviceErrorEvent.emit(new ServiceErrorEvent(ServiceErrorType.INSERT, error));
        }
      );
    } else {
      this.entityService.update(this.entity).subscribe(
        entity => {
          this.entity = entity;
          this.entityUpdated.emit(entity);
          let msg = '', title = '';
          this.translate.get('MESSAGE.ITEM_SAVED').subscribe((res:string) => {
            msg = res;
          });
          this.translate.get('LABEL.SUCCESS').subscribe((res:string) => {
            title = res;
          });
          this.messageService.info(msg, title);
          if (!this.sub) {
            this.openList();
          }
        },
        error => {
          this.serviceErrorEvent.emit(new ServiceErrorEvent(ServiceErrorType.UPDATE, error));
        }
      );
    }
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
   * Trata o erro de requisição.
   * @param httpError erro
   */
  public handleErrorOnSave(httpError: HttpErrorResponse) {
    let errorMsg = '', errorTitle = '';
    this.translate.get('MESSAGE.ERROR_ON_SAVING').subscribe((res:string) => {
      errorMsg = res;
    });
    this.translate.get('MESSAGE.ERROR_WHEN_SAVING').subscribe((res:string) => {
      errorTitle = res;
    });
    this.handleHttpError(httpError, errorMsg, errorTitle);
  }

  /**
   * Executado ao clicar no botão Voltar
   */
  public onClickBack() {
    this.router.navigate([this.getModulePath() + '/' + this.entityName.toLowerCase(), 'list']);
  }

  /**
   * Executado ao clicar no botão Cancelar
   */
  public onClickCancel() {
    if (this.sub) {
      this.cancelClicked.emit('cancel');
    }
  }

}
