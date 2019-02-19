(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaContato');

    inheritance.controller('PessoaContatoController', PessoaContatoController);

    PessoaContatoController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'PessoaContatoFactory', 'PessoaContatoService'];


  /**
   * Controlador da entidade PessoaContato que estende as funcionalidades do BaseController. 
   */
  function PessoaContatoController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 PessoaContatoFactory, PessoaContatoService) {

    var vm = this;

    vm.pessoaContato = vm.pessoaContato || new PessoaContatoFactory();

    $controller('BaseController', { vm: vm, factory: PessoaContatoFactory, entityName: 'PessoaContato', entity: vm.pessoaContato, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoa && vm.searchData.pessoa != null) {
    		restrictions.push({field: 'pessoa.id', operator: 'EQ', value: vm.searchData.pessoa.id});
    	}
		if (vm.searchData.email && vm.searchData.email != "") {
    		restrictions.push({field: 'email', operator: 'LI', value: vm.searchData.email});
    	}
		if (vm.searchData.ddd && vm.searchData.ddd != "") {
    		restrictions.push({field: 'ddd', operator: 'LI', value: vm.searchData.ddd});
    	}
		if (vm.searchData.numero && vm.searchData.numero != "") {
    		restrictions.push({field: 'numero', operator: 'LI', value: vm.searchData.numero});
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
	
	// Carregamento dos campos Enums
	
	// Modal para upload de imagens
 
  }

})();