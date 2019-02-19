(function() {
    'use strict';
    

	angular
		.module('app.keycloakUsuario')
		.factory('KeycloakUsuarioFactory', KeycloakUsuarioFactory);
	
	function KeycloakUsuarioFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'keycloakUsuario/';
		var resource = $resource(resourceUrl + ':EntityId', {EntityId : '@id'}, {
			'queryAll' : {
				url: resourceUrl + 'findByRestrictions',
				method : 'POST',
				isArray : false
			},
			'query' : {
				method : 'GET',
				isArray : false
			},
			'update' : {
				method : 'PUT'
			},
			'delete' : {
				method : 'DELETE'
			}
		});
		return resource;
	};

})();