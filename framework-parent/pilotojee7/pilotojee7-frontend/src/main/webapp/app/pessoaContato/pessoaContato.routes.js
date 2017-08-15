(function() {
    'use strict';
    

	angular
		.module('app.pessoaContato')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/PessoaContato', {
            templateUrl:'app/pessoaContato/search.html',
            controller:'PessoaContatoController',
            controllerAs: 'vmPessoaContato'
        })

        .when('/PessoaContato/new', {
            templateUrl:'app/pessoaContato/detail.html',
            controller:'PessoaContatoController',
            controllerAs: 'vmPessoaContato'
        })

        .when('/PessoaContato/edit/:EntityId', {
            templateUrl:'app/pessoaContato/detail.html',
            controller:'PessoaContatoController',
            controllerAs: 'vmPessoaContato'
        })

        .when('/PessoaContato/view/:EntityId', {
            templateUrl:'app/pessoaContato/detail.html',
            controller:'PessoaContatoController',
            controllerAs: 'vmPessoaContato'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();