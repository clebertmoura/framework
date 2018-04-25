(function () {
  'use strict';
    var inheritance = angular.module('app.keycloakUsuario');

    inheritance.controller('KeycloakUsuarioController', KeycloakUsuarioController);

    KeycloakUsuarioController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'KeycloakUsuarioFactory', 'KeycloakUsuarioService', 'KeycloakGrupoFactory', 'ImagemFactory', 'KeycloakGrupoService', 'ImagemService'];


  /**
   * Controlador da entidade KeycloakUsuario que estende as funcionalidades do BaseController. 
   */
  function KeycloakUsuarioController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 KeycloakUsuarioFactory, KeycloakUsuarioService, KeycloakGrupoFactory, ImagemFactory, KeycloakGrupoService, ImagemService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: KeycloakUsuarioFactory, entityName: 'KeycloakUsuario', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.primeiroNome && vm.searchData.primeiroNome != "") {
    		restrictions.push({field: 'primeiroNome', operator: 'LI', value: vm.searchData.primeiroNome});
    	}
		if (vm.searchData.ultimoNome && vm.searchData.ultimoNome != "") {
    		restrictions.push({field: 'ultimoNome', operator: 'LI', value: vm.searchData.ultimoNome});
    	}
		if (vm.searchData.email && vm.searchData.email != "") {
    		restrictions.push({field: 'email', operator: 'LI', value: vm.searchData.email});
    	}
		if (vm.searchData.login && vm.searchData.login != "") {
    		restrictions.push({field: 'login', operator: 'LI', value: vm.searchData.login});
    	}
		if (vm.searchData.idKeycloak && vm.searchData.idKeycloak != "") {
    		restrictions.push({field: 'idKeycloak', operator: 'LI', value: vm.searchData.idKeycloak});
    	}
		if (vm.searchData.imagem && vm.searchData.imagem != null) {
    		restrictions.push({field: 'imagem.id', operator: 'EQ', value: vm.searchData.imagem.id});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'id', order: 'ASC'});
		return orderings;
	};
	
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
	
	
	// Modal para upload de imagens
 
  }

})();