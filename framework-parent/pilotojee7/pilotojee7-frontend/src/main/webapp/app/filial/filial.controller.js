(function () {
  'use strict';
    var inheritance = angular.module('app.filial');

    inheritance.controller('FilialController', FilialController);

    FilialController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'FilialFactory', 'FilialService', 'PessoaJuridicaFactory', 'EmpresaFactory', 'PessoaJuridicaService', 'EmpresaService'];


  /**
   * Controlador da entidade Filial que estende as funcionalidades do BaseController. 
   */
  function FilialController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 FilialFactory, FilialService, PessoaJuridicaFactory, EmpresaFactory, PessoaJuridicaService, EmpresaService) {

    var vm = this;

    vm.filial = vm.filial || new FilialFactory();

    $controller('BaseController', { vm: vm, factory: FilialFactory, entityName: 'Filial', entity: vm.filial, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoaJuridica && vm.searchData.pessoaJuridica != null) {
    		restrictions.push({field: 'pessoaJuridica.id', operator: 'EQ', value: vm.searchData.pessoaJuridica.id});
    	}
		if (vm.searchData.empresa && vm.searchData.empresa != null) {
    		restrictions.push({field: 'empresa.id', operator: 'EQ', value: vm.searchData.empresa.id});
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
	// Lista de PessoaJuridica
    vm.pessoaJuridicaList = PessoaJuridicaService.pessoaJuridicaList;
    vm.listarPessoaJuridica = PessoaJuridicaService.listarPessoaJuridica;
    vm.listarPessoaJuridica();
	// Lista de Empresa
    vm.empresaList = EmpresaService.empresaList;
    vm.listarEmpresa = EmpresaService.listarEmpresa;
    vm.listarEmpresa();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();