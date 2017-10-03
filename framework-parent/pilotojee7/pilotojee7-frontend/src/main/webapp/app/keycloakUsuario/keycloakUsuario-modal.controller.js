(function () {
  'use strict';
    var inheritance = angular.module('app.keycloakUsuario');

    inheritance.controller('KeycloakUsuarioModalController', KeycloakUsuarioModalController);

    KeycloakUsuarioModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'KeycloakUsuarioFactory', 'KeycloakUsuarioService', 'KeycloakGrupoFactory', 'ImagemFactory', 'KeycloakGrupoService', 'ImagemService'];


  /**
   * Controlador da entidade KeycloakUsuario que estende as funcionalidades do BaseModalController. 
   */
  function KeycloakUsuarioModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 KeycloakUsuarioFactory, KeycloakUsuarioService, KeycloakGrupoFactory, ImagemFactory, KeycloakGrupoService, ImagemService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: KeycloakUsuarioFactory, entityName: 'KeycloakUsuario', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de KeycloakGrupo
    vm.keycloakGrupoList = KeycloakGrupoService.keycloakGrupoList;
    vm.listarKeycloakGrupo = KeycloakGrupoService.listarKeycloakGrupo;
    vm.listarKeycloakGrupo();
	// Lista de Imagem
    vm.imagemList = ImagemService.imagemList;
    vm.listarImagem = ImagemService.listarImagem;
    vm.listarImagem();
	
	// Carregamento dos campos Enums
	
  }

})();