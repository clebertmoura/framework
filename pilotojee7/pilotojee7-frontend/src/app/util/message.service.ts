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

    info(summary: string, detail: string) {
        this.messageSource.next({severity: 'info', summary: summary, detail: detail});
        this.toastr.success(detail, summary);
        console.log('INFO: ' + summary + ' DETAIL: ' + detail);
    }

    warning(summary: string, detail: string) {
        this.messageSource.next({severity: 'warning', summary: summary, detail: detail});
        this.toastr.warning(detail, summary);
        console.log('WARN: ' + summary + ' DETAIL: ' + detail);
    }

    error(summary: string, detail: string) {
        this.messageSource.next({severity: 'error', summary: summary, detail: detail});
        this.toastr.error('', summary);
        console.log('ERROR: ' + summary + ' DETAIL: ' + detail);
    }
}
