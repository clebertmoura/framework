(function () {
  'use strict';
    var inheritance = angular.module('app.modeloCelular');

    inheritance.controller('ModeloCelularController', ModeloCelularController);

    ModeloCelularController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'ModeloCelularFactory', 'ModeloCelularService', 'FabricanteFactory', 'FabricanteService'];


  /**
   * Controlador da entidade ModeloCelular que estende as funcionalidades do BaseController. 
   */
  function ModeloCelularController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 ModeloCelularFactory, ModeloCelularService, FabricanteFactory, FabricanteService) {

    var vm = this;

    vm.modeloCelular = vm.modeloCelular || new ModeloCelularFactory();

    $controller('BaseController', { vm: vm, factory: ModeloCelularFactory, entityName: 'ModeloCelular', entity: vm.modeloCelular, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.fabricante && vm.searchData.fabricante != null) {
    		restrictions.push({field: 'fabricante.id', operator: 'EQ', value: vm.searchData.fabricante.id});
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
	// Lista de Fabricante
    vm.fabricanteList = FabricanteService.fabricanteList;
    vm.listarFabricante = FabricanteService.listarFabricante;
    vm.listarFabricante();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();