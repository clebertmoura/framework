(function() {
    'use strict';
    

	angular
		.module('app.endereco')
		.factory('EnderecoFactory', EnderecoFactory);
	
	function EnderecoFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'endereco/';
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