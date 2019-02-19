'use strict';

angular.module('app.relatorio.filtro.directives', [])

.directive('filtro', ['FiltroFactory', 'toastr', function(FiltroFactory, toastr) {
	
	function fLink(scope, element, attrs, controller, transcludeFn) {
		scope.list = [];
		FiltroFactory.getFiltroItens(scope.filtroModel, 
			function(result) {
				scope.list = result;
    		},
    		function(error) {
    			console.error(error);
    			toastr.error("Não foi possível carregar a lista de itens. Favor verificar o SQL ou JSON informado!");
    		});
		
		switch (scope.filtroModel.tipoFiltro) {
	    	case 'SELECT_ONE':
	    		scope.ngModel = {};
				break;
	    	case 'SELECT_MANY':
	    		scope.ngModel = [];
	    		break;
	    	case 'CHECKBOX':
	    		scope.ngModel = [];
	    		break;
	    	case 'RADIO':
	    		scope.ngModel = null;
	    		break;
	    	case 'TEXT':
	    		scope.ngModel = "";
	    		break;
	    	case 'NUMBER':
	    	case 'FLOAT':
	    		scope.ngModel = 0;
	    		break;
	    	case 'DATE':
	    	case 'DATETIME':
			case 'TIME':
				scope.ngModel = new Date();
				break;
		}
	};
	
	return {
		restrict : 'AE',
		transclude: false,
		templateUrl : 'app/relatorio/filtro/directives/filtro.directive.html',
		scope : {
			filtroModel : '=',
			ngModel : '=',
			controller : '='
		},
		link: fLink
	};

}]);