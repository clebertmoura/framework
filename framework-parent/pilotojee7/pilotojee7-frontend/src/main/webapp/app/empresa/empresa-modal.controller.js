(function () {
  'use strict';
    var inheritance = angular.module('app.empresa');

    inheritance.controller('EmpresaModalController', EmpresaModalController);

    EmpresaModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'EmpresaFactory', 'EmpresaService', 'PessoaJuridicaFactory', 'PessoaJuridicaService'];


  /**
   * Controlador da entidade Empresa que estende as funcionalidades do BaseModalController. 
   */
  function EmpresaModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 EmpresaFactory, EmpresaService, PessoaJuridicaFactory, PessoaJuridicaService) {

    var vm = this;

    vm.empresa = vm.empresa || new EmpresaFactory();

    $controller('BaseModalController', { vm: vm, factory: EmpresaFactory, entityName: 'Empresa', entity: vm.empresa, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de PessoaJuridica
    vm.pessoaJuridicaList = PessoaJuridicaService.pessoaJuridicaList;
    vm.listarPessoaJuridica = PessoaJuridicaService.listarPessoaJuridica;
    vm.listarPessoaJuridica();
	
	// Carregamento dos campos Enums
	
  }

})();