(function () {
  'use strict';
    var inheritance = angular.module('app.bairro');

    inheritance.controller('BairroController', BairroController);

    BairroController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'BairroFactory', 'BairroService', 'CidadeFactory', 'CidadeService'];


  /**
   * Controlador da entidade Bairro que estende as funcionalidades do BaseController. 
   */
  function BairroController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 BairroFactory, BairroService, CidadeFactory, CidadeService) {

    var vm = this;

    vm.bairro = vm.bairro || new BairroFactory();

    $controller('BaseController', { vm: vm, factory: BairroFactory, entityName: 'Bairro', entity: vm.bairro, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.cidade && vm.searchData.cidade != null) {
    		restrictions.push({field: 'cidade.id', operator: 'EQ', value: vm.searchData.cidade.id});
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
	// Lista de Cidade
    vm.cidadeList = CidadeService.cidadeList;
    vm.listarCidade = CidadeService.listarCidade;
    vm.listarCidade();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();