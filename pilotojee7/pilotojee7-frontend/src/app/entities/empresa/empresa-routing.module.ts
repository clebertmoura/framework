//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entity/entity-routing.module.ts.e.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpresaListComponent } from './empresa-list/empresa-list.component';
import { EmpresaDetailComponent } from './empresa-detail/empresa-detail.component';

const routes: Routes = [
  {
    path: 'empresa',
    data: {
      breadcrumb: 'Empresa'
    },
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: EmpresaListComponent,
        data: {
          breadcrumb: 'Listagem'
        }
      },
      { path: 'new', component: EmpresaDetailComponent,
        data: {
          breadcrumb: 'Novo'
        }
      },
      { path: 'edit/:id', component: EmpresaDetailComponent,
        data: {
          breadcrumb: 'Editar'
        }
      },
      { path: 'view/:id', component: EmpresaDetailComponent,
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
export class EmpresaRoutingModule {}
