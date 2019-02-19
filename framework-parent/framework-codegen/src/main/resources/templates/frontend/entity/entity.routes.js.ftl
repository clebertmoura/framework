(function() {
    'use strict';
    

	angular
		.module('app.${entityNameCamelCase}')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html?ts=' + moment().valueOf(),
            controller:'HomeController'
        })
        
        .when('/${entityName}', {
            templateUrl:'app/${entityNameCamelCase}/search.html?ts=' + moment().valueOf(),
            controller:'${entityName}Controller',
            controllerAs: 'vm${entityName}'
        })

        .when('/${entityName}/new', {
            templateUrl:'app/${entityNameCamelCase}/detail.html?ts=' + moment().valueOf(),
            controller:'${entityName}Controller',
            controllerAs: 'vm${entityName}'
        })

        .when('/${entityName}/edit/:EntityId', {
            templateUrl:'app/${entityNameCamelCase}/detail.html?ts=' + moment().valueOf(),
            controller:'${entityName}Controller',
            controllerAs: 'vm${entityName}'
        })

        .when('/${entityName}/view/:EntityId', {
            templateUrl:'app/${entityNameCamelCase}/detail.html?ts=' + moment().valueOf(),
            controller:'${entityName}Controller',
            controllerAs: 'vm${entityName}'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();