(function () {
  'use strict';
    var inheritance = angular.module('app.pessoa');

    inheritance.controller('PessoaModalController', PessoaModalController);

    PessoaModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'PessoaFactory', 'PessoaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador da entidade Pessoa que estende as funcionalidades do BaseModalController. 
   */
  function PessoaModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 PessoaFactory, PessoaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;

    vm.pessoa = vm.pessoa || new PessoaFactory();

    $controller('BaseModalController', { vm: vm, factory: PessoaFactory, entityName: 'Pessoa', entity: vm.pessoa, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
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
	
  }

})();