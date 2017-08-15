(function() {
    'use strict';
    
	angular
		.module('app.logradouro')
		.service('LogradouroService', LogradouroService);
		
	LogradouroService.$inject = ['LogradouroFactory'];
	
	function LogradouroService(LogradouroFactory) {
	
		var vm = this;
		
		vm.logradouroList = vm.logradouroList || [];
		vm.listarLogradouro = listarLogradouro;
		
		function listarLogradouro(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return LogradouroFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.logradouroList);
	        	}
	        );
		}
		
	};

})();