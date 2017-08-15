(function() {
    'use strict';
    
	angular
		.module('app.operadora')
		.service('OperadoraService', OperadoraService);
		
	OperadoraService.$inject = ['OperadoraFactory'];
	
	function OperadoraService(OperadoraFactory) {
	
		var vm = this;
		
		vm.operadoraList = vm.operadoraList || [];
		vm.listarOperadora = listarOperadora;
		
		function listarOperadora(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return OperadoraFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.operadoraList);
	        	}
	        );
		}
		
	};

})();