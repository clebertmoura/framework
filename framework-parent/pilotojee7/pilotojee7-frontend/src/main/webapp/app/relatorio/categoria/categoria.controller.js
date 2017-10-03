(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.categoria');

    inheritance.controller('CategoriaController', CategoriaController);

    CategoriaController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'CategoriaFactory', 'CategoriaService'];


  /**
   * Controlador da entidade Categoria que estende as funcionalidades do BaseController. 
   */
  function CategoriaController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 CategoriaFactory, CategoriaService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: CategoriaFactory, entityName: 'Categoria', $scope: $scope });
    
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