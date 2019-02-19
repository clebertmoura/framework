(function() {
    'use strict';
    

	angular
		.module('app.imagem')
		.factory('ImagemFactory', ImagemFactory);
	
	function ImagemFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'imagem/';
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