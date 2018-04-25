(function () {
  'use strict';
    var inheritance = angular.module('app.relatorio.relatorio');

    inheritance.controller('RelatorioController', RelatorioController);

    RelatorioController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', 'CadastroEnumsResource', 'RelatorioEnumsResource', 'RelatorioFactory', 'RelatorioService', 'RelatorioFiltroFactory', 'CategoriaFactory', 'RelatorioFiltroService', 'CategoriaService', 'toastr'];


  /**
   * Controlador da entidade Relatorio que estende as funcionalidades do BaseController. 
   */
  function RelatorioController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	CadastroEnumsResource, RelatorioEnumsResource, RelatorioFactory, RelatorioService, RelatorioFiltroFactory, CategoriaFactory, RelatorioFiltroService, CategoriaService, toastr) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: RelatorioFactory, entityName: 'Relatorio', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();
    
    vm.maxFileSize = 2 * 1024 * 1024; 
    vm.jrxmlDataFlow = null;
    
    vm.validateFile = validateFile;
    
    function validateFile(file) {
        if (file.size > vm.maxFileSize) {
            toastr.error("O tamanho máximo do arquivo não pode ultrapassar 2 Mb.");
            return false;
        }
    };
    
    vm.processFiles = processFiles;

    function processFiles(files) {
    	
        vm.entity.jrxmlData = null;
        
        angular.forEach(files, function (flowFile, i) {
            var fileReader = new FileReader();
            fileReader.onload = function (event) {
                var uri = event.target.result;
                console.log(uri);
                vm.entity.jrxmlData = uri;
            };
            fileReader.readAsDataURL(flowFile.file);
        });
    };
    
    vm.addImageFlow = addImageFlow;

    function addImageFlow(image) {
        $timeout(function () {
            var blob = blobUtil.base64StringToBlob(image.data, image.contentType);
            blob.name = image.nome;
            vm.jrxmlDataFlow.addFile(blob);
        });
    };
    
    vm.removeFile = removeFile;
    
    function removeFile() {
        vm.jrxmlDataFlow.files = [];
        vm.entity.jrxmlData = null;
    };

    vm.options = {
        language: 'pt',
        allowedContent: true,
        entities: false
    };
    
    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.nome && vm.searchData.nome != "") {
    		restrictions.push({field: 'nome', operator: 'LI', value: vm.searchData.nome});
    	}
		if (vm.searchData.descricao && vm.searchData.descricao != "") {
    		restrictions.push({field: 'descricao', operator: 'LI', value: vm.searchData.descricao});
    	}
		if (vm.searchData.permissao && vm.searchData.permissao != "") {
    		restrictions.push({field: 'permissao', operator: 'LI', value: vm.searchData.permissao});
    	}
		if (vm.searchData.habilitado && vm.searchData.habilitado != null) {
    		restrictions.push({field: 'habilitado', operator: 'EQ', value: vm.searchData.habilitado});
    	}
		if (vm.searchData.jrxmlData && vm.searchData.jrxmlData != null) {
    		restrictions.push({field: 'jrxmlData', operator: 'EQ', value: vm.searchData.jrxmlData});
    	}
		if (vm.searchData.categoria && vm.searchData.categoria != null) {
    		restrictions.push({field: 'categoria.id', operator: 'EQ', value: vm.searchData.categoria.id});
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
	vm.entity.filtros = vm.entity.filtros || [];
	vm.openModalRelatorioFiltro = openModalRelatorioFiltro;
    vm.addRelatorioFiltro = addRelatorioFiltro;
    vm.removeRelatorioFiltro = removeRelatorioFiltro;
    
    function openModalRelatorioFiltro() {
    	vm.modalRelatorioFiltro = $uibModal.open({
			templateUrl: 'app/relatorio/relatorioFiltro/modal.tpl.html',
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
    		vm.entity.filtros.push(relatorioFiltro);
    	}
    };
    
    function removeRelatorioFiltro(index) {
    	vm.entity.filtros.splice(index, 1);
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