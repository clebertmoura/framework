import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

export class UserInfo {
  matricula: string;
  fullName: string;
  cpf: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    public keycloak: KeycloakService
  ) {}

  logout(): void {
    this.keycloak.logout('/pilotojee7-frontend');
  }

  loadUserInfo(): Promise<UserInfo> {
    return new Promise<UserInfo>((resolve, reject) => {
      if (this.keycloak.isLoggedIn) {
        this.keycloak
          .loadUserProfile()
          .then(userDetails => {
            const userProfile: UserInfo = this.parserUserProfile(userDetails);
            resolve(userProfile);
          })
          .catch(() => {
            reject(null);
          });
      } else {
        reject(null);
      }
    });
  }

  private parserUserProfile(userDetails: any): UserInfo {
    const user: UserInfo = new UserInfo();
    user.matricula = userDetails['attributes'].matricula[0];
    user.fullName = userDetails['attributes'].nomeCompleto[0];
    user.cpf = userDetails['attributes'].cpf[0];
    user.email = userDetails.email;
    return user;
  }
}
