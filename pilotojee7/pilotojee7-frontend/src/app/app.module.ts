import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatSidenavModule, MatSortModule } from '@angular/material';
import { MessageService } from 'framework-lib';
import { EnumeratorsService } from './entities/enumerators.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './shared/shared.module';
import { ConfirmDeleteDialogComponent } from './shared/confirm-delete-dialog/confirm-delete-dialog.component';
import { initializer } from './keycloak/app.init';
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { SpinnerService } from './util/spinner/spinner.service';
import { SpinnerComponent } from './util/spinner/spinner.component';
import { ToastMessageService } from './util/toast-message.service';
import { NgxMaskModule } from 'ngx-mask';
// import ngx-translate and the http loader
import {TranslateCompiler, TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateMessageFormatCompiler} from 'ngx-translate-messageformat-compiler';

import { DataSharingService } from 'framework-lib';
import { ErrorsModule } from './core/errors/errors.module';
import { SecurityModule } from './core/security/security.module';
import { ErrorService } from './core/errors/error.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
// export function createTranslateLoader(httpClient: HttpClient) {
//  return new TranslateHttpLoader(httpClient, './assets/i18n/', '.json');
  // return new TranslateHttpLoader(httpClient);
// }

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      closeButton: true,
      positionClass: 'toast-top-right',
      maxOpened: 5,
      autoDismiss: true,
    }),
    HttpClientModule,
    KeycloakAngularModule,
    LayoutModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgxMaskModule.forRoot(),
    MatSidenavModule,
    MatSortModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      },
      // compiler configuration
      compiler: {
        provide: TranslateCompiler,
        useClass: TranslateMessageFormatCompiler
      }
    }),
    SharedModule,
    ErrorsModule,
    SecurityModule
  ],
  providers: [
    EnumeratorsService,
    MessageService,
    KeycloakService,
    SpinnerService,
    ToastrService,
    ToastMessageService,
    DataSharingService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService, ErrorService]
    }
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
