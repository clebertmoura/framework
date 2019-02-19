(function () {
  'use strict';
    var inheritance = angular.module('app.uf');

    inheritance.controller('UfModalController', UfModalController);

    UfModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'UfFactory', 'UfService', 'PaisFactory', 'PaisService'];


  /**
   * Controlador da entidade Uf que estende as funcionalidades do BaseModalController. 
   */
  function UfModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 UfFactory, UfService, PaisFactory, PaisService) {

    var vm = this;

    vm.uf = vm.uf || new UfFactory();

    $controller('BaseModalController', { vm: vm, factory: UfFactory, entityName: 'Uf', entity: vm.uf, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Pais
    vm.paisList = PaisService.paisList;
    vm.listarPais = PaisService.listarPais;
    vm.listarPais();
	
	// Carregamento dos campos Enums
	
  }

})();