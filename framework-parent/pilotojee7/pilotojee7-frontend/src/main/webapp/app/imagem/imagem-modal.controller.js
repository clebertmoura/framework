(function () {
  'use strict';
    var inheritance = angular.module('app.imagem');

    inheritance.controller('ImagemModalController', ImagemModalController);

    ImagemModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'ImagemFactory', 'ImagemService'];


  /**
   * Controlador da entidade Imagem que estende as funcionalidades do BaseModalController. 
   */
  function ImagemModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 ImagemFactory, ImagemService) {

    var vm = this;

    vm.imagem = vm.imagem || new ImagemFactory();

    $controller('BaseModalController', { vm: vm, factory: ImagemFactory, entityName: 'Imagem', entity: vm.imagem, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
  }

})();