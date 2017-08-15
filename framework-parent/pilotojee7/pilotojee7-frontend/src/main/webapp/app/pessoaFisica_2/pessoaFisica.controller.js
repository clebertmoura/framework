(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaFisica');

    inheritance.controller('PessoaFisicaController', PessoaFisicaController);

    PessoaFisicaController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', 'FlashFactory', 'CadastroEnumsFactory',  'PessoaFisicaFactory', 'PessoaFisicaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador do Processo que estende as funcionalidades do BaseController. 
   */
  function PessoaFisicaController($controller, $routeParams, $rootScope, $scope, $filter, $location, FlashFactory, 
  	CadastroEnumsFactory,  PessoaFisicaFactory, PessoaFisicaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;

    vm.pessoaFisica = vm.pessoaFisica || {};

    $controller('BaseController', { vm: vm, factory: PessoaFisicaFactory, restrictions: getRestrictions(), entityName: 'PessoaFisica', entity: vm.pessoaFisica });
    
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
		if (vm.searchData.cpf && vm.searchData.cpf != "") {
    		restrictions.push({field: 'cpf', operator: 'LI', value: vm.searchData.cpf});
    	}
		if (vm.searchData.dataNascimento && vm.searchData.dataNascimento != null) {
    		restrictions.push({field: 'dataNascimento', operator: 'EQ', value: vm.searchData.dataNascimento});
    	}
		if (vm.searchData.rg && vm.searchData.rg != "") {
    		restrictions.push({field: 'rg', operator: 'LI', value: vm.searchData.rg});
    	}
		if (vm.searchData.nomeMae && vm.searchData.nomeMae != "") {
    		restrictions.push({field: 'nomeMae', operator: 'LI', value: vm.searchData.nomeMae});
    	}
		if (vm.searchData.nomePai && vm.searchData.nomePai != "") {
    		restrictions.push({field: 'nomePai', operator: 'LI', value: vm.searchData.nomePai});
    	}
		if (vm.searchData.genero && vm.searchData.genero != null) {
    		restrictions.push({field: 'genero', operator: 'EQ', value: vm.searchData.genero});
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
	// Lista de PessoaContato
    vm.pessoaContatoList = PessoaContatoService.pessoaContatoList;
    vm.listarPessoaContato = PessoaContatoService.listarPessoaContato;
    vm.listarPessoaContato();
	
	// Carregamento dos campos Enums
	vm.generoList = vm.generoList || [];
    CadastroEnumsFactory.listGenero(function(items){
        vm.generoList = items;
    });
	
	
	// Modal para upload de imagens
 
  }

})();