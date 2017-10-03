(function() {
    'use strict';
    

	angular
		.module('app.keycloakGrupo')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/KeycloakGrupo', {
            templateUrl:'app/keycloakGrupo/search.html',
            controller:'KeycloakGrupoController',
            controllerAs: 'vmKeycloakGrupo'
        })

        .when('/KeycloakGrupo/new', {
            templateUrl:'app/keycloakGrupo/detail.html',
            controller:'KeycloakGrupoController',
            controllerAs: 'vmKeycloakGrupo'
        })

        .when('/KeycloakGrupo/edit/:EntityId', {
            templateUrl:'app/keycloakGrupo/detail.html',
            controller:'KeycloakGrupoController',
            controllerAs: 'vmKeycloakGrupo'
        })

        .when('/KeycloakGrupo/view/:EntityId', {
            templateUrl:'app/keycloakGrupo/detail.html',
            controller:'KeycloakGrupoController',
            controllerAs: 'vmKeycloakGrupo'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();