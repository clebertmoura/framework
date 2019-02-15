import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { Message } from './message';

@Injectable()
export class MessageService {

    private messageSource = new Subject<Message>();

    messageSource$ = this.messageSource.asObservable();

    constructor(private toastr: ToastrService) {
    }

    /**
     * Exibe uma mensagem de informação
     * 
     * @param message mensagem
     * @param title titulo
     */
    info(message?: string, title?: string) {
        this.messageSource.next({severity: 'info', title: title, message: message});
        this.toastr.success(message, title);
        console.log('INFO: ' + title + ' MESSAGE: ' + message);
    }

    /**
     * Exibe uma mensagem de alerta
     * 
     * @param message mensagem
     * @param title titulo
     */
    warning(message?: string, title?: string) {
        this.messageSource.next({severity: 'warning', title: title, message: message});
        this.toastr.warning(message, title);
        console.warn('WARN: ' + title + ' MESSAGE: ' + message);
    }

    /**
     * Exibe uma mensagem de erro
     * 
     * @param message mensagem
     * @param title titulo
     */
    error(message?: string, title?: string) {
        this.messageSource.next({severity: 'error', title: title, message: message});
        this.toastr.error(message, title);
        console.error('ERROR: ' + title + ' MESSAGE: ' + message);
    }
}
