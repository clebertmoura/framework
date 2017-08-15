(function() {
    'use strict';
    

	angular
		.module('app.fabricante')
		.factory('FabricanteFactory', FabricanteFactory);
	
	function FabricanteFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'fabricante/';
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