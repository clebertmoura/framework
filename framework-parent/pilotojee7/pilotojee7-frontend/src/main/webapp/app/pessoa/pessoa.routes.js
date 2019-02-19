(function() {
    'use strict';
    

	angular
		.module('app.pessoa')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Pessoa', {
            templateUrl:'app/pessoa/search.html',
            controller:'PessoaController',
            controllerAs: 'vmPessoa'
        })

        .when('/Pessoa/new', {
            templateUrl:'app/pessoa/detail.html',
            controller:'PessoaController',
            controllerAs: 'vmPessoa'
        })

        .when('/Pessoa/edit/:EntityId', {
            templateUrl:'app/pessoa/detail.html',
            controller:'PessoaController',
            controllerAs: 'vmPessoa'
        })

        .when('/Pessoa/view/:EntityId', {
            templateUrl:'app/pessoa/detail.html',
            controller:'PessoaController',
            controllerAs: 'vmPessoa'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();