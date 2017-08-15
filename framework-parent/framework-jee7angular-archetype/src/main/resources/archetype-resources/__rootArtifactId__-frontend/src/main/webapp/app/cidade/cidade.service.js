(function() {
    'use strict';
    
	angular
		.module('app.cidade')
		.service('CidadeService', CidadeService);
		
	CidadeService.$inject = ['CidadeFactory'];
	
	function CidadeService(CidadeFactory) {
	
		var vm = this;
		
		vm.cidadeList = vm.cidadeList || [];
		vm.listarCidade = listarCidade;
		
		function listarCidade(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
			return CidadeFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.cidadeList);
	        	}
	        );
		}
		
	};

})();