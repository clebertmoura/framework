(function() {
    'use strict';
    
	angular
		.module('app.funcionario')
		.service('FuncionarioService', FuncionarioService);
		
	FuncionarioService.$inject = ['FuncionarioFactory'];
	
	function FuncionarioService(FuncionarioFactory) {
	
		var vm = this;
		
		vm.funcionarioList = vm.funcionarioList || [];
		vm.listarFuncionario = listarFuncionario;
		
		function listarFuncionario(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return FuncionarioFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.funcionarioList);
	        	}
	        );
		}
		
	};

})();