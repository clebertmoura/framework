(function() {
    'use strict';
    

	angular
		.module('app.logradouro')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Logradouro', {
            templateUrl:'app/logradouro/search.html',
            controller:'LogradouroController',
            controllerAs: 'vmLogradouro'
        })

        .when('/Logradouro/new', {
            templateUrl:'app/logradouro/detail.html',
            controller:'LogradouroController',
            controllerAs: 'vmLogradouro'
        })

        .when('/Logradouro/edit/:EntityId', {
            templateUrl:'app/logradouro/detail.html',
            controller:'LogradouroController',
            controllerAs: 'vmLogradouro'
        })

        .when('/Logradouro/view/:EntityId', {
            templateUrl:'app/logradouro/detail.html',
            controller:'LogradouroController',
            controllerAs: 'vmLogradouro'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();