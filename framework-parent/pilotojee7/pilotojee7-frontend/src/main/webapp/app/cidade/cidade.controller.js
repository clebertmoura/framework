(function () {
  'use strict';
    var inheritance = angular.module('app.cidade');

    inheritance.controller('CidadeController', CidadeController);

    CidadeController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'CidadeFactory', 'CidadeService', 'UfFactory', 'UfService'];


  /**
   * Controlador da entidade Cidade que estende as funcionalidades do BaseController. 
   */
  function CidadeController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 CidadeFactory, CidadeService, UfFactory, UfService) {

    var vm = this;

    vm.cidade = vm.cidade || new CidadeFactory();

    $controller('BaseController', { vm: vm, factory: CidadeFactory, entityName: 'Cidade', entity: vm.cidade, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.uf && vm.searchData.uf != null) {
    		restrictions.push({field: 'uf.id', operator: 'EQ', value: vm.searchData.uf.id});
    	}
		if (vm.searchData.codigoIbge && vm.searchData.codigoIbge != "") {
    		restrictions.push({field: 'codigoIbge', operator: 'LI', value: vm.searchData.codigoIbge});
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
	// Lista de Uf
    vm.ufList = UfService.ufList;
    vm.listarUf = UfService.listarUf;
    vm.listarUf();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();