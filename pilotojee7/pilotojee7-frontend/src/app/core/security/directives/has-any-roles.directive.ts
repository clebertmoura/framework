import { Directive, Input } from '@angular/core';
import { TemplateRef, ViewContainerRef } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { BaseRolesDirective, RoleResource } from './base-roles.directive';

@Directive({
    selector: '[hasAnyRoles]'
})
export class HasAnyRolesDirective extends BaseRolesDirective {

    private _roles;

    constructor(
        private keycloakAngular: KeycloakService,
        templateRef: TemplateRef<any>,
        viewContainer: ViewContainerRef
      ) {
        super(templateRef, viewContainer);
    }

    @Input('hasAnyRoles')
    set hasAnyRoles(roles: string | string[]) {
        this._roles = roles;
        this.applyDirective();
    }

    checkPermissions(): boolean {
        const rolesResources: RoleResource[] = this.parseRoles(this._roles);
        for (const roleResource of rolesResources) {
            if (this.keycloakAngular.isUserInRole(roleResource.role, roleResource.resource)) {
                return true;
            }
        }
        return false;
    }

}
