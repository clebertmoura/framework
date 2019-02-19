(function() {
    'use strict';
    

	angular
		.module('app.operadora')
		.factory('OperadoraFactory', OperadoraFactory);
	
	function OperadoraFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'operadora/';
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