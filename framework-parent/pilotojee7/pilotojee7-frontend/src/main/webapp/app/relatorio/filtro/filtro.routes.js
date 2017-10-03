(function() {
    'use strict';
    

	angular
		.module('app.relatorio.filtro')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Filtro', {
            templateUrl:'app/relatorio/filtro/search.html',
            controller:'FiltroController',
            controllerAs: 'vmFiltro'
        })

        .when('/Filtro/new', {
            templateUrl:'app/relatorio/filtro/detail.html',
            controller:'FiltroController',
            controllerAs: 'vmFiltro'
        })

        .when('/Filtro/edit/:EntityId', {
            templateUrl:'app/relatorio/filtro/detail.html',
            controller:'FiltroController',
            controllerAs: 'vmFiltro'
        })

        .when('/Filtro/view/:EntityId', {
            templateUrl:'app/relatorio/filtro/detail.html',
            controller:'FiltroController',
            controllerAs: 'vmFiltro'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();