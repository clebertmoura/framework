(function() {
    'use strict';
    

	angular
		.module('app.tipoLogradouro')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/TipoLogradouro', {
            templateUrl:'app/tipoLogradouro/search.html',
            controller:'TipoLogradouroController',
            controllerAs: 'vmTipoLogradouro'
        })

        .when('/TipoLogradouro/new', {
            templateUrl:'app/tipoLogradouro/detail.html',
            controller:'TipoLogradouroController',
            controllerAs: 'vmTipoLogradouro'
        })

        .when('/TipoLogradouro/edit/:EntityId', {
            templateUrl:'app/tipoLogradouro/detail.html',
            controller:'TipoLogradouroController',
            controllerAs: 'vmTipoLogradouro'
        })

        .when('/TipoLogradouro/view/:EntityId', {
            templateUrl:'app/tipoLogradouro/detail.html',
            controller:'TipoLogradouroController',
            controllerAs: 'vmTipoLogradouro'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();