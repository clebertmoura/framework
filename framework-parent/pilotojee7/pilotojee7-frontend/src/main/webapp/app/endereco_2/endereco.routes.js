(function() {
    'use strict';
    

	angular
		.module('app.endereco')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Endereco', {
            templateUrl:'app/endereco/search.html',
            controller:'EnderecoController',
            controllerAs: 'vm'
        })

        .when('/Endereco/new', {
            templateUrl:'app/endereco/detail.html',
            controller:'EnderecoController',
            controllerAs: 'vm'
        })

        .when('/Endereco/edit/:EntityId', {
            templateUrl:'app/endereco/detail.html',
            controller:'EnderecoController',
            controllerAs: 'vm'
        })

        .when('/Endereco/view/:EntityId', {
            templateUrl:'app/endereco/detail.html',
            controller:'EnderecoController',
            controllerAs: 'vm'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();