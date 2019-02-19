(function() {
    'use strict';
    
	angular
		.module('app.pais')
		.service('PaisService', PaisService);
		
	PaisService.$inject = ['PaisFactory'];
	
	function PaisService(PaisFactory) {
	
		var vm = this;
		
		vm.paisList = vm.paisList || [];
		vm.listarPais = listarPais;
		
		function listarPais(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
			return PaisFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.paisList);
	        	}
	        );
		}
		
	};

})();