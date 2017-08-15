(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaJuridica');

    inheritance.controller('PessoaJuridicaModalController', PessoaJuridicaModalController);

    PessoaJuridicaModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'PessoaJuridicaFactory', 'PessoaJuridicaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador da entidade PessoaJuridica que estende as funcionalidades do BaseModalController. 
   */
  function PessoaJuridicaModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 PessoaJuridicaFactory, PessoaJuridicaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;

    vm.pessoaJuridica = vm.pessoaJuridica || new PessoaJuridicaFactory();

    $controller('BaseModalController', { vm: vm, factory: PessoaJuridicaFactory, entityName: 'PessoaJuridica', entity: vm.pessoaJuridica, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
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