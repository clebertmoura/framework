(function () {
  'use strict';
    var inheritance = angular.module('app.simcard');

    inheritance.controller('SimcardModalController', SimcardModalController);

    SimcardModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CoreEnumsService', 'CadastroEnumsService',  'SimcardFactory', 'SimcardService', 'OperadoraFactory', 'EmpresaFactory', 'OperadoraService', 'EmpresaService'];


  /**
   * Controlador da entidade Simcard que estende as funcionalidades do BaseModalController. 
   */
  function SimcardModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	CoreEnumsService, CadastroEnumsService,  SimcardFactory, SimcardService, OperadoraFactory, EmpresaFactory, OperadoraService, EmpresaService) {

    var vm = this;

    vm.simcard = vm.simcard || new SimcardFactory();

    $controller('BaseModalController', { vm: vm, factory: SimcardFactory, entityName: 'Simcard', entity: vm.simcard, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Operadora
    vm.operadoraList = OperadoraService.operadoraList;
    vm.listarOperadora = OperadoraService.listarOperadora;
    vm.listarOperadora();
	// Lista de Empresa
    vm.empresaList = EmpresaService.empresaList;
    vm.listarEmpresa = EmpresaService.listarEmpresa;
    vm.listarEmpresa();
	
	// Carregamento dos campos Enums
    vm.habilitadoList = CoreEnumsService.habilitadoList;
    vm.usoSimcardList = CadastroEnumsService.usoSimcardList;
	
  }

})();