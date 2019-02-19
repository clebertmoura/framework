import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { RouterModule } from '@angular/router';
import { ConfirmDeleteDialogComponent } from './confirm-delete-dialog/confirm-delete-dialog.component';
import { MatDialogModule, MatGridListModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    MatDialogModule,
    MatGridListModule
  ],
  exports: [BreadcrumbComponent, ConfirmDeleteDialogComponent],
  declarations: [BreadcrumbComponent, ConfirmDeleteDialogComponent],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ]
})
export class SharedModule { }
