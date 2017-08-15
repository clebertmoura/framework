(function () {
  'use strict';
    var inheritance = angular.module('app.funcionario');

    inheritance.controller('FuncionarioModalController', FuncionarioModalController);

    FuncionarioModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'FuncionarioFactory', 'FuncionarioService', 'TipoFuncionarioFactory', 'FilialFactory', 'PessoaFisicaFactory', 'TipoFuncionarioService', 'FilialService', 'PessoaFisicaService'];


  /**
   * Controlador da entidade Funcionario que estende as funcionalidades do BaseModalController. 
   */
  function FuncionarioModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 FuncionarioFactory, FuncionarioService, TipoFuncionarioFactory, FilialFactory, PessoaFisicaFactory, TipoFuncionarioService, FilialService, PessoaFisicaService) {

    var vm = this;

    vm.funcionario = vm.funcionario || new FuncionarioFactory();

    $controller('BaseModalController', { vm: vm, factory: FuncionarioFactory, entityName: 'Funcionario', entity: vm.funcionario, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de TipoFuncionario
    vm.tipoFuncionarioList = TipoFuncionarioService.tipoFuncionarioList;
    vm.listarTipoFuncionario = TipoFuncionarioService.listarTipoFuncionario;
    vm.listarTipoFuncionario();
	// Lista de Filial
    vm.filialList = FilialService.filialList;
    vm.listarFilial = FilialService.listarFilial;
    vm.listarFilial();
	// Lista de PessoaFisica
    vm.pessoaFisicaList = PessoaFisicaService.pessoaFisicaList;
    vm.listarPessoaFisica = PessoaFisicaService.listarPessoaFisica;
    vm.listarPessoaFisica();
	
	// Carregamento dos campos Enums
	
  }

})();