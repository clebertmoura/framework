import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError as observableThrowError,  Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

export abstract class AbstractEnumeratorsService {

    constructor(protected http: HttpClient) {
    }

    /**
     * Retorna a URl da API.
     */
    public abstract getApiUrl(): string;

    /**
     * Retorna uma coleção de valores do enumerator informado.
     * @param enumType tipo do enum
     */
    public getEnumValues(enumType: string): Observable<any[]> {
        return this.http.get<any[]>(this.getApiUrl() + enumType)
            .pipe(
                catchError(this.handleError)
            );
    }

    // sample method from angular doc
    protected handleError(httpError: HttpErrorResponse) {
        console.error('Ocorreu erro na requisição:', httpError);
        if (httpError.status >= 401 && httpError.status <= 403) {
            window.location.href = '/';
        }
        return observableThrowError(httpError);
    }

}
