import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { Parametro } from './parametro';
import { EntityService } from '../../../../../framework-lib/src/lib/framework/service/entity.service';
import { environment } from '../../../environments/environment';
import { PageRequest } from '../../../../../framework-lib/src/lib/framework/service/paging/pagerequest';
import { PageResponse } from '../../../../../framework-lib/src/lib/framework/service/paging/pageresponse';
import { map, catchError } from 'rxjs/operators';

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

  getEntity(id: any): Observable<Parametro> {
    return this.http.get(this.getApiUrl() + '/entity.json')
        .pipe(
            map((response: any) => this.newEntity(response)),
            catchError(this.handleError)
        );
  }


}
