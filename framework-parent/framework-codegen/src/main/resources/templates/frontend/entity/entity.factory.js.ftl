(function() {
    'use strict';
    

	angular
		.module('app.${entityNameCamelCase}')
		.factory('${entityName}Factory', ${entityName}Factory);
		
	${entityName}Factory.$inject = ['$resource', 'Constants'];
	
	function ${entityName}Factory($resource, Constants) {
		var resourceUrl = Constants.backendUrl + '${entityNameCamelCase}/';
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