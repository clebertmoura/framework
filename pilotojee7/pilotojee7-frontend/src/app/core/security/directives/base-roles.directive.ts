import { TemplateRef, ViewContainerRef } from '@angular/core';

export abstract class BaseRolesDirective {

    constructor(
      private templateRef: TemplateRef<any>,
      private viewContainer: ViewContainerRef,
    ) {}

    abstract checkPermissions(): boolean;

    applyDirective(): void {
      const authorized: boolean = this.checkPermissions();
      if (authorized) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
    }

    parseRoles(roles: string | string[]): RoleResource[] {
      if (roles === null || roles === undefined) {
        return [];
      } else {
        const splittedRoles: string[] = roles instanceof Array ? roles :  roles.split(',');
        return splittedRoles.reduce((rolesResources, role) => {
          const roleResource = this.parseRoleResource(role.trim());
          if (roleResource !== null && roleResource !== undefined) {
            rolesResources.push(roleResource);
          }
          return rolesResources;
        }, []);
      }
    }

    parseRoleResource(roleResource: string): RoleResource {
      if (roleResource === null || roleResource === undefined) {
        return null;
      }

      const parts: string[] = roleResource.split(':', 2).map(part => part.trim());

      return this.createRoleResource(parts[0], parts.length > 1 ? parts[1] : undefined);
    }

    createRoleResource(role: string, resource?: string): RoleResource {
      if (role === null || role === undefined) {
        return null;
      }
      const roleResource = {role: role, resource: resource};

      return roleResource;
    }

}

export interface RoleResource {
  role: string;
  resource?: string;
}

