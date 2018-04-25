(function() {
    'use strict';
    

	angular
		.module('app.relatorio.relatorio')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Relatorio', {
            templateUrl:'app/relatorio/relatorio/search.html',
            controller:'RelatorioController',
            controllerAs: 'vmRelatorio'
        })

        .when('/Relatorio/new', {
            templateUrl:'app/relatorio/relatorio/detail.html',
            controller:'RelatorioController',
            controllerAs: 'vmRelatorio'
        })

        .when('/Relatorio/edit/:EntityId', {
            templateUrl:'app/relatorio/relatorio/detail.html',
            controller:'RelatorioController',
            controllerAs: 'vmRelatorio'
        })

        .when('/Relatorio/view/:EntityId', {
            templateUrl:'app/relatorio/relatorio/detail.html',
            controller:'RelatorioController',
            controllerAs: 'vmRelatorio'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();