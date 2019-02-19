(function() {
    'use strict';
    

	angular
		.module('app.uf')
		.factory('UfFactory', UfFactory);
	
	function UfFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'uf/';
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