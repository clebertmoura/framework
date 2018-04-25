(function() {
    'use strict';
    
	angular
		.module('app.relatorio.filtro')
		.service('FiltroService', FiltroService);
		
	FiltroService.$inject = ['FiltroFactory'];
	
	function FiltroService(FiltroFactory) {
	
		var vm = this;
		
		vm.filtroList = vm.filtroList || [];
		vm.listarFiltro = listarFiltro;
		
		function listarFiltro(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return FiltroFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.filtroList);
	        	}
	        );
		}
		
	};

})();