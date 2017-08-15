'use strict';

var app = angular.module('app',['ngRoute','ngResource', 'app.shared', 
	'app.imagem', 
	'app.pais', 
	'app.uf', 
	'app.cidade', 
	'app.bairro',
	'app.modeloCelular',
	'app.celular',
	'app.cliente',
	'app.empresa',
	'app.endereco',
	'app.fabricante',
	'app.filial',
	'app.funcionario',
	'app.logradouro',
	'app.operadora',
	'app.pessoaContato',
	'app.pessoaFisica',
	'app.pessoaJuridica',
	'app.tipoFuncionario',
	'app.tipoLogradouro',
	'app.simcard'
	]);

/**
 * Carrega as informações do usuário autenticado pelo Keycloak.
 * Cria a factory chamada AuthenticationService que contém os dados da autenticação.
 */
angular.element(document).ready(function (Constants) {
    var auth = {};
    var keycloakAuth = new Keycloak('keycloak.json');
    auth.loggedIn = false;
    
    keycloakAuth.init({ onLoad: 'login-required' }).success(function () {
        keycloakAuth.loadUserInfo().success(function (userInfo) {
            auth.loggedIn = true;
            auth.authz = keycloakAuth;
            auth.userInfo = userInfo;
            auth.userInfo.permissions = [];
            if (auth.authz.tokenParsed) {
            	// carrega as roles globais
            	if (auth.authz.tokenParsed.realm_access) {
            		angular.forEach(auth.authz.tokenParsed.realm_access.roles, function(value, key) {
            			auth.userInfo.permissions.push(value);
            		}, auth.userInfo.permissions);
            	}
            	// carrega as roles associadas aos recursos
            	if (auth.authz.tokenParsed.resource_access) {
            		var resource_access = auth.authz.tokenParsed.resource_access;
            		angular.forEach(resource_access, function(value, key) {
                  	  if (value.roles && value.roles.length > 0) {
                  		angular.forEach(value.roles, function(value, key) {
                			auth.userInfo.permissions.push(value);
                		}, auth.userInfo.permissions);
                  	  }
                    }, resource_access);
            	}
            }
            var userToken = 'Bearer ' + auth.authz.token;
            //console.log(userInfo);
            //console.log('auth roles', auth.authz.realmAccess.roles);
            app.factory('AuthenticationService', function(Constants) {
            	localStorage.setItem(Constants.headerTokenName, userToken);
                return auth;
            });
            angular.bootstrap(document, ["app"]);
        });

    }).error(function () {
        window.location.reload();
    });
    
});

/**
 * Checa as permissões ao mudar de rota
 */
app.run(['$rootScope', '$location', 'AuthorizationFactory', 'Constants',
    function ($rootScope, $location, AuthorizationFactory, Constants) {
        var routeChangeRequiredAfterLogin = false,
            loginRedirectUrl;
        $rootScope.$on('$routeChangeStart', function (event, next) {
            var authorised;
            if (routeChangeRequiredAfterLogin && next.originalPath !== Constants.routes.login) {
                routeChangeRequiredAfterLogin = false;
                $location.path(loginRedirectUrl).replace();
            } else if (next.access !== undefined) {
                if(undefined !== AuthorizationFactory) {
                    authorised = AuthorizationFactory.authorize(next.access.loginRequired,
                        next.access.permissions,
                        next.access.permissionCheckType);
                    if (authorised === Constants.enums.authorised.loginRequired) {
                        routeChangeRequiredAfterLogin = true;
                        loginRedirectUrl = next.originalPath;
                        $location.path(Constants.routes.login);
                    } else if (authorised === Constants.enums.authorised.notAuthorised) {
                        //console.log('nao tem autorizacao!');
                        $location.path(Constants.routes.notAuthorised).replace();
                    }
                }
            }
        });
    }]);

app.config(['$httpProvider', function($httpProvider) {

    $httpProvider.interceptors.push('ErrorInterceptor');
    $httpProvider.interceptors.push('AuthInterceptor');

}]);



