(function () {
  'use strict';
    var inheritance = angular.module('app.pais');

    inheritance.controller('PaisModalController', PaisModalController);

    PaisModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'PaisFactory', 'PaisService', 'ImagemFactory', 'ImagemService'];


  /**
   * Controlador da entidade Pais que estende as funcionalidades do BaseModalController. 
   */
  function PaisModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 PaisFactory, PaisService, ImagemFactory, ImagemService) {

    var vm = this;

    vm.pais = vm.pais || new PaisFactory();

    $controller('BaseModalController', { vm: vm, factory: PaisFactory, entityName: 'Pais', entity: vm.pais, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Imagem
    vm.imagemList = ImagemService.imagemList;
    vm.listarImagem = ImagemService.listarImagem;
    vm.listarImagem();
	
	// Carregamento dos campos Enums
	
  }

})();