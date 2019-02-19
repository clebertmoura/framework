(function () {
  'use strict';
    var inheritance = angular.module('app.celular');

    inheritance.controller('CelularModalController', CelularModalController);

    CelularModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CoreEnumsService',  'CelularFactory', 'CelularService', 'FilialFactory', 'SimcardFactory', 'ModeloCelularFactory', 'FilialService', 'SimcardService', 'ModeloCelularService'];


  /**
   * Controlador da entidade Celular que estende as funcionalidades do BaseModalController. 
   */
  function CelularModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	CoreEnumsService,  CelularFactory, CelularService, FilialFactory, SimcardFactory, ModeloCelularFactory, FilialService, SimcardService, ModeloCelularService) {

    var vm = this;

    vm.celular = vm.celular || new CelularFactory();

    $controller('BaseModalController', { vm: vm, factory: CelularFactory, entityName: 'Celular', entity: vm.celular, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
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
	
  }

})();