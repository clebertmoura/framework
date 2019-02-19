(function() {
    'use strict';
    

	angular
		.module('app.bairro')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Bairro', {
            templateUrl:'app/bairro/search.html',
            controller:'BairroController',
            controllerAs: 'vmBairro'
        })

        .when('/Bairro/new', {
            templateUrl:'app/bairro/detail.html',
            controller:'BairroController',
            controllerAs: 'vmBairro'
        })

        .when('/Bairro/edit/:EntityId', {
            templateUrl:'app/bairro/detail.html',
            controller:'BairroController',
            controllerAs: 'vmBairro'
        })

        .when('/Bairro/view/:EntityId', {
            templateUrl:'app/bairro/detail.html',
            controller:'BairroController',
            controllerAs: 'vmBairro'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();