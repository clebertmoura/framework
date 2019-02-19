(function() {
    'use strict';
    

	angular
		.module('app.keycloakUsuario')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/KeycloakUsuario', {
            templateUrl:'app/keycloakUsuario/search.html',
            controller:'KeycloakUsuarioController',
            controllerAs: 'vmKeycloakUsuario'
        })

        .when('/KeycloakUsuario/new', {
            templateUrl:'app/keycloakUsuario/detail.html',
            controller:'KeycloakUsuarioController',
            controllerAs: 'vmKeycloakUsuario'
        })

        .when('/KeycloakUsuario/edit/:EntityId', {
            templateUrl:'app/keycloakUsuario/detail.html',
            controller:'KeycloakUsuarioController',
            controllerAs: 'vmKeycloakUsuario'
        })

        .when('/KeycloakUsuario/view/:EntityId', {
            templateUrl:'app/keycloakUsuario/detail.html',
            controller:'KeycloakUsuarioController',
            controllerAs: 'vmKeycloakUsuario'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();