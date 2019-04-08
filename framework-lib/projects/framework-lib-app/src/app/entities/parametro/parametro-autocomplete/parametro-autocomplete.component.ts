import { Component, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

import { Parametro } from '../parametro';
import { ParametroService } from '../parametro.service';
import { EntityCompleteComponent } from 'framework-lib';

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

  constructor(protected entityService: ParametroService) {
    super(entityService);
  }

}

