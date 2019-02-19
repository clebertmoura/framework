(function() {
    'use strict';
    

	angular
		.module('app.filial')
		.factory('FilialFactory', FilialFactory);
	
	function FilialFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'filial/';
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