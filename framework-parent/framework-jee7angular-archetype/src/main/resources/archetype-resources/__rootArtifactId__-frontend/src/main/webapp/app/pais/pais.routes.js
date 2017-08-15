(function() {
    'use strict';
    

	angular
		.module('app.pais')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Pais', {
            templateUrl:'app/pais/search.html',
            controller:'PaisController',
            controllerAs: 'vm'
        })

        .when('/Pais/new', {
            templateUrl:'app/pais/detail.html',
            controller:'PaisController',
            controllerAs: 'vm'
        })

        .when('/Pais/edit/:EntityId', {
            templateUrl:'app/pais/detail.html',
            controller:'PaisController',
            controllerAs: 'vm'
        })

        .when('/Pais/view/:EntityId', {
            templateUrl:'app/pais/detail.html',
            controller:'PaisController',
            controllerAs: 'vm'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();