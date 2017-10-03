(function () {
  'use strict';
    var inheritance = angular.module('app.keycloakGrupo');

    inheritance.controller('KeycloakGrupoController', KeycloakGrupoController);

    KeycloakGrupoController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'KeycloakGrupoFactory', 'KeycloakGrupoService', 'KeycloakUsuarioFactory', 'KeycloakUsuarioService'];


  /**
   * Controlador da entidade KeycloakGrupo que estende as funcionalidades do BaseController. 
   */
  function KeycloakGrupoController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 KeycloakGrupoFactory, KeycloakGrupoService, KeycloakUsuarioFactory, KeycloakUsuarioService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: KeycloakGrupoFactory, entityName: 'KeycloakGrupo', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.idKeycloak && vm.searchData.idKeycloak != "") {
    		restrictions.push({field: 'idKeycloak', operator: 'LI', value: vm.searchData.idKeycloak});
    	}
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'nome', order: 'ASC'});
		return orderings;
	};
	
	// Carregamento de relacionamentos
	// Lista de KeycloakUsuario
    vm.keycloakUsuarioList = KeycloakUsuarioService.keycloakUsuarioList;
    vm.listarKeycloakUsuario = KeycloakUsuarioService.listarKeycloakUsuario;
    vm.listarKeycloakUsuario();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();