(function() {
    'use strict';
    
	angular
		.module('app.celular')
		.service('CelularService', CelularService);
		
	CelularService.$inject = ['CelularFactory'];
	
	function CelularService(CelularFactory) {
	
		var vm = this;
		
		vm.celularList = vm.celularList || [];
		vm.listarCelular = listarCelular;
		
		function listarCelular(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return CelularFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.celularList);
	        	}
	        );
		}
		
	};

})();