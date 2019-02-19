(function () {
  'use strict';
    var inheritance = angular.module('app.modeloCelular');

    inheritance.controller('ModeloCelularModalController', ModeloCelularModalController);

    ModeloCelularModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'ModeloCelularFactory', 'ModeloCelularService', 'FabricanteFactory', 'FabricanteService'];


  /**
   * Controlador da entidade ModeloCelular que estende as funcionalidades do BaseModalController. 
   */
  function ModeloCelularModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 ModeloCelularFactory, ModeloCelularService, FabricanteFactory, FabricanteService) {

    var vm = this;

    vm.modeloCelular = vm.modeloCelular || new ModeloCelularFactory();

    $controller('BaseModalController', { vm: vm, factory: ModeloCelularFactory, entityName: 'ModeloCelular', entity: vm.modeloCelular, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Fabricante
    vm.fabricanteList = FabricanteService.fabricanteList;
    vm.listarFabricante = FabricanteService.listarFabricante;
    vm.listarFabricante();
	
	// Carregamento dos campos Enums
	
  }

})();