(function() {
    'use strict';
    

	angular
		.module('app.modeloCelular')
		.factory('ModeloCelularFactory', ModeloCelularFactory);
	
	function ModeloCelularFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'modeloCelular/';
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