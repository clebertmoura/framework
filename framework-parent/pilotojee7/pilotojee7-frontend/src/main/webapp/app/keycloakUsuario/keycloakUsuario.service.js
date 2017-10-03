(function() {
    'use strict';
    
	angular
		.module('app.keycloakUsuario')
		.service('KeycloakUsuarioService', KeycloakUsuarioService);
		
	KeycloakUsuarioService.$inject = ['KeycloakUsuarioFactory'];
	
	function KeycloakUsuarioService(KeycloakUsuarioFactory) {
	
		var vm = this;
		
		vm.keycloakUsuarioList = vm.keycloakUsuarioList || [];
		vm.listarKeycloakUsuario = listarKeycloakUsuario;
		
		function listarKeycloakUsuario(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
	    	}
			vOrderings.push({field: 'id', order: 'ASC'});
	    	return KeycloakUsuarioFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.keycloakUsuarioList);
	        	}
	        );
		}
		
	};

})();