(function () {
  'use strict';
    var inheritance = angular.module('app.celular');

    inheritance.controller('CelularController', CelularController);

    CelularController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CoreEnumsService',  'CelularFactory', 'CelularService', 'FilialFactory', 'SimcardFactory', 'ModeloCelularFactory', 'FilialService', 'SimcardService', 'ModeloCelularService'];


  /**
   * Controlador da entidade Celular que estende as funcionalidades do BaseController. 
   */
  function CelularController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	CoreEnumsService,  CelularFactory, CelularService, FilialFactory, SimcardFactory, ModeloCelularFactory, FilialService, SimcardService, ModeloCelularService) {

    var vm = this;

    vm.celular = vm.celular || new CelularFactory();

    $controller('BaseController', { vm: vm, factory: CelularFactory, entityName: 'Celular', entity: vm.celular, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.trackingAtivo && vm.searchData.trackingAtivo != null) {
    		restrictions.push({field: 'trackingAtivo', operator: 'EQ', value: vm.searchData.trackingAtivo});
    	}
		if (vm.searchData.imei && vm.searchData.imei != "") {
    		restrictions.push({field: 'imei', operator: 'LI', value: vm.searchData.imei});
    	}
		if (vm.searchData.serial && vm.searchData.serial != "") {
    		restrictions.push({field: 'serial', operator: 'LI', value: vm.searchData.serial});
    	}
		if (vm.searchData.filial && vm.searchData.filial != null) {
    		restrictions.push({field: 'filial.id', operator: 'EQ', value: vm.searchData.filial.id});
    	}
		if (vm.searchData.simcard && vm.searchData.simcard != null) {
    		restrictions.push({field: 'simcard.id', operator: 'EQ', value: vm.searchData.simcard.id});
    	}
		if (vm.searchData.versao && vm.searchData.versao != "") {
    		restrictions.push({field: 'versao', operator: 'LI', value: vm.searchData.versao});
    	}
		if (vm.searchData.modelo && vm.searchData.modelo != null) {
    		restrictions.push({field: 'modelo.id', operator: 'EQ', value: vm.searchData.modelo.id});
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
	// Lista de Filial
    vm.filialList = FilialService.filialList;
    vm.listarFilial = FilialService.listarFilial;
    vm.listarFilial();
	// Lista de Simcard
    vm.simcardList = SimcardService.simcardList;
    vm.listarSimcard = SimcardService.listarSimcard;
    vm.listarSimcard();
	// Lista de ModeloCelular
    vm.modeloCelularList = ModeloCelularService.modeloCelularList;
    vm.listarModeloCelular = ModeloCelularService.listarModeloCelular;
    vm.listarModeloCelular();
	
	// Carregamento dos campos Enums
    vm.trackingAtivoList = CoreEnumsService.trackingAtivoList;
	
	
	// Modal para upload de imagens
 
  }

})();