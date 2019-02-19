(function() {
    'use strict';
    
	angular
		.module('app.endereco')
		.service('EnderecoService', EnderecoService);
		
	EnderecoService.$inject = ['EnderecoFactory'];
	
	function EnderecoService(EnderecoFactory) {
	
		var vm = this;
		
		vm.enderecoList = vm.enderecoList || [];
		vm.listarEndereco = listarEndereco;
		
		function listarEndereco(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return EnderecoFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.enderecoList);
	        	}
	        );
		}
		
	};

})();