(function() {
    'use strict';
    
	angular
		.module('app.tipoLogradouro')
		.service('TipoLogradouroService', TipoLogradouroService);
		
	TipoLogradouroService.$inject = ['TipoLogradouroFactory'];
	
	function TipoLogradouroService(TipoLogradouroFactory) {
	
		var vm = this;
		
		vm.tipoLogradouroList = vm.tipoLogradouroList || [];
		vm.listarTipoLogradouro = listarTipoLogradouro;
		
		function listarTipoLogradouro(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'descricao', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'descricao', order: 'ASC'});
	    	return TipoLogradouroFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.tipoLogradouroList);
	        	}
	        );
		}
		
	};

})();