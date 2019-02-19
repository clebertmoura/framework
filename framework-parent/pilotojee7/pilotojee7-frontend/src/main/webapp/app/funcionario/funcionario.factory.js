(function() {
    'use strict';
    

	angular
		.module('app.funcionario')
		.factory('FuncionarioFactory', FuncionarioFactory);
	
	function FuncionarioFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'funcionario/';
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