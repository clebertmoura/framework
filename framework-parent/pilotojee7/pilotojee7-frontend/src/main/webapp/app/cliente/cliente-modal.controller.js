(function () {
  'use strict';
    var inheritance = angular.module('app.cliente');

    inheritance.controller('ClienteModalController', ClienteModalController);

    ClienteModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'ClienteFactory', 'ClienteService', 'PessoaFactory', 'ClienteUsuarioFactory', 'PessoaService'];


  /**
   * Controlador da entidade Cliente que estende as funcionalidades do BaseModalController. 
   */
  function ClienteModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 ClienteFactory, ClienteService, PessoaFactory, ClienteUsuarioFactory, PessoaService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: ClienteFactory, entityName: 'Cliente', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Pessoa
    vm.pessoaList = PessoaService.pessoaList;
    vm.listarPessoa = PessoaService.listarPessoa;
    vm.listarPessoa();
	
	// Carregamento dos campos Enums
	
  }

})();