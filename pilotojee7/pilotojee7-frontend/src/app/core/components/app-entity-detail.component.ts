import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseEntity, EntityService, MessageService, EntityDetailComponent } from 'framework-lib';
import { EnumeratorsService } from 'src/app/entities/enumerators.service';
import { Observable } from 'rxjs';
import { OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

export abstract class AppEntityDetailComponent<
  E extends BaseEntity,
  S extends EntityService<E>
> extends EntityDetailComponent<E, S> implements OnInit {
  constructor(
    protected route: ActivatedRoute,
    protected router: Router,
    protected messageService: MessageService,
    protected location: Location,
    protected translate: TranslateService,
    protected fb: FormBuilder,
    protected enumeratorsService: EnumeratorsService,
    protected entityService: S,
    protected entityName: string
  ) {
    super(
      route,
      router,
      messageService,
      location,
      translate,
      fb,
      enumeratorsService,
      entityService,
      entityName
    );
  }

  protected getModulePath(): string {
    return '/pages/entities';
  }

}
