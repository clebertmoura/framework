import { OnInit, OnDestroy, Input, Output, EventEmitter} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService} from '../util/message.service';
import { EntityService } from '../service/entity.service';
import { BaseEntity } from '../entity/baseEntity';
import { AbstractEnumeratorsService } from '../service/enumerators.service';
import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';
import { Error } from '../service/error/error';
import { ErrorLayer } from '../service/error/errorlayer';
import { Observable } from 'rxjs';

export abstract class EntityDetailComponent<E extends BaseEntity, S extends EntityService<E>> implements OnInit, OnDestroy {

    @Input()
    public header: string;

    public entity: E;

    public editMode = false;
    public viewMode = false;

    public params_subscription: any;

    protected cacheEnums: any[] = [];

    @Input() public sub = false;

    @Output() public saveClicked = new EventEmitter<E>();
    @Output() public cancelClicked = new EventEmitter();

    constructor(protected route: ActivatedRoute,
        protected router: Router,
        protected messageService: MessageService,
        protected location: Location,
        protected enumeratorsService: AbstractEnumeratorsService,
        protected entityService: S,
        protected entityName: string) {
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
     * Responsável por fazer o carregamento dos relacionamentos,
     */
    protected loadEntityRelations(): void {
    }

    ngOnInit() {
        if (this.sub) {
            return;
        }
        const obs = Observable.create(observer => {
            this.params_subscription = this.route.url.subscribe(segments => {
                const routeAction = segments[0].path;
                if (routeAction === 'new') {
                    this.entity = this.entityService.newEntity(null);
                    observer.next(this.entity);
                } else {
                    this.editMode = segments[0].path === 'edit';
                    this.viewMode = segments[0].path === 'view';
                    const id = segments[1].path;
                    this.entityService.getEntity(id)
                        .subscribe(
                            entity => {
                                this.entity = entity;
                                observer.next(this.entity);
                            },
                            error => {
                                this.messageService.error('ngOnInit error', error);
                                observer.error(error);
                            }
                        );
                }
            });
        });
        obs.subscribe(entity => {
            // Load Enum Values
            this.loadEnumValues();
            this.loadEntityRelations();
        }, error => {
            this.messageService.error('ngOnInit error', error);
        });
    }

    ngOnDestroy() {
        if (!this.sub) {
            this.params_subscription.unsubscribe();
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
     * Executado ao clicar no botão Salvar
     */
    public onClickSave() {
        this.loadCompositeId();
        let obs = null;
        if (!this.entity.id) {
            obs = this.entityService.insert(this.entity);
        } else {
            obs = this.entityService.update(this.entity);
        }
        obs.subscribe(
            entity => {
                this.entity = entity;
                if (this.sub) {
                    this.saveClicked.emit(this.entity);
                    this.messageService.info('Saved OK and msg emitted', 'Angular Rocks!');
                } else {
                    this.messageService.info('Registro salvo com sucesso!', 'Sucesso');
                    this.openList();
                }
            },
            error => this.handleErrorOnSave(error)
        );
    }

    public handleErrorOnSave(httpError: HttpErrorResponse) {
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
            this.messageService.info('Cancel clicked and msg emitted', 'Angular Rocks!');
        }
    }

}
