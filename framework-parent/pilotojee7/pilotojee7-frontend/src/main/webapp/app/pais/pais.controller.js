(function () {
  'use strict';
    var inheritance = angular.module('app.pais');

    inheritance.controller('PaisController', PaisController);

    PaisController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'PaisFactory', 'PaisService', 'ImagemFactory', 'ImagemService'];


  /**
   * Controlador da entidade Pais que estende as funcionalidades do BaseController. 
   */
  function PaisController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 PaisFactory, PaisService, ImagemFactory, ImagemService) {

    var vm = this;

    vm.pais = vm.pais || new PaisFactory();

    $controller('BaseController', { vm: vm, factory: PaisFactory, entityName: 'Pais', entity: vm.pais, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.codigoOnuAlpha && vm.searchData.codigoOnuAlpha != "") {
    		restrictions.push({field: 'codigoOnuAlpha', operator: 'LI', value: vm.searchData.codigoOnuAlpha});
    	}
		if (vm.searchData.codigoOnu && vm.searchData.codigoOnu != "") {
    		restrictions.push({field: 'codigoOnu', operator: 'LI', value: vm.searchData.codigoOnu});
    	}
		if (vm.searchData.imagem && vm.searchData.imagem != null) {
    		restrictions.push({field: 'imagem.id', operator: 'EQ', value: vm.searchData.imagem.id});
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
	// Lista de Imagem
    vm.imagemList = ImagemService.imagemList;
    vm.listarImagem = ImagemService.listarImagem;
    vm.listarImagem();
	
	// Carregamento dos campos Enums
	
	
	// Modal para upload de imagens
 
  }

})();