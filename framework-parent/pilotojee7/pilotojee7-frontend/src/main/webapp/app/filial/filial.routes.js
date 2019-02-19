(function() {
    'use strict';
    

	angular
		.module('app.filial')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Filial', {
            templateUrl:'app/filial/search.html',
            controller:'FilialController',
            controllerAs: 'vmFilial'
        })

        .when('/Filial/new', {
            templateUrl:'app/filial/detail.html',
            controller:'FilialController',
            controllerAs: 'vmFilial'
        })

        .when('/Filial/edit/:EntityId', {
            templateUrl:'app/filial/detail.html',
            controller:'FilialController',
            controllerAs: 'vmFilial'
        })

        .when('/Filial/view/:EntityId', {
            templateUrl:'app/filial/detail.html',
            controller:'FilialController',
            controllerAs: 'vmFilial'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();