(function() {
    'use strict';
    

	angular
		.module('app.celular')
		.factory('CelularFactory', CelularFactory);
	
	function CelularFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'celular/';
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