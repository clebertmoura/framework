(function () {
  'use strict';
    var inheritance = angular.module('app.operadora');

    inheritance.controller('OperadoraModalController', OperadoraModalController);

    OperadoraModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'OperadoraFactory', 'OperadoraService'];


  /**
   * Controlador da entidade Operadora que estende as funcionalidades do BaseModalController. 
   */
  function OperadoraModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 OperadoraFactory, OperadoraService) {

    var vm = this;

    vm.operadora = vm.operadora || new OperadoraFactory();

    $controller('BaseModalController', { vm: vm, factory: OperadoraFactory, entityName: 'Operadora', entity: vm.operadora, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
  }

})();