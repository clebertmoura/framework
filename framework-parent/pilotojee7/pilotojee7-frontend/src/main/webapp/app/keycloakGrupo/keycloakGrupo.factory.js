(function() {
    'use strict';
    

	angular
		.module('app.keycloakGrupo')
		.factory('KeycloakGrupoFactory', KeycloakGrupoFactory);
	
	function KeycloakGrupoFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'keycloakGrupo/';
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