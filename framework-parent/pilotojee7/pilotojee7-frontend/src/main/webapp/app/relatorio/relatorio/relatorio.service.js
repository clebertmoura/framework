(function() {
    'use strict';
    
	angular
		.module('app.relatorio.relatorio')
		.service('RelatorioService', RelatorioService);
		
	RelatorioService.$inject = ['RelatorioFactory'];
	
	function RelatorioService(RelatorioFactory) {
	
		var vm = this;
		
		vm.relatorioList = vm.relatorioList || [];
		vm.listarRelatorio = listarRelatorio;
		
		function listarRelatorio(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return RelatorioFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.relatorioList);
	        	}
	        );
		}
		
		vm.listarRelatorioPorCategoria = listarRelatorioPorCategoria;
		
		function listarRelatorioPorCategoria(categoria) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (categoria && categoria != null) {
				vRestrictions.push({field: 'categoria.id', operator: 'EQ', value: categoria.id});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return RelatorioFactory.queryAll(
				{
					first: -1, max: -1, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}
	        );
		}
		
	};

})();