(function() {
    'use strict';
    
	angular
		.module('app.keycloakGrupo')
		.service('KeycloakGrupoService', KeycloakGrupoService);
		
	KeycloakGrupoService.$inject = ['KeycloakGrupoFactory'];
	
	function KeycloakGrupoService(KeycloakGrupoFactory) {
	
		var vm = this;
		
		vm.keycloakGrupoList = vm.keycloakGrupoList || [];
		vm.listarKeycloakGrupo = listarKeycloakGrupo;
		
		function listarKeycloakGrupo(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
	    	return KeycloakGrupoFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.keycloakGrupoList);
	        	}
	        );
		}
		
	};

})();