import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { EnumeratorsService } from '../../enumerators.service';

import { Parametro } from '../parametro';
import { ParametroService } from '../parametro.service';
import { EntityDetailComponent } from '../../../../../../framework-tjpe-lib/src/lib/framework/components/entity-detail.component';
import { MessageService } from '../../../../../../framework-tjpe-lib/src/lib/framework/util/message.service';

@Component({
  selector: 'app-parametro-detail',
  templateUrl: './parametro-detail.component.html',
  styleUrls: ['./parametro-detail.component.scss']
})
export class ParametroDetailComponent extends EntityDetailComponent<Parametro, ParametroService> {



    tipoParametroValues: any[];

    constructor(protected route: ActivatedRoute, protected router: Router, protected messageService: MessageService,
                protected location: Location,
                protected enumeratorsService: EnumeratorsService,
                protected entityService: ParametroService) {
        super(route, router, messageService, location, enumeratorsService, entityService, 'Parametro');
    }

    protected loadEnumValues(): void {
        this.tipoParametroValues = [];
        this.enumeratorsService.getTipoParametroValues().subscribe(
            enumItems => this.tipoParametroValues = enumItems,
            error => this.messageService.error('Não foi possível carregar os valores do campo tipoParametro', error)
        );

    }

    protected loadEntityRelations(): void {
    }



}
