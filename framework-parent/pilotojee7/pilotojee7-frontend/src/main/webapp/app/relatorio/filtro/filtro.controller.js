(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.filtro');

    inheritance.controller('FiltroController', FiltroController);

    FiltroController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CadastroEnumsResource', 'RelatorioEnumsResource', 'FiltroFactory', 'FiltroService', 'toastr'];


  /**
   * Controlador da entidade Filtro que estende as funcionalidades do BaseController. 
   */
  function FiltroController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
	CadastroEnumsResource, RelatorioEnumsResource, FiltroFactory, FiltroService, toastr) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: FiltroFactory, entityName: 'Filtro', $scope: $scope });
    
    vm.exemplos = ["select id,nome from tabela order by nome asc", "{\"1\": \"Item 1\",\"2\": \"Item 2\"}"];
    vm.dadosExemplo = vm.exemplos[0];
    vm.teste = {};
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    vm.selectFlagSql = selectFlagSql;
    vm.onSelectTipoFiltro = onSelectTipoFiltro;
    vm.loadEntityRelations = loadEntityRelations;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.descricao && vm.searchData.descricao != "") {
    		restrictions.push({field: 'descricao', operator: 'LI', value: vm.searchData.descricao});
    	}
		if (vm.searchData.path && vm.searchData.path != "") {
    		restrictions.push({field: 'path', operator: 'LI', value: vm.searchData.path});
    	}
		if (vm.searchData.permissao && vm.searchData.permissao != "") {
    		restrictions.push({field: 'permissao', operator: 'LI', value: vm.searchData.permissao});
    	}
		if (vm.searchData.habilitado && vm.searchData.habilitado != null) {
    		restrictions.push({field: 'habilitado', operator: 'EQ', value: vm.searchData.habilitado});
    	}
		if (vm.searchData.pai && vm.searchData.pai != null) {
    		restrictions.push({field: 'pai.id', operator: 'EQ', value: vm.searchData.pai.id});
    	}
		if (vm.searchData.tipoFiltro && vm.searchData.tipoFiltro != null) {
    		restrictions.push({field: 'tipoFiltro', operator: 'EQ', value: vm.searchData.tipoFiltro});
    	}
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: 'nome', order: 'ASC'});
		return orderings;
	};
	
	
	function loadEntityRelations() {
		vm.selectFlagSql(vm.entity.tipoFiltro);
	}
	
	// Carregamento de relacionamentos
	// Lista de Filtro
    vm.filtroList = FiltroService.filtroList;
    vm.listarFiltro = FiltroService.listarFiltro;
    vm.listarFiltro();
    
    // Carregamento dos campos Enums
    vm.habilitadoList = vm.habilitadoList || [];
    CadastroEnumsResource.listSimNao(function (items) {
        vm.habilitadoList = items;
    });
    
    vm.tipoFiltroList = vm.tipoFiltroList || [];
    RelatorioEnumsResource.listTipoFiltro(function (items) {
        vm.tipoFiltroList = items;
    });
    
	// Modal para upload de imagens
    
    function selectFlagSql(radioValue) {
    	if (radioValue == "S") {
    		vm.dadosExemplo = vm.exemplos[0];
    	} else {
    		vm.dadosExemplo = vm.exemplos[1];
    	}
    }
    
    function onSelectTipoFiltro(item, model) {
    	if (item != null) {
    		switch (item.key) {
		    	case 'SELECT_ONE':
					vm.teste = {};
					break;
		    	case 'SELECT_MANY':
		    		vm.teste = [];
		    		break;
		    	case 'CHECKBOX':
		    		vm.teste = [];
		    		break;
		    	case 'RADIO':
		    		vm.teste = null;
		    		break;
		    	case 'TEXT':
		    		vm.teste = "";
		    		break;
		    	case 'NUMBER':
		    	case 'FLOAT':
		    		vm.teste = 0;
		    		break;
		    	case 'DATE':
		    	case 'DATETIME':
				case 'TIME':
					vm.teste = new Date();
					break;
			}
    	}
    }
    
  }

})();