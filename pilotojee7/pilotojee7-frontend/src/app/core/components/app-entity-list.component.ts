import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BaseEntity, EntityService, MessageService, EntityListComponent } from 'framework-lib';
import { EnumeratorsService } from 'src/app/entities/enumerators.service';

export abstract class AppEntityListComponent<
  E extends BaseEntity,
  S extends EntityService<E>
> extends EntityListComponent<E, S> {
  constructor(
    protected router: Router,
    protected messageService: MessageService,
    protected confirmDeleteDialog: NgbModal,
    protected enumeratorsService: EnumeratorsService,
    protected entityService: S,
    protected entityName: string
  ) {
    super(
      router,
      messageService,
      confirmDeleteDialog,
      enumeratorsService,
      entityService,
      entityName
    );
  }

  protected getModulePath(): string {
    return '/pages/entities';
  }
}
