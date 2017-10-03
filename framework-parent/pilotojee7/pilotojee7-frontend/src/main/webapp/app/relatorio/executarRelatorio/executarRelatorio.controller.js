(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.executarRelatorio');

    inheritance.controller('ExecutarRelatorioController', ExecutarRelatorioController);

    ExecutarRelatorioController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CadastroEnumsResource', 'RelatorioEnumsResource', 'RelatorioFactory', 'RelatorioService', 'RelatorioFiltroFactory', 'CategoriaFactory', 'RelatorioFiltroService', 'CategoriaService', 'toastr'];


  /**
   * Controlador da entidade Relatorio que estende as funcionalidades do BaseController. 
   */
  function ExecutarRelatorioController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	CadastroEnumsResource, RelatorioEnumsResource, RelatorioFactory, RelatorioService, RelatorioFiltroFactory, CategoriaFactory, RelatorioFiltroService, CategoriaService, toastr) {

    var vm = this;
    
    vm.categoriaSelecionada = null;
    vm.relatorios = [];
    vm.relatorioSelecionado = null;
    
    vm.carregarRelatorios = carregarRelatorios;

    function carregarRelatorios(categoria) {
    	vm.categoriaSelecionada = categoria;
    	vm.relatorios = [];
    	RelatorioService.listarRelatorioPorCategoria(categoria).$promise.then(function(result) {
    		angular.copy(result.results, vm.relatorios);
    	});
    };
    
    vm.selecionarRelatorio = selecionarRelatorio;
    
    function selecionarRelatorio(relatorio) {
    	vm.relatorioSelecionado = relatorio;
    	console.log("Relatorio selecionado: ", relatorio);
    };

	// Lista de Categoria
    vm.categoriaList = CategoriaService.categoriaList;
    vm.listarCategoria = CategoriaService.listarCategoria;
    vm.listarCategoria();
	
	// Carregamento dos campos Enums
    vm.habilitadoList = vm.habilitadoList || [];
    CadastroEnumsResource.listSimNao(function (items) {
        vm.habilitadoList = items;
    });
	
	
	// Modal para upload de imagens
 
  }

})();