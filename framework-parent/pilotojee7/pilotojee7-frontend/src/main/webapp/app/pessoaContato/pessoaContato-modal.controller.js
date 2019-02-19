(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaContato');

    inheritance.controller('PessoaContatoModalController', PessoaContatoModalController);

    PessoaContatoModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'PessoaContatoFactory', 'PessoaContatoService'];


  /**
   * Controlador da entidade PessoaContato que estende as funcionalidades do BaseModalController. 
   */
  function PessoaContatoModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 PessoaContatoFactory, PessoaContatoService) {

    var vm = this;

    vm.pessoaContato = vm.pessoaContato || new PessoaContatoFactory();

    $controller('BaseModalController', { vm: vm, factory: PessoaContatoFactory, entityName: 'PessoaContato', entity: vm.pessoaContato, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
  }

})();