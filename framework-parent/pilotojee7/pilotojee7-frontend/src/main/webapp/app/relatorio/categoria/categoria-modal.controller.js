(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.categoria');

    inheritance.controller('CategoriaModalController', CategoriaModalController);

    CategoriaModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'CategoriaFactory', 'CategoriaService'];


  /**
   * Controlador da entidade Categoria que estende as funcionalidades do BaseModalController. 
   */
  function CategoriaModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 CategoriaFactory, CategoriaService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: CategoriaFactory, entityName: 'Categoria', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
  }

})();