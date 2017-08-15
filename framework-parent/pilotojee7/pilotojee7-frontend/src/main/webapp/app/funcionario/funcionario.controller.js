(function () {
  'use strict';
    var inheritance = angular.module('app.funcionario');

    inheritance.controller('FuncionarioController', FuncionarioController);

    FuncionarioController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'FuncionarioFactory', 'FuncionarioService', 'TipoFuncionarioFactory', 'FilialFactory', 'PessoaFisicaFactory', 'TipoFuncionarioService', 'FilialService', 'PessoaFisicaService'];


  /**
   * Controlador da entidade Funcionario que estende as funcionalidades do BaseController. 
   */
  function FuncionarioController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 FuncionarioFactory, FuncionarioService, TipoFuncionarioFactory, FilialFactory, PessoaFisicaFactory, TipoFuncionarioService, FilialService, PessoaFisicaService) {

    var vm = this;

    vm.funcionario = vm.funcionario || new FuncionarioFactory();

    $controller('BaseController', { vm: vm, factory: FuncionarioFactory, entityName: 'Funcionario', entity: vm.funcionario, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.tipoFuncionario && vm.searchData.tipoFuncionario != null) {
    		restrictions.push({field: 'tipoFuncionario.id', operator: 'EQ', value: vm.searchData.tipoFuncionario.id});
    	}
		if (vm.searchData.filial && vm.searchData.filial != null) {
    		restrictions.push({field: 'filial.id', operator: 'EQ', value: vm.searchData.filial.id});
    	}
		if (vm.searchData.pessoaFisica && vm.searchData.pessoaFisica != null) {
    		restrictions.push({field: 'pessoaFisica.id', operator: 'EQ', value: vm.searchData.pessoaFisica.id});
    	}
		if (vm.searchData.matricula && vm.searchData.matricula != "") {
    		restrictions.push({field: 'matricula', operator: 'LI', value: vm.searchData.matricula});
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
	
	
	// Modal para upload de imagens
 
  }

})();