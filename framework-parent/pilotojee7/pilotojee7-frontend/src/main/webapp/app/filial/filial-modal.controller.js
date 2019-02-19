(function () {
  'use strict';
    var inheritance = angular.module('app.filial');

    inheritance.controller('FilialModalController', FilialModalController);

    FilialModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'FilialFactory', 'FilialService', 'PessoaJuridicaFactory', 'EmpresaFactory', 'PessoaJuridicaService', 'EmpresaService'];


  /**
   * Controlador da entidade Filial que estende as funcionalidades do BaseModalController. 
   */
  function FilialModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 FilialFactory, FilialService, PessoaJuridicaFactory, EmpresaFactory, PessoaJuridicaService, EmpresaService) {

    var vm = this;

    vm.filial = vm.filial || new FilialFactory();

    $controller('BaseModalController', { vm: vm, factory: FilialFactory, entityName: 'Filial', entity: vm.filial, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de PessoaJuridica
    vm.pessoaJuridicaList = PessoaJuridicaService.pessoaJuridicaList;
    vm.listarPessoaJuridica = PessoaJuridicaService.listarPessoaJuridica;
    vm.listarPessoaJuridica();
	// Lista de Empresa
    vm.empresaList = EmpresaService.empresaList;
    vm.listarEmpresa = EmpresaService.listarEmpresa;
    vm.listarEmpresa();
	
	// Carregamento dos campos Enums
	
  }

})();