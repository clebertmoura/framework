(function () {
  'use strict';
    var inheritance = angular.module('app.tipoLogradouro');

    inheritance.controller('TipoLogradouroModalController', TipoLogradouroModalController);

    TipoLogradouroModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'TipoLogradouroFactory', 'TipoLogradouroService'];


  /**
   * Controlador da entidade TipoLogradouro que estende as funcionalidades do BaseModalController. 
   */
  function TipoLogradouroModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 TipoLogradouroFactory, TipoLogradouroService) {

    var vm = this;

    vm.tipoLogradouro = vm.tipoLogradouro || new TipoLogradouroFactory();

    $controller('BaseModalController', { vm: vm, factory: TipoLogradouroFactory, entityName: 'TipoLogradouro', entity: vm.tipoLogradouro, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
  }

})();