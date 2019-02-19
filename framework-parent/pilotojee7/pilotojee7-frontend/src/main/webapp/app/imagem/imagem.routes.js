(function() {
    'use strict';
    

	angular
		.module('app.imagem')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Imagem', {
            templateUrl:'app/imagem/search.html',
            controller:'ImagemController',
            controllerAs: 'vmImagem'
        })

        .when('/Imagem/new', {
            templateUrl:'app/imagem/detail.html',
            controller:'ImagemController',
            controllerAs: 'vmImagem'
        })

        .when('/Imagem/edit/:EntityId', {
            templateUrl:'app/imagem/detail.html',
            controller:'ImagemController',
            controllerAs: 'vmImagem'
        })

        .when('/Imagem/view/:EntityId', {
            templateUrl:'app/imagem/detail.html',
            controller:'ImagemController',
            controllerAs: 'vmImagem'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();