
import { throwError as observableThrowError,  Observable, forkJoin } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { MessageService } from '../../util/message.service';
import { catchError, map, flatMap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { BaseEntity } from '../entity/baseEntity';
import { LazyLoadEvent } from './dto/lazyloadevent';
import { PageRequestByExample } from './dto/paging';
import { FilterMetadata } from './paging/filtermetadata';
import { PageRequest } from './paging/pagerequest';
import { Order } from './paging/order';
import { PageResponse } from './paging/pageresponse';
import { isArray } from 'util';

/**
 * Classe abstrata de implementação de serviço.
 */
export abstract class EntityService<E extends BaseEntity> {

    protected options = { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) };

    constructor(
        protected http: HttpClient,
        protected messageService: MessageService,
        protected entityName: string) {
    }

    /**
     * Retorna a URl da API.
     */
    public getApiUrl(): string {
        return environment.baseUrl + '/' + this.entityName;
    }

    /**
     * Cria uma instancia da entidade a partir do json da resposta.
     * Deve ser implementado na classe concreta
     */
    public abstract newEntity(response?: any): E;
    /**
     * Cria uma lista de instancias da entidade a partir do json da resposta.
     * Deve ser implementado na classe concreta
     */
    public abstract newEntityList(response: any): E[];
    /**
     * Faz a requisicao dos relacionamentos.
     *
     * Deve ser sobrecarregado na classe concreta
     */
    protected flatMapEntityRelations(entity: E): Observable<any> {
        return null;
    }
    /**
     * Carrega os relacioamentos
     *
     * Deve ser sobrecarregado na classe concreta
     */
    protected mapEntityRelations(entity: E, response: any): E {
        return null;
    }

    /**
     * Recupera uma entidade pelo ID
     */
    getEntity(id: any): Observable<E> {
        return this.http.get(this.getApiUrl() + '/' + id)
            .pipe(
                map((response: any) => this.newEntity(response)),
                catchError(this.handleError)
            );
    }

    /**
     * Insere uma entidade
     */
    insert(entity: E): Observable<E> {
        let copy: any = entity;
        copy = { ...copy };
        this.removeAttributeRecursive(copy, '_json');
        return this.http.post<E>(this.getApiUrl(), copy, this.options)
            .pipe(
                map((response: E) => response),
                catchError(this.handleError)
            );
    }

    /**
     * Atualiza uma entidade
     */
    update(entity: E): Observable<E> {
        let copy: any = entity;
        copy = { ...copy };
        this.removeAttributeRecursive(copy, '_json');
        return this.http.put(this.getApiUrl() + '/' + entity.id, copy, this.options)
            .pipe(
                map((response: any) => this.newEntity(response)),
                catchError(this.handleError)
            );
    }

    /**
     * Retorna uma entidade de exemplo para uso como filtro.
     */
    getPageEntityExample(entity: E): E {
        const example: E = this.newEntity(null);
        return example;
    }

    /**
     * Consulta no backend uma página de registros da entidade.
     *
     * @param globalFilter Filtro global.
     * @param filters Mapa dos filtros
     * @param sortField Campo para ordenação.
     * @param sortOrder Ordenação: ASC ou DESC
     * @param pageIndex Indice da página
     * @param pageSize Quantidade máxima de resultados por página
     */
    public findPage(
        globalFilter: string,
        filters: { [s: string]: FilterMetadata },
        sortField: string,
        sortOrder: string,
        pageIndex: number,
        pageSize: number
      ): Observable<PageResponse<E>> {

        const pageRequest = new PageRequest();
        pageRequest.globalFilter = globalFilter;
        pageRequest.filters = filters;
        pageRequest.first = pageIndex * pageSize;
        pageRequest.rows = pageSize;
        pageRequest.sortField = sortField;
        pageRequest.sortOrder = (sortOrder != null) ? (sortOrder.toUpperCase() === 'ASC' ? Order.ASC : Order.DESC) : Order.ASC;

        return this.http.post(this.getApiUrl() + '/findPage', pageRequest, this.options)
          .pipe(
            map((response: any) => {
              const pageResponse = new PageResponse<E>();
              pageResponse.results = response.results;
              pageResponse.totalRegisters = response.totalRegisters;
              return pageResponse;
            })
          );
      }

    /**
     * Retorna uma entidade de exemplo para operacao de complete.
     */
    protected getCompleteExample(query: string): E {
        const example: E = this.newEntity(null);
        return example;
    }

    /**
     * Utilizado pelo componente de auto complete.
     *
     * @param query valor para filtragem.
     * @param sortField campo para ordenação.
     * @param sortOrder ordenação: ASC ou DESC.
     * @param maxRows qtde máxima de registros.
     */
    complete(query: string, sortField?: string, sortOrder?: string, maxRows?: number): Observable<E[]> {
        const pageRequest = new PageRequest();
        pageRequest.globalFilter = query;
        pageRequest.sortField = sortField;
        pageRequest.sortOrder = (sortOrder) ? (sortOrder.toUpperCase() === 'ASC' ? Order.ASC : Order.DESC) : null;
        pageRequest.first = 0;
        pageRequest.rows = (maxRows) ? maxRows : 10;

        return this.http.post(this.getApiUrl() + '/findPage', pageRequest, this.options)
            .pipe(
                map((response: any) => {
                    return response.results;
                })
            );
    }

    /**
     * Remove uma entidade pelo ID
     */
    delete(id: any) {
        return this.http.delete(this.getApiUrl() + '/' + id)
            .pipe(
                catchError(this.handleError)
            );
    }

    // sample method from angular doc
    protected handleError (httpError: HttpErrorResponse) {
        console.error('Ocorreu erro na requisição:', httpError);
        if (httpError.status >= 401 && httpError.status <= 403) {
            window.location.href = '/';
        }
        return observableThrowError(httpError);
    }

    /**
     * Remove recursivamente um atributo do objeto.
     * @param obj objeto.
     * @param attrName nome so atributo.
     */
    protected removeAttributeRecursive(obj: any, attrName: string) {
        for (const key in obj) {
            if (key === attrName) {
                delete obj[key];
            } else if (typeof obj[key] === 'object') {
                this.removeAttributeRecursive(obj[key], attrName);
            }
        }
    }
}
