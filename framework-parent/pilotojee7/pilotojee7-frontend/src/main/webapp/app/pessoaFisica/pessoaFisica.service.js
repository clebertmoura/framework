(function() {
    'use strict';
    
	angular
		.module('app.pessoaFisica')
		.service('PessoaFisicaService', PessoaFisicaService);
		
	PessoaFisicaService.$inject = ['PessoaFisicaFactory'];
	
	function PessoaFisicaService(PessoaFisicaFactory) {
	
		var vm = this;
		
		vm.pessoaFisicaList = vm.pessoaFisicaList || [];
		vm.listarPessoaFisica = listarPessoaFisica;
		
		function listarPessoaFisica(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return PessoaFisicaFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.pessoaFisicaList);
	        	}
	        );
		}
		
	};

})();