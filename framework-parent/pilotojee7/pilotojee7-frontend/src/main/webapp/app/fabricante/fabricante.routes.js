(function() {
    'use strict';
    

	angular
		.module('app.fabricante')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Fabricante', {
            templateUrl:'app/fabricante/search.html',
            controller:'FabricanteController',
            controllerAs: 'vmFabricante'
        })

        .when('/Fabricante/new', {
            templateUrl:'app/fabricante/detail.html',
            controller:'FabricanteController',
            controllerAs: 'vmFabricante'
        })

        .when('/Fabricante/edit/:EntityId', {
            templateUrl:'app/fabricante/detail.html',
            controller:'FabricanteController',
            controllerAs: 'vmFabricante'
        })

        .when('/Fabricante/view/:EntityId', {
            templateUrl:'app/fabricante/detail.html',
            controller:'FabricanteController',
            controllerAs: 'vmFabricante'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();