import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { Parametro } from '../parametro';
import { ParametroService } from '../parametro.service';
import { ParametroDataSource } from '../parametro.datasource';
import { EntityListComponent } from '../../../../../../framework-lib/src/lib/framework/components/entity-list.component';
import { MessageService } from '../../../../../../framework-lib/src/lib/framework/util/message.service';
import { FilterMetadata } from 'projects/framework-lib/src/lib/framework/service/paging/filtermetadata';
import { Operator } from '../../../../../../framework-lib/src/lib/framework/service/paging/operator';

@Component({
  selector: 'app-parametro-list',
  templateUrl: './parametro-list.component.html',
  styleUrls: ['./parametro-list.component.scss']
})
export class ParametroListComponent extends EntityListComponent<Parametro, ParametroService> {


    constructor(protected router: Router,
                protected messageService: MessageService,
                protected confirmDeleteDialog: MatDialog,
                protected entityService: ParametroService) {
        super(router, messageService, confirmDeleteDialog, entityService, 'Parametro');
        // inicialização do dataSource
        this.dataSource = new ParametroDataSource(this.paginator, this.sort, this.entityService);
        // configura as colunas exibidas no datatable.
        this.displayedColumns = [
            'descricao',
            'valor',
            'chave',
            'dataHoraAlteracao',
            'tipoParametro',
            'actions'
        ];
        // configura os campos de filtro
        this.filters['descricao'] = new FilterMetadata(null, Operator.LI);
        this.filters['valor'] = new FilterMetadata(null, Operator.LI);
        this.filters['chave'] = new FilterMetadata(null, Operator.LI);
        this.filters['dataHoraAlteracao'] = new FilterMetadata(null, Operator.EQ);
        this.filters['tipoParametro'] = new FilterMetadata(null, Operator.LI);
    }

}
