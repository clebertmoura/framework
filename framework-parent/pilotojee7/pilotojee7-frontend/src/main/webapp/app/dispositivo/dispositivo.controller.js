(function () {
  'use strict';
    var inheritance = angular.module('app.dispositivo');

    inheritance.controller('DispositivoController', DispositivoController);

    DispositivoController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'DispositivoFactory', 'DispositivoService', 'KeycloakUsuarioFactory', 'KeycloakUsuarioService'];


  /**
   * Controlador da entidade Dispositivo que estende as funcionalidades do BaseController. 
   */
  function DispositivoController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 DispositivoFactory, DispositivoService, KeycloakUsuarioFactory, KeycloakUsuarioService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: DispositivoFactory, entityName: 'Dispositivo', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.serial && vm.searchData.serial != "") {
    		restrictions.push({field: 'serial', operator: 'LI', value: vm.searchData.serial});
    	}
		if (vm.searchData.versao && vm.searchData.versao != "") {
    		restrictions.push({field: 'versao', operator: 'LI', value: vm.searchData.versao});
    	}
		if (vm.searchData.cordova && vm.searchData.cordova != "") {
    		restrictions.push({field: 'cordova', operator: 'LI', value: vm.searchData.cordova});
    	}
		if (vm.searchData.modelo && vm.searchData.modelo != "") {
    		restrictions.push({field: 'modelo', operator: 'LI', value: vm.searchData.modelo});
    	}
		if (vm.searchData.platform && vm.searchData.platform != "") {
    		restrictions.push({field: 'platform', operator: 'LI', value: vm.searchData.platform});
    	}
		if (vm.searchData.uuid && vm.searchData.uuid != "") {
    		restrictions.push({field: 'uuid', operator: 'LI', value: vm.searchData.uuid});
    	}
		if (vm.searchData.manufacturer && vm.searchData.manufacturer != "") {
    		restrictions.push({field: 'manufacturer', operator: 'LI', value: vm.searchData.manufacturer});
    	}
		if (vm.searchData.isVirtual && vm.searchData.isVirtual != null) {
    		restrictions.push({field: 'isVirtual', operator: 'EQ', value: vm.searchData.isVirtual});
    	}
		if (vm.searchData.ddd && vm.searchData.ddd != "") {
    		restrictions.push({field: 'ddd', operator: 'LI', value: vm.searchData.ddd});
    	}
		if (vm.searchData.numero && vm.searchData.numero != "") {
    		restrictions.push({field: 'numero', operator: 'LI', value: vm.searchData.numero});
    	}
		if (vm.searchData.keycloakUsuario && vm.searchData.keycloakUsuario != null) {
    		restrictions.push({field: 'keycloakUsuario.id', operator: 'EQ', value: vm.searchData.keycloakUsuario.id});
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
	// Lista de KeycloakUsuario
    vm.keycloakUsuarioList = KeycloakUsuarioService.keycloakUsuarioList;
    vm.listarKeycloakUsuario = KeycloakUsuarioService.listarKeycloakUsuario;
    vm.listarKeycloakUsuario();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();