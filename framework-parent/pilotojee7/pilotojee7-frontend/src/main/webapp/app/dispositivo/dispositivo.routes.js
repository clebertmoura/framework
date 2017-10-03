(function() {
    'use strict';
    

	angular
		.module('app.dispositivo')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Dispositivo', {
            templateUrl:'app/dispositivo/search.html',
            controller:'DispositivoController',
            controllerAs: 'vmDispositivo'
        })

        .when('/Dispositivo/new', {
            templateUrl:'app/dispositivo/detail.html',
            controller:'DispositivoController',
            controllerAs: 'vmDispositivo'
        })

        .when('/Dispositivo/edit/:EntityId', {
            templateUrl:'app/dispositivo/detail.html',
            controller:'DispositivoController',
            controllerAs: 'vmDispositivo'
        })

        .when('/Dispositivo/view/:EntityId', {
            templateUrl:'app/dispositivo/detail.html',
            controller:'DispositivoController',
            controllerAs: 'vmDispositivo'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();