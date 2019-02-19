(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaFisica');

    inheritance.controller('PessoaFisicaModalController', PessoaFisicaModalController);

    PessoaFisicaModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CadastroEnumsService',  'PessoaFisicaFactory', 'PessoaFisicaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador da entidade PessoaFisica que estende as funcionalidades do BaseModalController. 
   */
  function PessoaFisicaModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	CadastroEnumsService,  PessoaFisicaFactory, PessoaFisicaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;

    vm.pessoaFisica = vm.pessoaFisica || new PessoaFisicaFactory();

    $controller('BaseModalController', { vm: vm, factory: PessoaFisicaFactory, entityName: 'PessoaFisica', entity: vm.pessoaFisica, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
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
    vm.generoList = CadastroEnumsService.generoList;
	
  }

})();