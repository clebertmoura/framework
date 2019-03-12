import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { NbThemeModule, NbMenuModule, NbLayoutModule, NbActionsModule, NbSidebarModule, NbSidebarService } from '@nebular/theme';

import { AppComponent } from './app.component';
import { LibTjpeFrameworkModule } from '../../../framework-tjpe-lib/src/lib/framework-tjpe-lib.module';
import { ParametroModule } from './entities/parametro/parametro.module';

import {
  MatToolbarModule,
  MatCheckboxModule,
  MatButtonModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatGridListModule,
  MatCardModule,
  MatMenuModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatFormFieldModule,
  MatDialogModule
} from '@angular/material';

import { OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { HttpClientModule } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MessageService } from '../../../framework-tjpe-lib/src/lib/framework/util/message.service';
import { NbMenuInternalService, NbMenuService } from '@nebular/theme/components/menu/menu.service';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,

    NbThemeModule.forRoot({ name: 'default' }),
    NbLayoutModule,

    NbMenuModule,
    NbActionsModule,
    NbSidebarModule,

    FontAwesomeModule,

    OwlDateTimeModule,
    OwlNativeDateTimeModule,

    MatToolbarModule,
    MatCheckboxModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatDialogModule,

    LibTjpeFrameworkModule,
    ParametroModule
  ],
  providers: [
    MessageService,
    NbSidebarService,
    NbMenuInternalService,
    NbMenuService,
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'pt'},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
