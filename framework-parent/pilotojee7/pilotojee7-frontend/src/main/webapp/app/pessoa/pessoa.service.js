(function() {
    'use strict';
    
	angular
		.module('app.pessoa')
		.service('PessoaService', PessoaService);
		
	PessoaService.$inject = ['PessoaFactory'];
	
	function PessoaService(PessoaFactory) {
	
		var vm = this;
		
		vm.pessoaList = vm.pessoaList || [];
		vm.listarPessoa = listarPessoa;
		
		function listarPessoa(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return PessoaFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.pessoaList);
	        	}
	        );
		}
		
	};

})();