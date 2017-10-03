(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.relatorioFiltro');

    inheritance.controller('RelatorioFiltroModalController', RelatorioFiltroModalController);

    RelatorioFiltroModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CadastroEnumsResource',  'RelatorioFiltroFactory', 'RelatorioFiltroService', 'RelatorioFactory', 'FiltroFactory', 'RelatorioService', 'FiltroService'];


  /**
   * Controlador da entidade RelatorioFiltro que estende as funcionalidades do BaseModalController. 
   */
  function RelatorioFiltroModalController($controller, $rootScope, $scope, $uibModalInstance, 
		CadastroEnumsResource,  RelatorioFiltroFactory, RelatorioFiltroService, RelatorioFactory, FiltroFactory, RelatorioService, FiltroService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: RelatorioFiltroFactory, entityName: 'RelatorioFiltro', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Relatorio
    vm.relatorioList = RelatorioService.relatorioList;
    vm.listarRelatorio = RelatorioService.listarRelatorio;
    vm.listarRelatorio();
	// Lista de Filtro
    vm.filtroList = FiltroService.filtroList;
    vm.listarFiltro = FiltroService.listarFiltro;
    vm.listarFiltro();
	
	// Carregamento dos campos Enums
    vm.habilitadoList = vm.habilitadoList || [];
    CadastroEnumsResource.listSimNao(function (items) {
        vm.habilitadoList = items;
    });
	
  }

})();