import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatToolbarModule,
  MatTabsModule,
  MatSelectModule,
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
  MatAutocompleteModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EntitiesModule } from '../entities.module';
import { ParametroListComponent } from './parametro-list/parametro-list.component';
import { ParametroDetailComponent } from './parametro-detail/parametro-detail.component';
import { ParametroAutocompleteComponent } from './parametro-autocomplete/parametro-autocomplete.component';
import { ParametroLineComponent } from './parametro-line/parametro-line.component';
import { ParametroRoutingModule } from './parametro-routing.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ParametroRoutingModule,

    // Angular Material
    MatToolbarModule,
    MatTabsModule,
    MatSelectModule,
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
    MatAutocompleteModule,
    EntitiesModule
  ],
  declarations: [
  ],
  exports: [
    ParametroListComponent,
    ParametroDetailComponent,
    ParametroLineComponent,
    ParametroAutocompleteComponent
  ]
})
export class ParametroModule {}
