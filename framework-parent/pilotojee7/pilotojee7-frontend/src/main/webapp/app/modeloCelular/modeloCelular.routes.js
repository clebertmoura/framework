(function() {
    'use strict';
    

	angular
		.module('app.modeloCelular')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/ModeloCelular', {
            templateUrl:'app/modeloCelular/search.html',
            controller:'ModeloCelularController',
            controllerAs: 'vmModeloCelular'
        })

        .when('/ModeloCelular/new', {
            templateUrl:'app/modeloCelular/detail.html',
            controller:'ModeloCelularController',
            controllerAs: 'vmModeloCelular'
        })

        .when('/ModeloCelular/edit/:EntityId', {
            templateUrl:'app/modeloCelular/detail.html',
            controller:'ModeloCelularController',
            controllerAs: 'vmModeloCelular'
        })

        .when('/ModeloCelular/view/:EntityId', {
            templateUrl:'app/modeloCelular/detail.html',
            controller:'ModeloCelularController',
            controllerAs: 'vmModeloCelular'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();