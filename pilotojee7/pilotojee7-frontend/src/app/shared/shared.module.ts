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
  ],
  exports: [
    BreadcrumbComponent,
    ConfirmDeleteDialogComponent,
    ExpandMenuDirective,
    LoadingSpinnerComponent,
    DatetimePickerComponent
  ],
  declarations: [
    BreadcrumbComponent,
    ConfirmDeleteDialogComponent,
    ExpandMenuDirective,
    LoadingSpinnerComponent,
    DatetimePickerComponent
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ]
})
export class SharedModule { }
