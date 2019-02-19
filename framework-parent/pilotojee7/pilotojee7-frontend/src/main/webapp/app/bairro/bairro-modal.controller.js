(function () {
  'use strict';
    var inheritance = angular.module('app.bairro');

    inheritance.controller('BairroModalController', BairroModalController);

    BairroModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'BairroFactory', 'BairroService', 'CidadeFactory', 'CidadeService'];


  /**
   * Controlador da entidade Bairro que estende as funcionalidades do BaseModalController. 
   */
  function BairroModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 BairroFactory, BairroService, CidadeFactory, CidadeService) {

    var vm = this;

    vm.bairro = vm.bairro || new BairroFactory();

    $controller('BaseModalController', { vm: vm, factory: BairroFactory, entityName: 'Bairro', entity: vm.bairro, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Cidade
    vm.cidadeList = CidadeService.cidadeList;
    vm.listarCidade = CidadeService.listarCidade;
    vm.listarCidade();
	
	// Carregamento dos campos Enums
	
  }

})();