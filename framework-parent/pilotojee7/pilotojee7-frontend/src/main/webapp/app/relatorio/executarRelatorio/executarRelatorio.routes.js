(function() {
    'use strict';
    

	angular
		.module('app.relatorio.executarRelatorio')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
	
		$routeProvider
	        .when('/ExecutarRelatorios', {
	            templateUrl:'app/relatorio/executarRelatorio/executar.html',
	            controller:'ExecutarRelatorioController',
	            controllerAs: 'vmExecutarRelatorio'
	        });
	    
	};


})();