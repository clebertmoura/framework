import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EntityDeleteDialogComponent } from 'framework-lib';

@Component({
  selector: 'app-confirm-delete-dialog',
  templateUrl: './confirm-delete-dialog.component.html',
  styleUrls: ['./confirm-delete-dialog.component.scss']
})
export class ConfirmDeleteDialogComponent extends EntityDeleteDialogComponent {

  constructor(public dialogRef: NgbActiveModal) {
    super(dialogRef);
  }

}