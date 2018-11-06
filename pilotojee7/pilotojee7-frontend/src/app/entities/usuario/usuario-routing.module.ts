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
import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { UsuarioDetailComponent } from './usuario-detail/usuario-detail.component';

const routes: Routes = [
  {
    path: 'usuario',
    data: {
      breadcrumb: 'Usuario'
    },
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: UsuarioListComponent,
        data: {
          breadcrumb: 'Listagem'
        }
      },
      { path: 'new', component: UsuarioDetailComponent,
        data: {
          breadcrumb: 'Novo'
        }
      },
      { path: 'edit/:id', component: UsuarioDetailComponent,
        data: {
          breadcrumb: 'Editar'
        }
      },
      { path: 'view/:id', component: UsuarioDetailComponent,
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
export class UsuarioRoutingModule {}
