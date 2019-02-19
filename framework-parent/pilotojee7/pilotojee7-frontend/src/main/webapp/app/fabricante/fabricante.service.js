(function() {
    'use strict';
    
	angular
		.module('app.fabricante')
		.service('FabricanteService', FabricanteService);
		
	FabricanteService.$inject = ['FabricanteFactory'];
	
	function FabricanteService(FabricanteFactory) {
	
		var vm = this;
		
		vm.fabricanteList = vm.fabricanteList || [];
		vm.listarFabricante = listarFabricante;
		
		function listarFabricante(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return FabricanteFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.fabricanteList);
	        	}
	        );
		}
		
	};

})();