(function() {
    'use strict';
    
	angular
		.module('app.cliente')
		.service('ClienteService', ClienteService);
		
	ClienteService.$inject = ['ClienteFactory'];
	
	function ClienteService(ClienteFactory) {
	
		var vm = this;
		
		vm.clienteList = vm.clienteList || [];
		vm.listarCliente = listarCliente;
		
		function listarCliente(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return ClienteFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.clienteList);
	        	}
	        );
		}
		
	};

})();