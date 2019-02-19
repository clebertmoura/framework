(function () {
  'use strict';
    var inheritance = angular.module('app.fabricante');

    inheritance.controller('FabricanteModalController', FabricanteModalController);

    FabricanteModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'FabricanteFactory', 'FabricanteService', 'PessoaJuridicaFactory', 'PessoaJuridicaService'];


  /**
   * Controlador da entidade Fabricante que estende as funcionalidades do BaseModalController. 
   */
  function FabricanteModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 FabricanteFactory, FabricanteService, PessoaJuridicaFactory, PessoaJuridicaService) {

    var vm = this;

    vm.fabricante = vm.fabricante || new FabricanteFactory();

    $controller('BaseModalController', { vm: vm, factory: FabricanteFactory, entityName: 'Fabricante', entity: vm.fabricante, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de PessoaJuridica
    vm.pessoaJuridicaList = PessoaJuridicaService.pessoaJuridicaList;
    vm.listarPessoaJuridica = PessoaJuridicaService.listarPessoaJuridica;
    vm.listarPessoaJuridica();
	
	// Carregamento dos campos Enums
	
  }

})();