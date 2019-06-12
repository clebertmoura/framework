//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/entities/entities-routing.module.ts.p.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntitiesComponent } from './entities.component';

const routes: Routes = [
  {
    path: '',
    component: EntitiesComponent,
    children: [
      {
        path: 'agenda',
        loadChildren: './agenda/agenda.module#AgendaModule'
      },
      {
        path: 'agendadetalhe',
        loadChildren: './agendadetalhe/agendadetalhe.module#AgendaDetalheModule'
      },
      {
        path: 'agendaperiodo',
        loadChildren: './agendaperiodo/agendaperiodo.module#AgendaPeriodoModule'
      },
      {
        path: 'area',
        loadChildren: './area/area.module#AreaModule'
      },
      {
        path: 'bloqueio',
        loadChildren: './bloqueio/bloqueio.module#BloqueioModule'
      },
      {
        path: 'consulta',
        loadChildren: './consulta/consulta.module#ConsultaModule'
      },
      {
        path: 'especialidade',
        loadChildren: './especialidade/especialidade.module#EspecialidadeModule'
      },
      {
        path: 'paciente',
        loadChildren: './paciente/paciente.module#PacienteModule'
      },
      {
        path: 'parametro',
        loadChildren: './parametro/parametro.module#ParametroModule'
      },
      {
        path: 'profissionalsaude',
        loadChildren: './profissionalsaude/profissionalsaude.module#ProfissionalSaudeModule'
      },
      {
        path: 'qualificacaoprofissional',
        loadChildren: './qualificacaoprofissional/qualificacaoprofissional.module#QualificacaoProfissionalModule'
      },
      {
        path: 'status',
        loadChildren: './status/status.module#StatusModule'
      },
      {
        path: 'tipoagenda',
        loadChildren: './tipoagenda/tipoagenda.module#TipoAgendaModule'
      },
      {
        path: 'tipoconsulta',
        loadChildren: './tipoconsulta/tipoconsulta.module#TipoConsultaModule'
      },
    ],
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
export class EntitiesRoutingModule {}