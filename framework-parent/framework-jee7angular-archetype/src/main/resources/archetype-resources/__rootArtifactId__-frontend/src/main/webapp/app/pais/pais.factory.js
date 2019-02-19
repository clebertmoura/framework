(function() {
    'use strict';
    

	angular
		.module('app.pais')
		.factory('PaisFactory', PaisFactory);
	
	function PaisFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'pais/';
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