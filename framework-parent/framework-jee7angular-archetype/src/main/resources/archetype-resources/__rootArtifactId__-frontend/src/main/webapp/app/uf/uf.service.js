(function() {
    'use strict';
    
	angular
		.module('app.uf')
		.service('UfService', UfService);
		
	UfService.$inject = ['UfFactory'];
	
	function UfService(UfFactory) {
	
		var vm = this;
		
		vm.ufList = vm.ufList || [];
		vm.listarUf = listarUf;
		
		function listarUf(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
			return UfFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.ufList);
	        	}
	        );
		}
		
	};

})();