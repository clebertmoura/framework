(function() {
    'use strict';
    
	angular
		.module('app.bairro')
		.service('BairroService', BairroService);
		
	BairroService.$inject = ['BairroFactory'];
	
	function BairroService(BairroFactory) {
	
		var vm = this;
		
		vm.bairroList = vm.bairroList || [];
		vm.listarBairro = listarBairro;
		
		function listarBairro(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return BairroFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.bairroList);
	        	}
	        );
		}
		
	};

})();