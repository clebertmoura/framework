(function() {
    'use strict';
    
	angular
		.module('app.dispositivo')
		.service('DispositivoService', DispositivoService);
		
	DispositivoService.$inject = ['DispositivoFactory'];
	
	function DispositivoService(DispositivoFactory) {
	
		var vm = this;
		
		vm.dispositivoList = vm.dispositivoList || [];
		vm.listarDispositivo = listarDispositivo;
		
		function listarDispositivo(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return DispositivoFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.dispositivoList);
	        	}
	        );
		}
		
	};

})();