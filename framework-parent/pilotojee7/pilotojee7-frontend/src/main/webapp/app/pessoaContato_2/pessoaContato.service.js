(function() {
    'use strict';
    
	angular
		.module('app.pessoaContato')
		.service('PessoaContatoService', PessoaContatoService);
		
	PessoaContatoService.$inject = ['PessoaContatoFactory'];
	
	function PessoaContatoService(PessoaContatoFactory) {
	
		var vm = this;
		
		vm.pessoaContatoList = vm.pessoaContatoList || [];
		vm.listarPessoaContato = listarPessoaContato;
		
		function listarPessoaContato(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
			return PessoaContatoFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.pessoaContatoList);
	        	}
	        );
		}
		
	};

})();