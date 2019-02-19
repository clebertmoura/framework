(function() {
    'use strict';
    

	angular
		.module('app.simcard')
		.factory('SimcardFactory', SimcardFactory);
	
	function SimcardFactory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + 'simcard/';
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