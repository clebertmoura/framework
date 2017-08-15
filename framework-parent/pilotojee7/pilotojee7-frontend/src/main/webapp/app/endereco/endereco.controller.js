(function () {
  'use strict';
    var inheritance = angular.module('app.endereco');

    inheritance.controller('EnderecoController', EnderecoController);

    EnderecoController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'EnderecoFactory', 'EnderecoService', 'CidadeFactory', 'BairroFactory', 'UfFactory', 'PaisFactory', 'CidadeService', 'BairroService', 'UfService', 'PaisService'];


  /**
   * Controlador da entidade Endereco que estende as funcionalidades do BaseController. 
   */
  function EnderecoController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 EnderecoFactory, EnderecoService, CidadeFactory, BairroFactory, UfFactory, PaisFactory, CidadeService, BairroService, UfService, PaisService) {

    var vm = this;

    //vm.endereco = vm.endereco || new EnderecoFactory();
    
    $controller('BaseController', { vm: vm, factory: EnderecoFactory, entityName: 'Endereco', $scope: $scope });
    
    $scope.$watch('vmEndereco.entity', function(){
    	if ($scope.$parent && $scope.$parent.PessoaJuridicaForm) {
    		$scope.$parent.vmPessoaJuridica.entity.endereco = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.PessoaFisicaForm) {
    		$scope.$parent.vmPessoaFisica.entity.endereco = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.ClienteForm) {
    		if ($scope.$parent.vmCliente.entity.pessoaJuridica) {
    			$scope.$parent.vmCliente.entity.pessoaJuridica.endereco = vm.entity;
    		} else if ($scope.$parent.vmCliente.entity.pessoaFisica) {
    			$scope.$parent.vmCliente.entity.pessoaFisica.endereco = vm.entity;
    		}
    	}
    	if ($scope.$parent && $scope.$parent.EmpresaForm) {
    		$scope.$parent.vmEmpresa.entity.pessoaJuridica.endereco = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.FilialForm) {
    		$scope.$parent.vmEmpresa.entity.pessoaJuridica.endereco = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.FabricanteForm) {
    		$scope.$parent.vmEmpresa.entity.pessoaJuridica.endereco = vm.entity;
    	}
    });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.onChangePais = onChangePais;
    vm.onChangeUf = onChangeUf;
	vm.onChangeCidade = onChangeCidade;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.cidade && vm.searchData.cidade != null) {
    		restrictions.push({field: 'cidade.id', operator: 'EQ', value: vm.searchData.cidade.id});
    	}
		if (vm.searchData.dataHoraCadastro && vm.searchData.dataHoraCadastro != null) {
    		restrictions.push({field: 'dataHoraCadastro', operator: 'EQ', value: vm.searchData.dataHoraCadastro});
    	}
		if (vm.searchData.lat && vm.searchData.lat != null) {
    		restrictions.push({field: 'lat', operator: 'EQ', value: vm.searchData.lat});
    	}
		if (vm.searchData.lng && vm.searchData.lng != null) {
    		restrictions.push({field: 'lng', operator: 'EQ', value: vm.searchData.lng});
    	}
		if (vm.searchData.bairro && vm.searchData.bairro != null) {
    		restrictions.push({field: 'bairro.id', operator: 'EQ', value: vm.searchData.bairro.id});
    	}
		if (vm.searchData.uf && vm.searchData.uf != null) {
    		restrictions.push({field: 'uf.id', operator: 'EQ', value: vm.searchData.uf.id});
    	}
		if (vm.searchData.pais && vm.searchData.pais != null) {
    		restrictions.push({field: 'pais.id', operator: 'EQ', value: vm.searchData.pais.id});
    	}
		if (vm.searchData.logradouro && vm.searchData.logradouro != "") {
    		restrictions.push({field: 'logradouro', operator: 'LI', value: vm.searchData.logradouro});
    	}
		if (vm.searchData.numero && vm.searchData.numero != "") {
    		restrictions.push({field: 'numero', operator: 'LI', value: vm.searchData.numero});
    	}
		if (vm.searchData.complemento && vm.searchData.complemento != "") {
    		restrictions.push({field: 'complemento', operator: 'LI', value: vm.searchData.complemento});
    	}
		if (vm.searchData.cep && vm.searchData.cep != "") {
    		restrictions.push({field: 'cep', operator: 'LI', value: vm.searchData.cep});
    	}
		if (vm.searchData.pontoReferencia && vm.searchData.pontoReferencia != "") {
    		restrictions.push({field: 'pontoReferencia', operator: 'LI', value: vm.searchData.pontoReferencia});
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
	// Lista de Pais
    vm.paisList = PaisService.paisList;
    vm.listarPais = PaisService.listarPais;
    vm.listarPais();
    // Lista de Uf
    vm.ufList = UfService.ufList;
    vm.listarUf = listarUf;
    vm.listarUf();
	// Lista de Cidade
    vm.cidadeList = CidadeService.cidadeList;
    vm.listarCidade = listarCidade;
    vm.listarCidade();
	// Lista de Bairro
    vm.bairroList = BairroService.bairroList;
    vm.listarBairro = listarBairro;
    vm.listarBairro();
	
    
    /**
     * Listar as Ufs
     */
    function listarUf(valorCampo) {
    	return UfService.listarUf(valorCampo, vm.entity.pais);
	};
	/**
     * Listar as Cidades
     */
	function listarCidade(valorCampo) {
		return CidadeService.listarCidade(valorCampo, vm.entity.uf);
	};
	/**
     * Listar os Bairros
     */
	function listarBairro(valorCampo) {
		return BairroService.listarBairro(valorCampo, vm.entity.cidade);
	};
    
    /**
     * Executado ao alterar o Pais
     */
    function onChangePais(item, model) {
    	vm.entity.uf = null;
    	vm.entity.cidade = null;
    	vm.entity.bairro = null;
    	vm.ufList.length = 0;
    	vm.listarUf("", item).$promise.then(function() {
    		vm.listarCidade("").$promise.then(function() {
        		vm.listarBairro("");
        	});
    	});
    };
	
    /**
     * Executado ao alterar a UF
     */
    function onChangeUf(item, model) {
    	vm.entity.cidade = null;
    	vm.entity.bairro = null;
    	vm.bairroList.length = 0;
    	vm.listarCidade("", item).$promise.then(function() {
    		vm.listarBairro("");
    	});
    };
    
    /**
     * Executado ao alterar a Cidade
     */
    function onChangeCidade(item, model) {
    	vm.entity.bairro = null;
    	vm.listarBairro("", item);
    };
    
    /**
	 * Monitora o evento para carregamento da entidade, quando o controller de 
	 * endereco é embarcado em outro controller.
	 */
    $scope.$on('endereco:loadEntity', function (event, data) {
    	console.log("listen endereco:loadEntity", data);
    	vm.entity = data;
    	vm.getById(data.id).$promise.then(function(value){
    		vm.original = value;
    		vm.loadEntityRelations();
    	});
	});
 
  }

})();