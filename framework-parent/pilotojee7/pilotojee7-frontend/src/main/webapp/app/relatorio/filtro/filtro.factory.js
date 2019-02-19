(function() {
    'use strict';
    

	angular
		.module('app.relatorio.filtro')
		.factory('FiltroFactory', FiltroFactory);
	
	function FiltroFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'filtro/';
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
			},
			'getFiltroItens' : {
				url: resourceUrl + 'getFiltroItens',
				method : 'POST',
				isArray : true
			},
			'getFiltroItensById' : {
				url: resourceUrl + 'getFiltroItensById',
				method : 'GET',
				isArray : true
			}
		});
		return resource;
	};

})();