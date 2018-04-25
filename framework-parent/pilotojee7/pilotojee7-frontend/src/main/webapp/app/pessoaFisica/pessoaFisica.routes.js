(function() {
    'use strict';
    

	angular
		.module('app.pessoaFisica')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/PessoaFisica', {
            templateUrl:'app/pessoaFisica/search.html',
            controller:'PessoaFisicaController',
            controllerAs: 'vmPessoaFisica'
        })

        .when('/PessoaFisica/new', {
            templateUrl:'app/pessoaFisica/detail.html',
            controller:'PessoaFisicaController',
            controllerAs: 'vmPessoaFisica'
        })

        .when('/PessoaFisica/edit/:EntityId', {
            templateUrl:'app/pessoaFisica/detail.html',
            controller:'PessoaFisicaController',
            controllerAs: 'vmPessoaFisica'
        })

        .when('/PessoaFisica/view/:EntityId', {
            templateUrl:'app/pessoaFisica/detail.html',
            controller:'PessoaFisicaController',
            controllerAs: 'vmPessoaFisica'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();