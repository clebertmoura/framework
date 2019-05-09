export const environment = {
  production: false,
  baseUrl: 'http://localhost:8080/pilotojee7-backend/api',
  keycloakConfig: {
    url: 'http://localhost:8080/auth',
    realm: 'pilotojee7', // .ie: master
    clientId: 'pilotojee7-frontend' // .ie: account
  }
};
