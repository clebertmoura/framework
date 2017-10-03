(function() {
    'use strict';
    

	angular
		.module('app.relatorio.categoria')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Categoria', {
            templateUrl:'app/relatorio/categoria/search.html',
            controller:'CategoriaController',
            controllerAs: 'vmCategoria'
        })

        .when('/Categoria/new', {
            templateUrl:'app/relatorio/categoria/detail.html',
            controller:'CategoriaController',
            controllerAs: 'vmCategoria'
        })

        .when('/Categoria/edit/:EntityId', {
            templateUrl:'app/relatorio/categoria/detail.html',
            controller:'CategoriaController',
            controllerAs: 'vmCategoria'
        })

        .when('/Categoria/view/:EntityId', {
            templateUrl:'app/relatorio/categoria/detail.html',
            controller:'CategoriaController',
            controllerAs: 'vmCategoria'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();