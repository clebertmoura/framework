(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.relatorioFiltro');

    inheritance.controller('RelatorioFiltroController', RelatorioFiltroController);

    RelatorioFiltroController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CadastroEnumsResource',  'RelatorioFiltroFactory', 'RelatorioFiltroService', 'RelatorioFactory', 'FiltroFactory', 'RelatorioService', 'FiltroService'];


  /**
   * Controlador da entidade RelatorioFiltro que estende as funcionalidades do BaseController. 
   */
  function RelatorioFiltroController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
		  CadastroEnumsResource,  RelatorioFiltroFactory, RelatorioFiltroService, RelatorioFactory, FiltroFactory, RelatorioService, FiltroService) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: RelatorioFiltroFactory, entityName: 'RelatorioFiltro', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.rotulo && vm.searchData.rotulo != "") {
    		restrictions.push({field: 'rotulo', operator: 'LI', value: vm.searchData.rotulo});
    	}
		if (vm.searchData.ordem && vm.searchData.ordem != null) {
    		restrictions.push({field: 'ordem', operator: 'EQ', value: vm.searchData.ordem});
    	}
		if (vm.searchData.relatorio && vm.searchData.relatorio != null) {
    		restrictions.push({field: 'relatorio.id', operator: 'EQ', value: vm.searchData.relatorio.id});
    	}
		if (vm.searchData.filtro && vm.searchData.filtro != null) {
    		restrictions.push({field: 'filtro.id', operator: 'EQ', value: vm.searchData.filtro.id});
    	}
		if (vm.searchData.permissao && vm.searchData.permissao != "") {
    		restrictions.push({field: 'permissao', operator: 'LI', value: vm.searchData.permissao});
    	}
		if (vm.searchData.habilitado && vm.searchData.habilitado != null) {
    		restrictions.push({field: 'habilitado', operator: 'EQ', value: vm.searchData.habilitado});
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
	// Lista de Relatorio
    vm.relatorioList = RelatorioService.relatorioList;
    vm.listarRelatorio = RelatorioService.listarRelatorio;
    vm.listarRelatorio();
	// Lista de Filtro
    vm.filtroList = FiltroService.filtroList;
    vm.listarFiltro = FiltroService.listarFiltro;
    vm.listarFiltro();
    // Lista de RelatorioFiltro
    vm.relatorioFiltroList = RelatorioFiltroService.filtroList;
    vm.listarRelatorioFiltro = RelatorioFiltroService.listarRelatorioFiltro;
    vm.listarRelatorioFiltro();
	
	// Carregamento dos campos Enums
    vm.habilitadoList = vm.habilitadoList || [];
    CadastroEnumsResource.listSimNao(function (items) {
        vm.habilitadoList = items;
    });
	
    vm.entity.filhos = vm.entity.filhos || [];
	vm.openModalRelatorioFiltro = openModalRelatorioFiltro;
    vm.addRelatorioFiltro = addRelatorioFiltro;
    vm.removeRelatorioFiltro = removeRelatorioFiltro;
    
    function openModalRelatorioFiltro() {
    	vm.modalRelatorioFiltro = $uibModal.open({
			templateUrl: 'app/relatorio/relatorioRelatorioFiltro/modal.tpl.html',
			controller: 'RelatorioFiltroModalController',
			controllerAs: 'vmRelatorioFiltro',
			backdrop: false,
			scope: $scope
	    }).result.then(function(result) {
	    	vm.addRelatorioFiltro(result);
		});    	
    };	
    
    function addRelatorioFiltro(relatorioFiltro) {
    	if (relatorioFiltro && relatorioFiltro != null) {
    		vm.entity.filhos.push(relatorioFiltro);
    	}
    };
    
    function removeRelatorioFiltro(index) {
    	vm.entity.filhos.splice(index, 1);
    };
	
	// Modal para upload de imagens
    
    /**
	 * Monitora o evento para carregamento da entidade, quando o controller de 
	 * relatorio é embarcado em outro controller.
	 */
    $scope.$on('relatorio:loadEntity', function (event, data) {
    	console.log("listen relatorio:loadEntity", data);
    	vm.entity.relatorio = data;
	});
 
  }

})();