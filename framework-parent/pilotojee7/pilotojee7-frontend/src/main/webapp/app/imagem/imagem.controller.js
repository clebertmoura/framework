(function () {
  'use strict';
    var inheritance = angular.module('app.imagem');

    inheritance.controller('ImagemController', ImagemController);

    ImagemController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'ImagemFactory', 'ImagemService'];


  /**
   * Controlador da entidade Imagem que estende as funcionalidades do BaseController. 
   */
  function ImagemController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 ImagemFactory, ImagemService) {

    var vm = this;

    vm.imagem = vm.imagem || new ImagemFactory();

    $controller('BaseController', { vm: vm, factory: ImagemFactory, entityName: 'Imagem', entity: vm.imagem, $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.contentType && vm.searchData.contentType != "") {
    		restrictions.push({field: 'contentType', operator: 'LI', value: vm.searchData.contentType});
    	}
		if (vm.searchData.fileExtension && vm.searchData.fileExtension != "") {
    		restrictions.push({field: 'fileExtension', operator: 'LI', value: vm.searchData.fileExtension});
    	}
		if (vm.searchData.data && vm.searchData.data != null) {
    		restrictions.push({field: 'data', operator: 'EQ', value: vm.searchData.data});
    	}
		if (vm.searchData.length && vm.searchData.length != null) {
    		restrictions.push({field: 'length', operator: 'EQ', value: vm.searchData.length});
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