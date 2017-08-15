(function () {
  'use strict';
    var inheritance = angular.module('app.tipoLogradouro');

    inheritance.controller('TipoLogradouroController', TipoLogradouroController);

    TipoLogradouroController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'TipoLogradouroFactory', 'TipoLogradouroService'];


  /**
   * Controlador da entidade TipoLogradouro que estende as funcionalidades do BaseController. 
   */
  function TipoLogradouroController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 TipoLogradouroFactory, TipoLogradouroService) {

    var vm = this;

    vm.tipoLogradouro = vm.tipoLogradouro || new TipoLogradouroFactory();

    $controller('BaseController', { vm: vm, factory: TipoLogradouroFactory, entityName: 'TipoLogradouro', entity: vm.tipoLogradouro, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.descricao && vm.searchData.descricao != "") {
    		restrictions.push({field: 'descricao', operator: 'LI', value: vm.searchData.descricao});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'descricao', order: 'ASC'});
		return orderings;
	};
	
	// Carregamento de relacionamentos
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();