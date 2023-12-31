import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { Observable, throwError } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { MatSort, MatPaginator } from '@angular/material';
import { BaseEntity } from '../entity/baseEntity';
import { EntityService } from './entity.service';
import { FilterMetadata } from './paging/filtermetadata';

export class EntityDataSource<E extends BaseEntity, S extends EntityService<E>> implements DataSource<E> {

  public itemsSubject = new BehaviorSubject<E[]>([]);
  public itemsCountSubject = new BehaviorSubject<number>(0);
  public loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingSubject.asObservable();
  public itemsCount$ = this.itemsCountSubject.asObservable();

  constructor(public paginator: MatPaginator, public sort: MatSort, public entityService: S) {
  }

  /**
   * Carrega uma página de resultados no datasource.
   *
   * @param globalFilter Filtro global.
   * @param filters Mapa dos filtros
   * @param sortField Campo para ordenação.
   * @param sortOrder Ordenação: ASC ou DESC
   * @param pageIndex Indice da página
   * @param pageSize Quantidade máxima de resultados por página
   */
  loadPage(
    globalFilter: string,
    filters: { [s: string]: FilterMetadata },
    sortField: string,
    sortOrder: string,
    pageIndex: number,
    pageSize: number
  ) {
    this.loadingSubject.next(true);
    this.entityService
      .findPage(globalFilter, filters, sortField, sortOrder, pageIndex, pageSize)
      .pipe(
        catchError(error => throwError(error)),
        finalize(() => this.loadingSubject.next(false))
      ).subscribe(response => {
        this.itemsCountSubject.next(response.totalRegisters);
        this.itemsSubject.next(response.results);
      });
  }

  connect(collectionViewer: CollectionViewer): Observable<E[]> {
    console.log('Connecting data source');
    return this.itemsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.itemsSubject.complete();
    this.itemsCountSubject.complete();
    this.loadingSubject.complete();
  }
}
