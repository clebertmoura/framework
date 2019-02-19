(function() {
    'use strict';
    
	angular
		.module('app.modeloCelular')
		.service('ModeloCelularService', ModeloCelularService);
		
	ModeloCelularService.$inject = ['ModeloCelularFactory'];
	
	function ModeloCelularService(ModeloCelularFactory) {
	
		var vm = this;
		
		vm.modeloCelularList = vm.modeloCelularList || [];
		vm.listarModeloCelular = listarModeloCelular;
		
		function listarModeloCelular(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return ModeloCelularFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.modeloCelularList);
	        	}
	        );
		}
		
	};

})();