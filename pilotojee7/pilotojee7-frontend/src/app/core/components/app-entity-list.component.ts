import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BaseEntity, EntityService, MessageService, EntityListComponent } from 'framework-lib';
import { EnumeratorsService } from 'src/app/entities/enumerators.service';
import { ConfirmDeleteDialogComponent } from '../../shared/confirm-delete-dialog/confirm-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

export abstract class AppEntityListComponent<
  E extends BaseEntity,
  S extends EntityService<E>
> extends EntityListComponent<E, S> {
  constructor(
    protected router: Router,
    protected messageService: MessageService,
    protected confirmDeleteDialog: NgbModal,
    protected fb: FormBuilder,
    protected translate: TranslateService,
    protected enumeratorsService: EnumeratorsService,
    protected entityService: S,
    protected entityName: string
  ) {
    super(
      router,
      messageService,
      confirmDeleteDialog,
      fb,
      translate,
      enumeratorsService,
      entityService,
      entityName
    );
  }

  protected getModulePath(): string {
    return '/pages/entities';
  }

  /**
   * Executado ao clicar no botão Edit
   * @param entity registro selecionado.
   */
  public onClickRemove(entity: E): void {
    console.log('sobrecarga onClickRemove: ', entity);
    const dialogRef = this.confirmDeleteDialog.open(ConfirmDeleteDialogComponent);
    dialogRef.result.then((result) => {
      if (result === 'delete') {
        this.delete(entity);
      }
    }).catch((error) => {
      console.log(error);
    });
  }
}
