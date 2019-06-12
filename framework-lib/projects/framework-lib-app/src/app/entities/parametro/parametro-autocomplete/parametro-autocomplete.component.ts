//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity-autocomplete/entity-auto-complete.component.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { Component, OnInit, forwardRef } from '@angular/core';
import { EntityCompleteComponent, MessageService } from 'framework-lib';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { Parametro } from '../parametro';
import { ParametroService } from '../parametro.service';

export const PARAMETRO_AUTO_COMPLETE_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ParametroAutocompleteComponent),
  multi: true
};

@Component({
  selector: 'app-parametro-autocomplete',
  templateUrl: './parametro-autocomplete.component.html',
  styleUrls: ['./parametro-autocomplete.component.scss'],
  providers: [PARAMETRO_AUTO_COMPLETE_CONTROL_VALUE_ACCESSOR]
})
export class ParametroAutocompleteComponent extends EntityCompleteComponent<Parametro, ParametroService> {

  constructor(protected entityService: ParametroService, protected messageService: MessageService) {
    super(entityService, messageService);
  }

}

