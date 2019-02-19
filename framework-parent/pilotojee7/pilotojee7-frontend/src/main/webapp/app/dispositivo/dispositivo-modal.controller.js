(function () {
  'use strict';
    var inheritance = angular.module('app.dispositivo');

    inheritance.controller('DispositivoModalController', DispositivoModalController);

    DispositivoModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'DispositivoFactory', 'DispositivoService', 'KeycloakUsuarioFactory', 'KeycloakUsuarioService'];


  /**
   * Controlador da entidade Dispositivo que estende as funcionalidades do BaseModalController. 
   */
  function DispositivoModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 DispositivoFactory, DispositivoService, KeycloakUsuarioFactory, KeycloakUsuarioService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: DispositivoFactory, entityName: 'Dispositivo', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de KeycloakUsuario
    vm.keycloakUsuarioList = KeycloakUsuarioService.keycloakUsuarioList;
    vm.listarKeycloakUsuario = KeycloakUsuarioService.listarKeycloakUsuario;
    vm.listarKeycloakUsuario();
	
	// Carregamento dos campos Enums
	
  }

})();