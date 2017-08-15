(function() {
    'use strict';
    

	angular
		.module('app.celular')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Celular', {
            templateUrl:'app/celular/search.html',
            controller:'CelularController',
            controllerAs: 'vmCelular'
        })

        .when('/Celular/new', {
            templateUrl:'app/celular/detail.html',
            controller:'CelularController',
            controllerAs: 'vmCelular'
        })

        .when('/Celular/edit/:EntityId', {
            templateUrl:'app/celular/detail.html',
            controller:'CelularController',
            controllerAs: 'vmCelular'
        })

        .when('/Celular/view/:EntityId', {
            templateUrl:'app/celular/detail.html',
            controller:'CelularController',
            controllerAs: 'vmCelular'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();