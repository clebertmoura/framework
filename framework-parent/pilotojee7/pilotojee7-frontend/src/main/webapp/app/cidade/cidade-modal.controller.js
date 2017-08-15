(function () {
  'use strict';
    var inheritance = angular.module('app.cidade');

    inheritance.controller('CidadeModalController', CidadeModalController);

    CidadeModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'CidadeFactory', 'CidadeService', 'UfFactory', 'UfService'];


  /**
   * Controlador da entidade Cidade que estende as funcionalidades do BaseModalController. 
   */
  function CidadeModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 CidadeFactory, CidadeService, UfFactory, UfService) {

    var vm = this;

    vm.cidade = vm.cidade || new CidadeFactory();

    $controller('BaseModalController', { vm: vm, factory: CidadeFactory, entityName: 'Cidade', entity: vm.cidade, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Uf
    vm.ufList = UfService.ufList;
    vm.listarUf = UfService.listarUf;
    vm.listarUf();
	
	// Carregamento dos campos Enums
	
  }

})();