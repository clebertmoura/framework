(function () {
  'use strict';
    var inheritance = angular.module('app.endereco');

    inheritance.controller('EnderecoModalController', EnderecoModalController);

    EnderecoModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance',  'EnderecoFactory', 'EnderecoService', 'CidadeFactory', 'BairroFactory', 'UfFactory', 'PaisFactory', 'CidadeService', 'BairroService', 'UfService', 'PaisService'];


  /**
   * Controlador da entidade Endereco que estende as funcionalidades do BaseModalController. 
   */
  function EnderecoModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	 EnderecoFactory, EnderecoService, CidadeFactory, BairroFactory, UfFactory, PaisFactory, CidadeService, BairroService, UfService, PaisService) {

    var vm = this;

    vm.endereco = vm.endereco || new EnderecoFactory();

    $controller('BaseModalController', { vm: vm, factory: EnderecoFactory, entityName: 'Endereco', entity: vm.endereco, $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
	// Lista de Cidade
    vm.cidadeList = CidadeService.cidadeList;
    vm.listarCidade = CidadeService.listarCidade;
    vm.listarCidade();
	// Lista de Bairro
    vm.bairroList = BairroService.bairroList;
    vm.listarBairro = BairroService.listarBairro;
    vm.listarBairro();
	// Lista de Uf
    vm.ufList = UfService.ufList;
    vm.listarUf = UfService.listarUf;
    vm.listarUf();
	// Lista de Pais
    vm.paisList = PaisService.paisList;
    vm.listarPais = PaisService.listarPais;
    vm.listarPais();
	
	// Carregamento dos campos Enums
	
  }

})();