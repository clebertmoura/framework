(function () {
  'use strict';
    var inheritance = angular.module('app.keycloakGrupo');

    inheritance.controller('KeycloakGrupoModalController', KeycloakGrupoModalController);

    KeycloakGrupoModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'KeycloakGrupoFactory', 'KeycloakGrupoService', 'KeycloakUsuarioFactory', 'KeycloakUsuarioService'];


  /**
   * Controlador da entidade KeycloakGrupo que estende as funcionalidades do BaseModalController. 
   */
  function KeycloakGrupoModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 KeycloakGrupoFactory, KeycloakGrupoService, KeycloakUsuarioFactory, KeycloakUsuarioService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: KeycloakGrupoFactory, entityName: 'KeycloakGrupo', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de KeycloakUsuario
    vm.keycloakUsuarioList = KeycloakUsuarioService.keycloakUsuarioList;
    vm.listarKeycloakUsuario = KeycloakUsuarioService.listarKeycloakUsuario;
    vm.listarKeycloakUsuario();
	
	// Carregamento dos campos Enums
	
  }

})();