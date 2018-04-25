(function() {
    'use strict';
    

	angular
		.module('app.cliente')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Cliente', {
            templateUrl:'app/cliente/search.html',
            controller:'ClienteController',
            controllerAs: 'vmCliente'
        })

        .when('/Cliente/new', {
            templateUrl:'app/cliente/detail.html',
            controller:'ClienteController',
            controllerAs: 'vmCliente'
        })

        .when('/Cliente/edit/:EntityId', {
            templateUrl:'app/cliente/detail.html',
            controller:'ClienteController',
            controllerAs: 'vmCliente'
        })

        .when('/Cliente/view/:EntityId', {
            templateUrl:'app/cliente/detail.html',
            controller:'ClienteController',
            controllerAs: 'vmCliente'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();