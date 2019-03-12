import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ParametroListComponent } from './parametro-list/parametro-list.component';
import { ParametroDetailComponent } from './parametro-detail/parametro-detail.component';

const routes: Routes = [
  {
    path: 'parametro',
    data: {
      breadcrumb: 'Parametro'
    },
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: ParametroListComponent,
        data: {
          breadcrumb: 'Listagem'
        }
      },
      { path: 'new', component: ParametroDetailComponent,
        data: {
          breadcrumb: 'Novo'
        }
      },
      { path: 'edit/:id', component: ParametroDetailComponent,
        data: {
          breadcrumb: 'Editar'
        }
      },
      { path: 'view/:id', component: ParametroDetailComponent,
        data: {
          breadcrumb: 'Visualizar'
        }
      }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(
      routes
    ),
  ],
  exports: [RouterModule],
  declarations: []
})
export class ParametroRoutingModule {}
