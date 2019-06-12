import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material';

import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';
import { HomeComponent } from './home/home.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MatSidenavModule,
    PagesRoutingModule,
    ReactiveFormsModule,
    SharedModule
  ],
  declarations: [
    PagesComponent,
    HomeComponent
  ]
})
export class PagesModule { }
