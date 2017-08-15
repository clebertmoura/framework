(function() {
    'use strict';
    
	angular
		.module('app.simcard')
		.service('SimcardService', SimcardService);
		
	SimcardService.$inject = ['SimcardFactory'];
	
	function SimcardService(SimcardFactory) {
	
		var vm = this;
		
		vm.simcardList = vm.simcardList || [];
		vm.listarSimcard = listarSimcard;
		
		function listarSimcard(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return SimcardFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.simcardList);
	        	}
	        );
		}
		
	};

})();