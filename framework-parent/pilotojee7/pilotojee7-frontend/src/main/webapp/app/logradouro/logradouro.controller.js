(function () {
  'use strict';
    var inheritance = angular.module('app.logradouro');

    inheritance.controller('LogradouroController', LogradouroController);

    LogradouroController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'LogradouroFactory', 'LogradouroService', 'BairroFactory', 'TipoLogradouroFactory', 'BairroService', 'TipoLogradouroService'];


  /**
   * Controlador da entidade Logradouro que estende as funcionalidades do BaseController. 
   */
  function LogradouroController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 LogradouroFactory, LogradouroService, BairroFactory, TipoLogradouroFactory, BairroService, TipoLogradouroService) {

    var vm = this;

    vm.logradouro = vm.logradouro || new LogradouroFactory();

    $controller('BaseController', { vm: vm, factory: LogradouroFactory, entityName: 'Logradouro', entity: vm.logradouro, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.bairro && vm.searchData.bairro != null) {
    		restrictions.push({field: 'bairro.id', operator: 'EQ', value: vm.searchData.bairro.id});
    	}
		if (vm.searchData.tipoLogradouro && vm.searchData.tipoLogradouro != null) {
    		restrictions.push({field: 'tipoLogradouro.id', operator: 'EQ', value: vm.searchData.tipoLogradouro.id});
    	}
		if (vm.searchData.cep && vm.searchData.cep != "") {
    		restrictions.push({field: 'cep', operator: 'LI', value: vm.searchData.cep});
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
	// Lista de Bairro
    vm.bairroList = BairroService.bairroList;
    vm.listarBairro = BairroService.listarBairro;
    vm.listarBairro();
	// Lista de TipoLogradouro
    vm.tipoLogradouroList = TipoLogradouroService.tipoLogradouroList;
    vm.listarTipoLogradouro = TipoLogradouroService.listarTipoLogradouro;
    vm.listarTipoLogradouro();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();