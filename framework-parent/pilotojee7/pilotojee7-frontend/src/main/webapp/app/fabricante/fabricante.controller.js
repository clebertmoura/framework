(function () {
  'use strict';
    var inheritance = angular.module('app.fabricante');

    inheritance.controller('FabricanteController', FabricanteController);

    FabricanteController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'FabricanteFactory', 'FabricanteService', 'PessoaJuridicaFactory', 'PessoaJuridicaService'];


  /**
   * Controlador da entidade Fabricante que estende as funcionalidades do BaseController. 
   */
  function FabricanteController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 FabricanteFactory, FabricanteService, PessoaJuridicaFactory, PessoaJuridicaService) {

    var vm = this;

    vm.fabricante = vm.fabricante || new FabricanteFactory();

    $controller('BaseController', { vm: vm, factory: FabricanteFactory, entityName: 'Fabricante', entity: vm.fabricante, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoaJuridica && vm.searchData.pessoaJuridica != null) {
    		restrictions.push({field: 'pessoaJuridica.id', operator: 'EQ', value: vm.searchData.pessoaJuridica.id});
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
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();