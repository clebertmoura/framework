(function() {
    'use strict';
    

	angular
		.module('app.funcionario')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Funcionario', {
            templateUrl:'app/funcionario/search.html',
            controller:'FuncionarioController',
            controllerAs: 'vmFuncionario'
        })

        .when('/Funcionario/new', {
            templateUrl:'app/funcionario/detail.html',
            controller:'FuncionarioController',
            controllerAs: 'vmFuncionario'
        })

        .when('/Funcionario/edit/:EntityId', {
            templateUrl:'app/funcionario/detail.html',
            controller:'FuncionarioController',
            controllerAs: 'vmFuncionario'
        })

        .when('/Funcionario/view/:EntityId', {
            templateUrl:'app/funcionario/detail.html',
            controller:'FuncionarioController',
            controllerAs: 'vmFuncionario'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();