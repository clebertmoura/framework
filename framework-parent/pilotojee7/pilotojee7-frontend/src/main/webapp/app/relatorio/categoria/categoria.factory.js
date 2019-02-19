(function() {
    'use strict';
    

	angular
		.module('app.relatorio.categoria')
		.factory('CategoriaFactory', CategoriaFactory);
	
	function CategoriaFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'categoria/';
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