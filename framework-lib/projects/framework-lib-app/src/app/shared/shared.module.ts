import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { RouterModule } from '@angular/router';
import { ConfirmDeleteDialogComponent } from './confirm-delete-dialog/confirm-delete-dialog.component';
import { MatDialogModule, MatGridListModule } from '@angular/material';
import { ExpandMenuDirective } from './expand-menu.directive';
import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { DatetimePickerComponent } from './datetime-picker/datetime-picker.component';
import { NgxMaskModule } from 'ngx-mask';
import { TranslateModule } from '@ngx-translate/core';
import { DisableControlDirective } from './disabled-control.directive';


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatGridListModule,
    NgxMaskModule.forChild(),
    TranslateModule
  ],
  exports: [
    BreadcrumbComponent,
    ConfirmDeleteDialogComponent,
    ExpandMenuDirective,
    DisableControlDirective,
    LoadingSpinnerComponent,
    DatetimePickerComponent,
    TranslateModule
  ],
  declarations: [
    BreadcrumbComponent,
    ConfirmDeleteDialogComponent,
    ExpandMenuDirective,
    DisableControlDirective,
    LoadingSpinnerComponent,
    DatetimePickerComponent
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ]
})
export class SharedModule { }
