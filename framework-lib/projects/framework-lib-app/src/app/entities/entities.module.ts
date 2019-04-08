import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  MatToolbarModule,
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
  MatTabsModule,
} from '@angular/material';

import { ParametroListComponent } from './parametro/parametro-list/parametro-list.component';
import { ParametroDetailComponent } from './parametro/parametro-detail/parametro-detail.component';
import { ParametroLineComponent } from './parametro/parametro-line/parametro-line.component';
import { ParametroAutocompleteComponent } from './parametro/parametro-autocomplete/parametro-autocomplete.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MessageService } from 'framework-lib';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,

    OwlDateTimeModule,
    OwlNativeDateTimeModule,

    FontAwesomeModule,

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
    MatAutocompleteModule
  ],
  declarations: [
    ParametroListComponent,
    ParametroDetailComponent,
    ParametroLineComponent,
    ParametroAutocompleteComponent
  ],
  exports: [
    ParametroListComponent,
    ParametroDetailComponent,
    ParametroLineComponent,
    ParametroAutocompleteComponent
  ],
  providers: [MessageService]
})
export class EntitiesModule {}
