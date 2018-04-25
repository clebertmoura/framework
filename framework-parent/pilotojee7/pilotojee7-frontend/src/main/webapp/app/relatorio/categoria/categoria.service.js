(function() {
    'use strict';
    
	angular
		.module('app.relatorio.categoria')
		.service('CategoriaService', CategoriaService);
		
	CategoriaService.$inject = ['CategoriaFactory'];
	
	function CategoriaService(CategoriaFactory) {
	
		var vm = this;
		
		vm.categoriaList = vm.categoriaList || [];
		vm.listarCategoria = listarCategoria;
		
		function listarCategoria(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return CategoriaFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.categoriaList);
	        	}
	        );
		}
		
	};

})();