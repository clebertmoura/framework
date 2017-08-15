(function() {
    'use strict';
    

	angular
		.module('app.pessoa')
		.factory('PessoaFactory', PessoaFactory);
	
	function PessoaFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'pessoa/';
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