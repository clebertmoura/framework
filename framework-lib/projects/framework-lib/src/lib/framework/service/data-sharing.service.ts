import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter, map, take } from 'rxjs/operators';

@Injectable()
export class DataSharingService {

  private dataMap: Map<string, any> = new Map<string, any>();
  private dataBehaviorSubject: BehaviorSubject<Map<string, any>> =
    new BehaviorSubject<Map<string, any>>(this.dataMap);
  private dataSource$: Observable<Map<string, any>> = this.dataBehaviorSubject.asObservable();

  constructor() { }

  setData(key: string, value: any): void {
    this.dataMap.set(key, value);
    this.dataBehaviorSubject.next(this.dataMap);
  }

  getData(key: string): any {
    return this.dataMap.get(key);
  }

  removeData(key: string): boolean {
    const result = this.dataMap.delete(key);
    this.dataBehaviorSubject.next(this.dataMap);
    return result;
  }

  onDataChange(key: string): Observable<any> {
    return this.dataSource$.pipe(
      filter(data => data.has(key)),
      map(data => data.get(key)),
      take(1)
    );
  }

}
