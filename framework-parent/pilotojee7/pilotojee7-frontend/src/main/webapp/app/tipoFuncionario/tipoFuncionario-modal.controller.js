(function () {
  'use strict';
    var inheritance = angular.module('app.tipoFuncionario');

    inheritance.controller('TipoFuncionarioModalController', TipoFuncionarioModalController);

    TipoFuncionarioModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'TipoFuncionarioFactory', 'TipoFuncionarioService', 'EmpresaFactory', 'EmpresaService'];


  /**
   * Controlador da entidade TipoFuncionario que estende as funcionalidades do BaseModalController. 
   */
  function TipoFuncionarioModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 TipoFuncionarioFactory, TipoFuncionarioService, EmpresaFactory, EmpresaService) {

    var vm = this;

    vm.tipoFuncionario = vm.tipoFuncionario || new TipoFuncionarioFactory();

    $controller('BaseModalController', { vm: vm, factory: TipoFuncionarioFactory, entityName: 'TipoFuncionario', entity: vm.tipoFuncionario, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Empresa
    vm.empresaList = EmpresaService.empresaList;
    vm.listarEmpresa = EmpresaService.listarEmpresa;
    vm.listarEmpresa();
	
	// Carregamento dos campos Enums
	
  }

})();