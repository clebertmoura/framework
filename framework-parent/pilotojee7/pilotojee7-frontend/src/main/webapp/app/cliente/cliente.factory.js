(function() {
    'use strict';
    

	angular
		.module('app.cliente')
		.factory('ClienteFactory', ClienteFactory);
	
	function ClienteFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'cliente/';
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