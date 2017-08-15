(function () {
  'use strict';
    var inheritance = angular.module('app.uf');

    inheritance.controller('UfController', UfController);

    UfController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', 'FlashFactory',  'UfFactory', 'UfService', 'PaisFactory', 'PaisService'];


  /**
   * Controlador do Processo que estende as funcionalidades do BaseController. 
   */
  function UfController($controller, $routeParams, $rootScope, $scope, $filter, $location, FlashFactory, 
  	 UfFactory, UfService, PaisFactory, PaisService) {

    var vm = this;

    vm.uf = vm.uf || {};

    $controller('BaseController', { vm: vm, factory: UfFactory, restrictions: getRestrictions(), entityName: 'Uf', entity: vm.uf });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.sigla && vm.searchData.sigla != "") {
    		restrictions.push({field: 'sigla', operator: 'LI', value: vm.searchData.sigla});
    	}
		if (vm.searchData.codigoIbge && vm.searchData.codigoIbge != "") {
    		restrictions.push({field: 'codigoIbge', operator: 'LI', value: vm.searchData.codigoIbge});
    	}
		if (vm.searchData.pais && vm.searchData.pais != null) {
    		restrictions.push({field: 'pais.id', operator: 'EQ', value: vm.searchData.pais.id});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'nome', order: 'ASC'});
		return orderings;
	};
	
	// Carregamento de relacionamentos
	// Lista de Pais
    vm.paisList = PaisService.paisList;
    vm.listarPais = PaisService.listarPais;
    vm.listarPais();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();