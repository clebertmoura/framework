import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { KeycloakService, KeycloakAuthGuard } from 'keycloak-angular';
import { ErrorService } from '../core/errors/error.service';
import { constants } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class AppAuthGuard extends KeycloakAuthGuard {

  constructor(
    protected router: Router,
    protected keycloakAngular: KeycloakService,
    private errorService: ErrorService) {
    super(router, keycloakAngular);
  }

  isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise(async (resolve, reject) => {
      if (!this.authenticated) {
        this.keycloakAngular.login();
        return;
      }

      // Checa se o usuário possui a role
      // mínima (pilotojee7-frontend.logar.sistema) para acessar a aplicação
      let resultGrant = false;
      const requiredRoles = route.data.roles;
      if (!requiredRoles || requiredRoles.length === 0) {
        resultGrant = true;
      } else {
        if (!this.roles || this.roles.length === 0) {
          resultGrant = false;
        }
        for (const requiredRole of requiredRoles) {
          if (this.roles.indexOf(requiredRole) > -1) {
            resultGrant = true;
            break;
          }
        }
        if (!resultGrant) {
          this.errorService.showNotAuthorizedError();
        }
      }
      return resolve(resultGrant);
    });
  }

}
