(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.filtro');

    inheritance.controller('FiltroModalController', FiltroModalController);

    FiltroModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CadastroEnumsResource', 'RelatorioEnumsResource', 'FiltroFactory', 'FiltroService'];


  /**
   * Controlador da entidade Filtro que estende as funcionalidades do BaseModalController. 
   */
  function FiltroModalController($controller, $rootScope, $scope, $uibModalInstance, 
		  CadastroEnumsResource, RelatorioEnumsResource, FiltroFactory, FiltroService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: FiltroFactory, entityName: 'Filtro', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Filtro
    vm.filtroList = FiltroService.filtroList;
    vm.listarFiltro = FiltroService.listarFiltro;
    vm.listarFiltro();
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
	
  }

})();