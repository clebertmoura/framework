(function () {
  'use strict';
    var inheritance = angular.module('app.pessoa');

    inheritance.controller('PessoaController', PessoaController);

    PessoaController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'PessoaFactory', 'PessoaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador da entidade Pessoa que estende as funcionalidades do BaseController. 
   */
  function PessoaController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 PessoaFactory, PessoaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;

    vm.pessoa = vm.pessoa || new PessoaFactory();

    $controller('BaseController', { vm: vm, factory: PessoaFactory, entityName: 'Pessoa', entity: vm.pessoa, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.endereco && vm.searchData.endereco != null) {
    		restrictions.push({field: 'endereco.id', operator: 'EQ', value: vm.searchData.endereco.id});
    	}
		if (vm.searchData.nomePessoa && vm.searchData.nomePessoa != "") {
    		restrictions.push({field: 'nomePessoa', operator: 'LI', value: vm.searchData.nomePessoa});
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
	// Lista de Endereco
    vm.enderecoList = EnderecoService.enderecoList;
    vm.listarEndereco = EnderecoService.listarEndereco;
    vm.listarEndereco();
	vm.entity.contatos = vm.entity.contatos || [];
	vm.openModalPessoaContato = openModalPessoaContato;
    vm.addPessoaContato = addPessoaContato;
    vm.removePessoaContato = removePessoaContato;
    
    function openModalPessoaContato() {
    	vm.modalPessoaContato = $uibModal.open({
			templateUrl: 'app/pessoaContato/modal.tpl.html',
			controller: 'PessoaContatoModalController',
			controllerAs: 'vmPessoaContato',
			backdrop: false,
			scope: $scope
	    }).result.then(function(result) {
	    	vm.addPessoaContato(result);
		});    	
    };	
    
    function addPessoaContato(pessoaContato) {
    	if (pessoaContato && pessoaContato != null) {
    		vm.entity.contatos.push(pessoaContato);
    	}
    };
    
    function removePessoaContato(index) {
    	vm.entity.contatos.splice(index, 1);
    };
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();