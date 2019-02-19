(function() {
    'use strict';
    

	angular
		.module('app.tipoFuncionario')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/TipoFuncionario', {
            templateUrl:'app/tipoFuncionario/search.html',
            controller:'TipoFuncionarioController',
            controllerAs: 'vmTipoFuncionario'
        })

        .when('/TipoFuncionario/new', {
            templateUrl:'app/tipoFuncionario/detail.html',
            controller:'TipoFuncionarioController',
            controllerAs: 'vmTipoFuncionario'
        })

        .when('/TipoFuncionario/edit/:EntityId', {
            templateUrl:'app/tipoFuncionario/detail.html',
            controller:'TipoFuncionarioController',
            controllerAs: 'vmTipoFuncionario'
        })

        .when('/TipoFuncionario/view/:EntityId', {
            templateUrl:'app/tipoFuncionario/detail.html',
            controller:'TipoFuncionarioController',
            controllerAs: 'vmTipoFuncionario'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();