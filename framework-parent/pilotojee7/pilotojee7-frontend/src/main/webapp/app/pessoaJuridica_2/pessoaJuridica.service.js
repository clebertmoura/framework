(function() {
    'use strict';
    
	angular
		.module('app.pessoaJuridica')
		.service('PessoaJuridicaService', PessoaJuridicaService);
		
	PessoaJuridicaService.$inject = ['PessoaJuridicaFactory'];
	
	function PessoaJuridicaService(PessoaJuridicaFactory) {
	
		var vm = this;
		
		vm.pessoaJuridicaList = vm.pessoaJuridicaList || [];
		vm.listarPessoaJuridica = listarPessoaJuridica;
		
		function listarPessoaJuridica(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
			return PessoaJuridicaFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.pessoaJuridicaList);
	        	}
	        );
		}
		
	};

})();