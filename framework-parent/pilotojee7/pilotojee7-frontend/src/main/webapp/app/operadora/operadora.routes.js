(function() {
    'use strict';
    

	angular
		.module('app.operadora')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Operadora', {
            templateUrl:'app/operadora/search.html',
            controller:'OperadoraController',
            controllerAs: 'vmOperadora'
        })

        .when('/Operadora/new', {
            templateUrl:'app/operadora/detail.html',
            controller:'OperadoraController',
            controllerAs: 'vmOperadora'
        })

        .when('/Operadora/edit/:EntityId', {
            templateUrl:'app/operadora/detail.html',
            controller:'OperadoraController',
            controllerAs: 'vmOperadora'
        })

        .when('/Operadora/view/:EntityId', {
            templateUrl:'app/operadora/detail.html',
            controller:'OperadoraController',
            controllerAs: 'vmOperadora'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();