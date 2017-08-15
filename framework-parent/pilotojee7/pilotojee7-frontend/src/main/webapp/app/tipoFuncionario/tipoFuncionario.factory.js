(function() {
    'use strict';
    

	angular
		.module('app.tipoFuncionario')
		.factory('TipoFuncionarioFactory', TipoFuncionarioFactory);
	
	function TipoFuncionarioFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'tipoFuncionario/';
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