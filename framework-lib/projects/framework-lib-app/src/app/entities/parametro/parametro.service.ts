import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { Parametro } from './parametro';
import { environment } from '../../../environments/environment';
import { map, catchError } from 'rxjs/operators';
import { EntityService, PageRequest, PageResponse } from 'framework-lib';

@Injectable({
  providedIn: 'root'
})
export class ParametroService extends EntityService<Parametro> {

  constructor(protected http: HttpClient) {
    super(http, 'Parametro');
    this.apiUrl = environment.baseUrl;
  }

  public newEntity(response?: any): Parametro {
    return new Parametro(response, 1);
  }

  public newEntityList(response: any): Parametro[] {
    return Parametro.toArray(response, 1);
  }

  protected flatMapEntityRelations(entity: Parametro): Observable<any[]> {
    const observables: Observable<any>[] = [];

    if (observables.length > 0) {
      return forkJoin(observables);
    } else {
      return ;
    }
  }


  protected mapEntityRelations(entity: Parametro, response: any): Parametro {
    return entity;
  }

    /**
    * ATENÇÃO: Este método foi criado para simular a resposta do backend. Não precisa implementá-lo no celerio.
    * @param entity 
    * @param response 
    */
    public findByPageRequest(pageRequest: PageRequest): Observable<PageResponse<Parametro>> {
      return this.http.get(this.getApiUrl() + '/findPage.json', this.options)
      .pipe(
        map((response: any) => {
          const pageResponse = new PageResponse<Parametro>();
          pageResponse.results = response.results;
          pageResponse.totalRegisters = response.totalRegisters;
          return pageResponse;
        })
    );
  }

  /**
   * ATENÇÃO: Este método foi criado para simular a resposta do backend. Não precisa implementá-lo no celerio.
   * @param id 
   */
  getEntity(id: any): Observable<Parametro> {
    return this.http.get(this.getApiUrl() + '/entity.json')
        .pipe(
            map((response: any) => this.newEntity(response)),
            catchError(this.handleError)
        );
  }


}
