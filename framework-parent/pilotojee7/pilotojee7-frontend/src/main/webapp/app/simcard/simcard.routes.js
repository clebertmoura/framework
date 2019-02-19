(function() {
    'use strict';
    

	angular
		.module('app.simcard')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
        .when('/', {
            templateUrl:'app/shared/home/home.html',
            controller:'HomeController'
        })
        
        .when('/Simcard', {
            templateUrl:'app/simcard/search.html',
            controller:'SimcardController',
            controllerAs: 'vmSimcard'
        })

        .when('/Simcard/new', {
            templateUrl:'app/simcard/detail.html',
            controller:'SimcardController',
            controllerAs: 'vmSimcard'
        })

        .when('/Simcard/edit/:EntityId', {
            templateUrl:'app/simcard/detail.html',
            controller:'SimcardController',
            controllerAs: 'vmSimcard'
        })

        .when('/Simcard/view/:EntityId', {
            templateUrl:'app/simcard/detail.html',
            controller:'SimcardController',
            controllerAs: 'vmSimcard'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();