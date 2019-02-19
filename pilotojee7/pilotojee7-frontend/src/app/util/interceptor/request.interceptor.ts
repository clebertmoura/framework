import { Injectable } from '@angular/core';
import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest
} from '@angular/common/http';
import { SpinnerService } from '../../util/spinner/spinner.service';
import { KeycloakService } from 'keycloak-angular';
import { finalize } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    constructor(private auth: KeycloakService, private _spinnerService: SpinnerService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this._spinnerService.show();
        const authToken = this.auth.getToken() || '';
        request = request.clone({
          setHeaders: {
            'Authorization': 'Bearer ' + authToken
          }
        });
        return next.handle(request).pipe(
            finalize(() => {
                this._spinnerService.hide();
            })
        );
    }

}
