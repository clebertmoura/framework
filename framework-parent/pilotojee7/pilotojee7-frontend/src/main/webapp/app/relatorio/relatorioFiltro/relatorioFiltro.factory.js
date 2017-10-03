(function() {
    'use strict';
    

	angular
		.module('app.relatorio.relatorioFiltro')
		.factory('RelatorioFiltroFactory', RelatorioFiltroFactory);
	
	function RelatorioFiltroFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'relatorioFiltro/';
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