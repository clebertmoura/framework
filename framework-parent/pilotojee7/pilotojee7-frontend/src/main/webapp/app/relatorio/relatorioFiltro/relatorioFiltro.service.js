(function() {
    'use strict';
    
	angular
		.module('app.relatorio.relatorioFiltro')
		.service('RelatorioFiltroService', RelatorioFiltroService);
		
	RelatorioFiltroService.$inject = ['RelatorioFiltroFactory'];
	
	function RelatorioFiltroService(RelatorioFiltroFactory) {
	
		var vm = this;
		
		vm.relatorioFiltroList = vm.relatorioFiltroList || [];
		vm.listarRelatorioFiltro = listarRelatorioFiltro;
		
		function listarRelatorioFiltro(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return RelatorioFiltroFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.relatorioFiltroList);
	        	}
	        );
		}
		
	};

})();