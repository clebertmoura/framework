(function() {
    'use strict';
    

	angular
		.module('app.pessoaJuridica')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/PessoaJuridica', {
            templateUrl:'app/pessoaJuridica/search.html',
            controller:'PessoaJuridicaController',
            controllerAs: 'vmPessoaJuridica'
        })

        .when('/PessoaJuridica/new', {
            templateUrl:'app/pessoaJuridica/detail.html',
            controller:'PessoaJuridicaController',
            controllerAs: 'vmPessoaJuridica'
        })

        .when('/PessoaJuridica/edit/:EntityId', {
            templateUrl:'app/pessoaJuridica/detail.html',
            controller:'PessoaJuridicaController',
            controllerAs: 'vmPessoaJuridica'
        })

        .when('/PessoaJuridica/view/:EntityId', {
            templateUrl:'app/pessoaJuridica/detail.html',
            controller:'PessoaJuridicaController',
            controllerAs: 'vmPessoaJuridica'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();