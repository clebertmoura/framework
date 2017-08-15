(function() {
    'use strict';
    

	angular
		.module('app.bairro')
		.factory('BairroFactory', BairroFactory);
	
	function BairroFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'bairro/';
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