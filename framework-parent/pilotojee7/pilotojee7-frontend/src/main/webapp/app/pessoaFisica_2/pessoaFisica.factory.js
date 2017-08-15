(function() {
    'use strict';
    

	angular
		.module('app.pessoaFisica')
		.factory('PessoaFisicaFactory', PessoaFisicaFactory);
	
	function PessoaFisicaFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'pessoaFisica/';
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