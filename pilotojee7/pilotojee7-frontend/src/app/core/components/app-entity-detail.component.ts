import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseEntity, EntityService, MessageService, EntityDetailComponent } from 'framework-lib';
import { EnumeratorsService } from 'src/app/entities/enumerators.service';

export abstract class AppEntityDetailComponent<
  E extends BaseEntity,
  S extends EntityService<E>
> extends EntityDetailComponent<E, S> {
  constructor(
    protected route: ActivatedRoute,
    protected router: Router,
    protected messageService: MessageService,
    protected location: Location,
    protected enumeratorsService: EnumeratorsService,
    protected entityService: S,
    protected entityName: string
  ) {
    super(
      route,
      router,
      messageService,
      location,
      enumeratorsService,
      entityService,
      entityName
    );
  }

  protected getModulePath(): string {
    return '/pages/entities';
  }
}
