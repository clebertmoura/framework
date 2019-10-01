import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { throwError, Observable, EMPTY } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { isArray } from 'util';

import { MessageService, Error, ErrorLayer } from 'framework-lib';
import { ErrorService } from '../error.service';

@Injectable()
export class ServerErrorsInterceptor implements HttpInterceptor {
  constructor(
    private messageService: MessageService,
    private errorService: ErrorService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse) {
          if (
            error.error instanceof ErrorEvent ||
            error.error instanceof ProgressEvent
          ) {
            this.errorService.showNoConnectionError();
          } else {
            if (error.status === 400) {
              // business e validation errors
              this.errorService.handleBusinessValidationError(error);
            } else if (error.status === 403) {
              // forbidden
              const errorResponse = error.error;
              let errorMessage = null;
              if (errorResponse && errorResponse.mensagem) {
                errorMessage = errorResponse.mensagem;
              }
              this.errorService.showNotAuthorizedError(errorMessage);
            } else {
              this.errorService.showUnexpectedError();
            }
          }
        }
        return throwError(error);
      })
    );
  }

}
