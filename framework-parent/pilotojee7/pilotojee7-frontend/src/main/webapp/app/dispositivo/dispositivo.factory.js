(function() {
    'use strict';
    

	angular
		.module('app.dispositivo')
		.factory('DispositivoFactory', DispositivoFactory);
	
	function DispositivoFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'dispositivo/';
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