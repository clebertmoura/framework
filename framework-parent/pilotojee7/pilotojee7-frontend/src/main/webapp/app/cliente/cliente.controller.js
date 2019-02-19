(function () {
  'use strict';
    var inheritance = angular.module('app.cliente');

    inheritance.controller('ClienteController', ClienteController);

    ClienteController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'ClienteFactory', 'ClienteService', 'PessoaFactory', 'ClienteUsuarioFactory', 'PessoaService', 'ClienteUsuarioService'];


  /**
   * Controlador da entidade Cliente que estende as funcionalidades do BaseController. 
   */
  function ClienteController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 ClienteFactory, ClienteService, PessoaFactory, ClienteUsuarioFactory, PessoaService, ClienteUsuarioService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: ClienteFactory, entityName: 'Cliente', $scope: $scope });
    
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
	vm.entity.clientesUsuarios = vm.entity.clientesUsuarios || [];
	vm.openModalClienteUsuario = openModalClienteUsuario;
    vm.addClienteUsuario = addClienteUsuario;
    vm.removeClienteUsuario = removeClienteUsuario;
    
    function openModalClienteUsuario() {
    	vm.modalClienteUsuario = $uibModal.open({
			templateUrl: 'app/clienteUsuario/modal.tpl.html',
			controller: 'ClienteUsuarioModalController',
			controllerAs: 'vmClienteUsuario',
			backdrop: false,
			scope: $scope
	    }).result.then(function(result) {
	    	vm.addClienteUsuario(result);
		});    	
    };	
    
    function addClienteUsuario(clienteUsuario) {
    	if (clienteUsuario && clienteUsuario != null) {
    		vm.entity.clientesUsuarios.push(clienteUsuario);
    	}
    };
    
    function removeClienteUsuario(index) {
    	vm.entity.clientesUsuarios.splice(index, 1);
    };
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();