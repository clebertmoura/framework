(function() {
    'use strict';
    
	angular
		.module('app.empresa')
		.service('EmpresaService', EmpresaService);
		
	EmpresaService.$inject = ['EmpresaFactory'];
	
	function EmpresaService(EmpresaFactory) {
	
		var vm = this;
		
		vm.empresaList = vm.empresaList || [];
		vm.listarEmpresa = listarEmpresa;
		
		function listarEmpresa(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
			return EmpresaFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.empresaList);
	        	}
	        );
		}
		
	};

})();