import { Directive, Input } from '@angular/core';
import { TemplateRef, ViewContainerRef } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { BaseRolesDirective, RoleResource } from './base-roles.directive';

@Directive({
    selector: '[hasAllRoles]'
})
export class HasAllRolesDirective extends BaseRolesDirective {

    private _roles: string | string[];

    constructor(
        private keycloakAngular: KeycloakService,
        templateRef: TemplateRef<any>,
        viewContainer: ViewContainerRef
      ) {
        super(templateRef, viewContainer);
    }

    @Input('hasAllRoles')
    set hasAllRoles(roles: string | string[]) {
        this._roles = roles;
        this.applyDirective();
    }

    checkPermissions(): boolean {
        const rolesResources: RoleResource[] = this.parseRoles(this._roles);
        for (const roleResource of rolesResources) {
            if (!this.keycloakAngular.isUserInRole(roleResource.role, roleResource.resource)) {
                return false;
            }
        }
        return true;
    }

}
