(function () {
  'use strict';
    var inheritance = angular.module('app.logradouro');

    inheritance.controller('LogradouroModalController', LogradouroModalController);

    LogradouroModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'LogradouroFactory', 'LogradouroService', 'BairroFactory', 'TipoLogradouroFactory', 'BairroService', 'TipoLogradouroService'];


  /**
   * Controlador da entidade Logradouro que estende as funcionalidades do BaseModalController. 
   */
  function LogradouroModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 LogradouroFactory, LogradouroService, BairroFactory, TipoLogradouroFactory, BairroService, TipoLogradouroService) {

    var vm = this;

    vm.logradouro = vm.logradouro || new LogradouroFactory();

    $controller('BaseModalController', { vm: vm, factory: LogradouroFactory, entityName: 'Logradouro', entity: vm.logradouro, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Bairro
    vm.bairroList = BairroService.bairroList;
    vm.listarBairro = BairroService.listarBairro;
    vm.listarBairro();
	// Lista de TipoLogradouro
    vm.tipoLogradouroList = TipoLogradouroService.tipoLogradouroList;
    vm.listarTipoLogradouro = TipoLogradouroService.listarTipoLogradouro;
    vm.listarTipoLogradouro();
	
	// Carregamento dos campos Enums
	
  }

})();