(function() {
    'use strict';
    

	angular
		.module('app.cidade')
		.factory('CidadeFactory', CidadeFactory);
	
	function CidadeFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'cidade/';
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