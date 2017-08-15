(function() {
    'use strict';
    
	angular
		.module('app.tipoFuncionario')
		.service('TipoFuncionarioService', TipoFuncionarioService);
		
	TipoFuncionarioService.$inject = ['TipoFuncionarioFactory'];
	
	function TipoFuncionarioService(TipoFuncionarioFactory) {
	
		var vm = this;
		
		vm.tipoFuncionarioList = vm.tipoFuncionarioList || [];
		vm.listarTipoFuncionario = listarTipoFuncionario;
		
		function listarTipoFuncionario(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'descricao', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'descricao', order: 'ASC'});
	    	return TipoFuncionarioFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.tipoFuncionarioList);
	        	}
	        );
		}
		
	};

})();