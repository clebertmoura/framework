import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessageService, Message } from 'framework-lib';
import { ToastMessageService } from './util/toast-message.service';

@Component({
  selector: 'app-root',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent implements OnDestroy {

  private messageSubscription: Subscription;

  constructor(private messageService: MessageService, private toastMessageService: ToastMessageService) {
    this.messageSubscription = this.messageService.messageSource$.subscribe((message: Message) => {
        // exibe a mensagem via toast
        this.toastMessageService.show(message);
    });
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }

}
