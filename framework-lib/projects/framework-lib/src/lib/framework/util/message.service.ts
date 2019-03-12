import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Message } from './message';

@Injectable()
export class MessageService {

    private messageSource = new Subject<Message>();

    messageSource$ = this.messageSource.asObservable();

    constructor() {
    }

    /**
     * Exibe uma mensagem de informação
     * 
     * @param message mensagem
     * @param title titulo
     */
    info(message?: string, title?: string) {
        this.messageSource.next({severity: 'info', title: title, message: message});
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
        console.error('ERROR: ' + title + ' MESSAGE: ' + message);
    }
}
