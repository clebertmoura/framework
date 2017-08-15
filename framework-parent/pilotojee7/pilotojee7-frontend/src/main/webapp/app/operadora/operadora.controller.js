(function () {
  'use strict';
    var inheritance = angular.module('app.operadora');

    inheritance.controller('OperadoraController', OperadoraController);

    OperadoraController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'OperadoraFactory', 'OperadoraService'];


  /**
   * Controlador da entidade Operadora que estende as funcionalidades do BaseController. 
   */
  function OperadoraController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 OperadoraFactory, OperadoraService) {

    var vm = this;

    vm.operadora = vm.operadora || new OperadoraFactory();

    $controller('BaseController', { vm: vm, factory: OperadoraFactory, entityName: 'Operadora', entity: vm.operadora, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
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
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();