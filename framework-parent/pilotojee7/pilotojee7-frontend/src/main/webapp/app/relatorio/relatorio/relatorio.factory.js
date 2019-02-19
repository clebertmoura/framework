(function() {
    'use strict';
    

	angular
		.module('app.relatorio.relatorio')
		.factory('RelatorioFactory', RelatorioFactory);
	
	function RelatorioFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'relatorio/';
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