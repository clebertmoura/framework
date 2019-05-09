import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AbstractEnumeratorsService } from 'framework-lib';
import { environment } from '../../environments/environment';
@Injectable({
    providedIn: 'root'
})
export class EnumeratorsService extends AbstractEnumeratorsService {

    constructor(http: HttpClient) {
        super(http);
    }

    public getApiUrl(): string {
        return environment.baseUrl + '/v1/enums/';
    }

    /**
     * Retorna a coleção de valores do enumerator: SimNao
     */
    public getSimNaoValues(): Observable<any[]> {
        return this.getEnumValues('SimNao');
    }

    /**
     * Retorna a coleção de valores do enumerator: Status
     */
    public getStatusValues(): Observable<any[]> {
        return this.getEnumValues('Status');
    }

    /**
     * Retorna a coleção de valores do enumerator: TipoParametro
     */
    public getTipoParametroValues(): Observable<any[]> {
        return this.getEnumValues('TipoParametro');
    }

}
