(function() {
    'use strict';
    

	angular
		.module('app.pessoaJuridica')
		.factory('PessoaJuridicaFactory', PessoaJuridicaFactory);
	
	function PessoaJuridicaFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'pessoaJuridica/';
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