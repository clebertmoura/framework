(function () {
  'use strict';
    var inheritance = angular.module('app.simcard');

    inheritance.controller('SimcardController', SimcardController);

    SimcardController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CoreEnumsService', 'CadastroEnumsService',  'SimcardFactory', 'SimcardService', 'OperadoraFactory', 'EmpresaFactory', 'OperadoraService', 'EmpresaService'];


  /**
   * Controlador da entidade Simcard que estende as funcionalidades do BaseController. 
   */
  function SimcardController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	CoreEnumsService, CadastroEnumsService,  SimcardFactory, SimcardService, OperadoraFactory, EmpresaFactory, OperadoraService, EmpresaService) {

    var vm = this;

    vm.simcard = vm.simcard || new SimcardFactory();

    $controller('BaseController', { vm: vm, factory: SimcardFactory, entityName: 'Simcard', entity: vm.simcard, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.operadora && vm.searchData.operadora != null) {
    		restrictions.push({field: 'operadora.id', operator: 'EQ', value: vm.searchData.operadora.id});
    	}
		if (vm.searchData.ddd && vm.searchData.ddd != "") {
    		restrictions.push({field: 'ddd', operator: 'LI', value: vm.searchData.ddd});
    	}
		if (vm.searchData.numero && vm.searchData.numero != "") {
    		restrictions.push({field: 'numero', operator: 'LI', value: vm.searchData.numero});
    	}
		if (vm.searchData.imei && vm.searchData.imei != "") {
    		restrictions.push({field: 'imei', operator: 'LI', value: vm.searchData.imei});
    	}
		if (vm.searchData.dataCompra && vm.searchData.dataCompra != null) {
    		restrictions.push({field: 'dataCompra', operator: 'EQ', value: vm.searchData.dataCompra});
    	}
		if (vm.searchData.dataNotaFiscal && vm.searchData.dataNotaFiscal != null) {
    		restrictions.push({field: 'dataNotaFiscal', operator: 'EQ', value: vm.searchData.dataNotaFiscal});
    	}
		if (vm.searchData.notaFiscal && vm.searchData.notaFiscal != "") {
    		restrictions.push({field: 'notaFiscal', operator: 'LI', value: vm.searchData.notaFiscal});
    	}
		if (vm.searchData.dataCancelamento && vm.searchData.dataCancelamento != null) {
    		restrictions.push({field: 'dataCancelamento', operator: 'EQ', value: vm.searchData.dataCancelamento});
    	}
		if (vm.searchData.habilitado && vm.searchData.habilitado != null) {
    		restrictions.push({field: 'habilitado', operator: 'EQ', value: vm.searchData.habilitado});
    	}
		if (vm.searchData.empresa && vm.searchData.empresa != null) {
    		restrictions.push({field: 'empresa.id', operator: 'EQ', value: vm.searchData.empresa.id});
    	}
		if (vm.searchData.usoSimcard && vm.searchData.usoSimcard != null) {
    		restrictions.push({field: 'usoSimcard', operator: 'EQ', value: vm.searchData.usoSimcard});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'id', order: 'ASC'});
		return orderings;
	};
	
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
	
	
	// Modal para upload de imagens
 
  }

})();