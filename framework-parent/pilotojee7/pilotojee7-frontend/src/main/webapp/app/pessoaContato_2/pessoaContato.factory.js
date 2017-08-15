(function() {
    'use strict';
    

	angular
		.module('app.pessoaContato')
		.factory('PessoaContatoFactory', PessoaContatoFactory);
	
	function PessoaContatoFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'pessoaContato/';
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