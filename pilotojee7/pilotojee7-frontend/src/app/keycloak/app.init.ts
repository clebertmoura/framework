import { KeycloakService } from 'keycloak-angular';

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: 'https://localhost:8443/auth/', // .ie: http://localhost:8080/auth/
            realm: 'master', // .ie: master
            clientId: 'pilotojee7-frontend' // .ie: account
          },
          initOptions: {
            onLoad: 'login-required',
            checkLoginIframe: false
          },
          enableBearerInterceptor: true,
          bearerExcludedUrls: [
            '/assets',
            '/clients/public'
          ]
        });
        resolve();
      } catch (error) {
        console.error(error);
        reject(error);
      }
    });
  };
}
