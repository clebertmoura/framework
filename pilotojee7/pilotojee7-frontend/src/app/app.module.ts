//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template celerio-pack-frontend:src/app/app.module.ts.p.vm
// Template is part of Open Source Project: https://github.com/jaxio/javaee-lab
//
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { LayoutModule } from '@angular/cdk/layout';
import {
  MatToolbarModule,
  MatCheckboxModule,
  MatButtonModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatGridListModule,
  MatCardModule,
  MatMenuModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatFormFieldModule,
  MatDialogModule
} from '@angular/material';
import { OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MessageService } from './util/message.service';
import { EnumeratorsService } from './entities/enumerators.service';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './shared/shared.module';
import { ConfirmDeleteDialogComponent } from './shared/confirm-delete-dialog/confirm-delete-dialog.component';
import { initializer } from './keycloak/app.init';
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { SpinnerService } from './util/spinner/spinner.service';
import { SpinnerComponent } from './util/spinner/spinner.component';

import { AgendaModule } from './entities/agenda/agenda.module';
import { AgendaService } from './entities/agenda/agenda.service';
import { AgendaDetalheModule } from './entities/agendadetalhe/agendadetalhe.module';
import { AgendaDetalheService } from './entities/agendadetalhe/agendadetalhe.service';
import { AgendaPeriodoModule } from './entities/agendaperiodo/agendaperiodo.module';
import { AgendaPeriodoService } from './entities/agendaperiodo/agendaperiodo.service';
import { AreaModule } from './entities/area/area.module';
import { AreaService } from './entities/area/area.service';
import { BloqueioModule } from './entities/bloqueio/bloqueio.module';
import { BloqueioService } from './entities/bloqueio/bloqueio.service';
import { ConsultaModule } from './entities/consulta/consulta.module';
import { ConsultaService } from './entities/consulta/consulta.service';
import { EspecialidadeModule } from './entities/especialidade/especialidade.module';
import { EspecialidadeService } from './entities/especialidade/especialidade.service';
import { PacienteModule } from './entities/paciente/paciente.module';
import { PacienteService } from './entities/paciente/paciente.service';
import { ParametroModule } from './entities/parametro/parametro.module';
import { ParametroService } from './entities/parametro/parametro.service';
import { ProfissionalSaudeModule } from './entities/profissionalsaude/profissionalsaude.module';
import { ProfissionalSaudeService } from './entities/profissionalsaude/profissionalsaude.service';
import { QualificacaoProfissionalModule } from './entities/qualificacaoprofissional/qualificacaoprofissional.module';
import { QualificacaoProfissionalService } from './entities/qualificacaoprofissional/qualificacaoprofissional.service';
import { StatusModule } from './entities/status/status.module';
import { StatusService } from './entities/status/status.service';
import { TipoAgendaModule } from './entities/tipoagenda/tipoagenda.module';
import { TipoAgendaService } from './entities/tipoagenda/tipoagenda.service';
import { TipoConsultaModule } from './entities/tipoconsulta/tipoconsulta.module';
import { TipoConsultaService } from './entities/tipoconsulta/tipoconsulta.service';

@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
    HomeComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({closeButton: true, progressBar: true}),
    HttpClientModule,
    LayoutModule,
    FormsModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatDialogModule,

    AgendaModule,
    AgendaDetalheModule,
    AgendaPeriodoModule,
    AreaModule,
    BloqueioModule,
    ConsultaModule,
    EspecialidadeModule,
    PacienteModule,
    ParametroModule,
    ProfissionalSaudeModule,
    QualificacaoProfissionalModule,
    StatusModule,
    TipoAgendaModule,
    TipoConsultaModule,
    KeycloakAngularModule,
    SharedModule
  ],
  providers: [
    AgendaService,
    AgendaDetalheService,
    AgendaPeriodoService,
    AreaService,
    BloqueioService,
    ConsultaService,
    EspecialidadeService,
    PacienteService,
    ParametroService,
    ProfissionalSaudeService,
    QualificacaoProfissionalService,
    StatusService,
    TipoAgendaService,
    TipoConsultaService,
	EnumeratorsService,
	MessageService,
    SpinnerService,
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'pt'},
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  entryComponents: [
    ConfirmDeleteDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }


