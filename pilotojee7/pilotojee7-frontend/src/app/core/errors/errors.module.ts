import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { ErrorRoutingModule } from './errors-routing.module';
import { ServerErrorsInterceptor } from './interceptors/server-errors.interceptor';
import { ErrorComponent } from './error.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    //ErrorRoutingModule,
  ],
  declarations: [
    ErrorComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ServerErrorsInterceptor,
      multi: true
    }
  ]
})
export class ErrorsModule { }
