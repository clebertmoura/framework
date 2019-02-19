(function() {
    'use strict';
    

	angular
		.module('app.logradouro')
		.factory('LogradouroFactory', LogradouroFactory);
	
	function LogradouroFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'logradouro/';
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