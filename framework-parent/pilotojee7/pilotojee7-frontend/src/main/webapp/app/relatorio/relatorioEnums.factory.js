(function() {
    'use strict';

    angular.module('app.relatorio').factory('RelatorioEnumsResource', function($resource, Constants) {
    	
    	var resourceUrl = Constants.backendUrl + 'relatorio/enums/';
    	var resource = $resource(resourceUrl, {id : '@id'}, {
    		'listTipoFiltro' : {
    			url: resourceUrl + 'TipoFiltro',
    			method : 'GET',
    			isArray : true
    		}
    	});
    	return resource;
    });

})();