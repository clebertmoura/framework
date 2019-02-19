(function() {
    'use strict';
    

	angular
		.module('app.relatorio.relatorioFiltro')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/RelatorioFiltro', {
            templateUrl:'app/relatorio/relatorioFiltro/search.html',
            controller:'RelatorioFiltroController',
            controllerAs: 'vmRelatorioFiltro'
        })

        .when('/RelatorioFiltro/new', {
            templateUrl:'app/relatorio/relatorioFiltro/detail.html',
            controller:'RelatorioFiltroController',
            controllerAs: 'vmRelatorioFiltro'
        })

        .when('/RelatorioFiltro/edit/:EntityId', {
            templateUrl:'app/relatorio/relatorioFiltro/detail.html',
            controller:'RelatorioFiltroController',
            controllerAs: 'vmRelatorioFiltro'
        })

        .when('/RelatorioFiltro/view/:EntityId', {
            templateUrl:'app/relatorio/relatorioFiltro/detail.html',
            controller:'RelatorioFiltroController',
            controllerAs: 'vmRelatorioFiltro'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();