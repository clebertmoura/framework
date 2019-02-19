(function() {
    'use strict';
    

	angular
		.module('app.uf')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Uf', {
            templateUrl:'app/uf/search.html',
            controller:'UfController',
            controllerAs: 'vm'
        })

        .when('/Uf/new', {
            templateUrl:'app/uf/detail.html',
            controller:'UfController',
            controllerAs: 'vm'
        })

        .when('/Uf/edit/:EntityId', {
            templateUrl:'app/uf/detail.html',
            controller:'UfController',
            controllerAs: 'vm'
        })

        .when('/Uf/view/:EntityId', {
            templateUrl:'app/uf/detail.html',
            controller:'UfController',
            controllerAs: 'vm'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();