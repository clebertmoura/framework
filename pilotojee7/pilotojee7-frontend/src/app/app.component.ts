import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessageService, Message } from 'framework-lib';
import { ToastMessageService } from './util/toast-message.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent implements OnDestroy {

  private messageSubscription: Subscription;

  constructor(private messageService: MessageService, private toastMessageService: ToastMessageService, translate: TranslateService) {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('pt-br');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('pt-br');

    this.messageSubscription = this.messageService.messageSource$.subscribe((message: Message) => {
        // exibe a mensagem via toast
        this.toastMessageService.show(message);
    });
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }

}
