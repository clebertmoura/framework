import { Injectable, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { isArray } from 'util';

import { constants } from './../../app.constants';
import { MessageService, DataSharingService, Error, ErrorLayer } from 'framework-lib';


@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(
    private injector: Injector,
    private dataService: DataSharingService,
    private messageService: MessageService
  ) {}

  showErrorPage(
    showHomeOption: boolean,
    showLogoutOption: boolean,
    title?: string,
    message?: string
  ): void {
    const error = {
      title: title,
      message: message,
      showHomeOption: showHomeOption,
      showLogoutOption: showLogoutOption
    };

    this.dataService.setData(constants.errorDataKey, error);
    this.injector.get(Router).navigateByUrl('/error');
  }

  showNotAuthorizedError(message?: string): void {
    const errorMessage = message ? message : 'Você não tem permissão para acessar esta área do sistema.';
    this.showErrorPage(true, true, 'Acesso não autorizado', errorMessage);
  }

  showNoConnectionError(): void {
    this.showErrorPage(
      true,
      true,
      'Sem conexão',
      'Não foi possível estabelecer conexão com o servidor.'
    );
  }

  showUnexpectedError(): void {
    this.showErrorPage(
      true,
      true,
      'Erro inseperado',
      'ocorreu um erro inesperado. Entre em contato com a central de serviços.'
    );
  }

  public handleBusinessValidationError(httpError: HttpErrorResponse) {
    if (httpError.error && isArray(httpError.error)) {
      const errors = Error.toArray(httpError.error);
      for (let i = 0; i < errors.length; i++) {
        const e = errors[i];
        switch (e.layer) {
          case ErrorLayer.BEAN_VALIDATION:
            this.messageService.warning(e.propertyPath + ': ' + e.message);
            break;
          case ErrorLayer.BUSINESS:
            this.messageService.error(e.message);
            break;
          default:
            this.messageService.error(e.message);
            break;
        }
      }
    } else {
      this.messageService.error(
        'Ocorreu um erro ao realizar a operação.',
        'Erro!'
      );
    }
  }
}
