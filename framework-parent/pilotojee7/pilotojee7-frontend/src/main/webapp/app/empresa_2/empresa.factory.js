(function() {
    'use strict';
    

	angular
		.module('app.empresa')
		.factory('EmpresaFactory', EmpresaFactory);
	
	function EmpresaFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'empresa/';
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