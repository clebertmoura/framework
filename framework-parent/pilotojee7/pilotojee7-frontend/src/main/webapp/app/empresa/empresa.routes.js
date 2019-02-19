(function() {
    'use strict';
    

	angular
		.module('app.empresa')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Empresa', {
            templateUrl:'app/empresa/search.html',
            controller:'EmpresaController',
            controllerAs: 'vmEmpresa'
        })

        .when('/Empresa/new', {
            templateUrl:'app/empresa/detail.html',
            controller:'EmpresaController',
            controllerAs: 'vmEmpresa'
        })

        .when('/Empresa/edit/:EntityId', {
            templateUrl:'app/empresa/detail.html',
            controller:'EmpresaController',
            controllerAs: 'vmEmpresa'
        })

        .when('/Empresa/view/:EntityId', {
            templateUrl:'app/empresa/detail.html',
            controller:'EmpresaController',
            controllerAs: 'vmEmpresa'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();