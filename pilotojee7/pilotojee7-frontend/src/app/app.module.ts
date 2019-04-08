import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatSidenavModule, MatSortModule } from '@angular/material';
import { MessageService } from 'framework-lib';
import { EnumeratorsService } from './entities/enumerators.service';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './shared/shared.module';
import { ConfirmDeleteDialogComponent } from './shared/confirm-delete-dialog/confirm-delete-dialog.component';
import { initializer } from './keycloak/app.init';
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { SpinnerService } from './util/spinner/spinner.service';
import { SpinnerComponent } from './util/spinner/spinner.component';
import { NgxMaskModule } from 'ngx-mask';

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
      timeOut: 0,
      extendedTimeOut: 0,
      maxOpened: 5,
      autoDismiss: true,
    }),
    HttpClientModule,
    KeycloakAngularModule,
    LayoutModule,
    FormsModule,
    NgbModule,
    NgxMaskModule.forRoot(),
    MatSidenavModule,
    MatSortModule,

    SharedModule,
    // MainModule
  ],
  providers: [
  EnumeratorsService,
  MessageService,
    KeycloakService,
    SpinnerService
    // ,
    // {
    //   provide: APP_INITIALIZER,
    //   useFactory: initializer,
    //   multi: true,
    //   deps: [KeycloakService]
    // }
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
