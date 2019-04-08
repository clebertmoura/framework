import { Parametro } from './parametro';
import { ParametroService } from './parametro.service';
import { MatPaginator, MatSort } from '@angular/material';
import { EntityDataSource } from 'framework-lib';

/**
 * Implementação da classe de serviço da entidade Parametro.
 */
export class ParametroDataSource extends EntityDataSource<Parametro, ParametroService> {

  constructor(public paginator: MatPaginator, public sort: MatSort, public entityService: ParametroService) {
    super(paginator, sort, entityService);
  }

}
