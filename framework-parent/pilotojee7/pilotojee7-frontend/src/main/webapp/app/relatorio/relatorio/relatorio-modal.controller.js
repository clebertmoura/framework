(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.relatorio');

    inheritance.controller('RelatorioModalController', RelatorioModalController);

    RelatorioModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', 'CadastroEnumsResource',  'RelatorioFactory', 'RelatorioService', 'RelatorioFiltroFactory', 'CategoriaFactory', 'RelatorioFiltroService', 'CategoriaService'];


  /**
   * Controlador da entidade Relatorio que estende as funcionalidades do BaseModalController. 
   */
  function RelatorioModalController($controller, $rootScope, $scope, $uibModalInstance, 
		  CadastroEnumsResource,  RelatorioFactory, RelatorioService, RelatorioFiltroFactory, CategoriaFactory, RelatorioFiltroService, CategoriaService) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: RelatorioFactory, entityName: 'Relatorio', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de RelatorioFiltro
    vm.relatorioFiltroList = RelatorioFiltroService.relatorioFiltroList;
    vm.listarRelatorioFiltro = RelatorioFiltroService.listarRelatorioFiltro;
    vm.listarRelatorioFiltro();
	// Lista de Categoria
    vm.categoriaList = CategoriaService.categoriaList;
    vm.listarCategoria = CategoriaService.listarCategoria;
    vm.listarCategoria();
	
	// Carregamento dos campos Enums
    vm.habilitadoList = vm.habilitadoList || [];
    CadastroEnumsResource.listSimNao(function (items) {
        vm.habilitadoList = items;
    });
	
  }

})();