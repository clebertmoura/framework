(function () {
  'use strict';
    var inheritance = angular.module('app.cliente');

    inheritance.controller('ClienteController', ClienteController);

    ClienteController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'ClienteFactory', 'ClienteService', 'PessoaFactory', 'PessoaService'];


  /**
   * Controlador da entidade Cliente que estende as funcionalidades do BaseController. 
   */
  function ClienteController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 ClienteFactory, ClienteService, PessoaFactory, PessoaService) {

    var vm = this;

    vm.cliente = vm.cliente || new ClienteFactory();

    $controller('BaseController', { vm: vm, factory: ClienteFactory, entityName: 'Cliente', entity: vm.cliente, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoa && vm.searchData.pessoa != null) {
    		restrictions.push({field: 'pessoa.id', operator: 'EQ', value: vm.searchData.pessoa.id});
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
	// Lista de Pessoa
    vm.pessoaList = PessoaService.pessoaList;
    vm.listarPessoa = PessoaService.listarPessoa;
    vm.listarPessoa();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();