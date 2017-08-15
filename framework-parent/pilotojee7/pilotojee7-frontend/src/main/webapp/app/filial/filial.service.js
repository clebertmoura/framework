(function() {
    'use strict';
    
	angular
		.module('app.filial')
		.service('FilialService', FilialService);
		
	FilialService.$inject = ['FilialFactory'];
	
	function FilialService(FilialFactory) {
	
		var vm = this;
		
		vm.filialList = vm.filialList || [];
		vm.listarFilial = listarFilial;
		
		function listarFilial(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return FilialFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.filialList);
	        	}
	        );
		}
		
	};

})();