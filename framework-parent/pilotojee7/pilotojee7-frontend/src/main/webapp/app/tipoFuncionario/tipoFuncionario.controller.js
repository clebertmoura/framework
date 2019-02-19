(function () {
  'use strict';
    var inheritance = angular.module('app.tipoFuncionario');

    inheritance.controller('TipoFuncionarioController', TipoFuncionarioController);

    TipoFuncionarioController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'TipoFuncionarioFactory', 'TipoFuncionarioService', 'EmpresaFactory', 'EmpresaService'];


  /**
   * Controlador da entidade TipoFuncionario que estende as funcionalidades do BaseController. 
   */
  function TipoFuncionarioController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 TipoFuncionarioFactory, TipoFuncionarioService, EmpresaFactory, EmpresaService) {

    var vm = this;

    vm.tipoFuncionario = vm.tipoFuncionario || new TipoFuncionarioFactory();

    $controller('BaseController', { vm: vm, factory: TipoFuncionarioFactory, entityName: 'TipoFuncionario', entity: vm.tipoFuncionario, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.descricao && vm.searchData.descricao != "") {
    		restrictions.push({field: 'descricao', operator: 'LI', value: vm.searchData.descricao});
    	}
		if (vm.searchData.empresa && vm.searchData.empresa != null) {
    		restrictions.push({field: 'empresa.id', operator: 'EQ', value: vm.searchData.empresa.id});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'descricao', order: 'ASC'});
		return orderings;
	};
	
	// Carregamento de relacionamentos
	// Lista de Empresa
    vm.empresaList = EmpresaService.empresaList;
    vm.listarEmpresa = EmpresaService.listarEmpresa;
    vm.listarEmpresa();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();