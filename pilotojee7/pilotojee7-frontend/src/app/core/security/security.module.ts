import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HasAnyRolesDirective } from './directives/has-any-roles.directive';
import { HasAllRolesDirective } from './directives/has-all-roles.directive';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
  ],
  declarations: [
    HasAnyRolesDirective,
    HasAllRolesDirective
  ],
  exports: [
    HasAnyRolesDirective,
    HasAllRolesDirective
  ]
})
export class SecurityModule { }
