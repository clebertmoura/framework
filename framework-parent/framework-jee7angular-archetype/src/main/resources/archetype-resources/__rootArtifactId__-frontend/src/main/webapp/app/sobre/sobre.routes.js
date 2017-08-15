(function() {
    'use strict';
    

	angular
		.module('app.sobre')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
        $routeProvider
        .when('/Sobre', {
            templateUrl:'app/sobre/sobre.html'
        })

        .otherwise({
            redirectTo: '/'
        });
	    
	};


})();