(function() {
    'use strict';
    

	angular
		.module('app.cidade')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Cidade', {
            templateUrl:'app/cidade/search.html',
            controller:'CidadeController',
            controllerAs: 'vm'
        })

        .when('/Cidade/new', {
            templateUrl:'app/cidade/detail.html',
            controller:'CidadeController',
            controllerAs: 'vm'
        })

        .when('/Cidade/edit/:EntityId', {
            templateUrl:'app/cidade/detail.html',
            controller:'CidadeController',
            controllerAs: 'vm'
        })

        .when('/Cidade/view/:EntityId', {
            templateUrl:'app/cidade/detail.html',
            controller:'CidadeController',
            controllerAs: 'vm'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();