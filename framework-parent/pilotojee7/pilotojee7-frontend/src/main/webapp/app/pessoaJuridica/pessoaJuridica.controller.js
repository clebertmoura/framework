(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaJuridica');

    inheritance.controller('PessoaJuridicaController', PessoaJuridicaController);

    PessoaJuridicaController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'PessoaJuridicaFactory', 'PessoaJuridicaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador da entidade PessoaJuridica que estende as funcionalidades do BaseController. 
   */
  function PessoaJuridicaController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 PessoaJuridicaFactory, PessoaJuridicaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;
    vm.instance = vm;
    
    $controller('BaseController', { vm: vm, factory: PessoaJuridicaFactory, entityName: 'PessoaJuridica', $scope: $scope });
    
    vm.entity.endereco = vm.entity.endereco || new EnderecoFactory();
    
    $scope.$watch('vmPessoaJuridica.entity', function(){
    	if ($scope.$parent && $scope.$parent.ClienteForm) {
    		$scope.$parent.vmCliente.entity.pessoaJuridica = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.EmpresaForm) {
    		$scope.$parent.vmEmpresa.entity.pessoaJuridica = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.FilialForm) {
    		$scope.$parent.vmFilial.entity.pessoaJuridica = vm.entity;
    	}
    	if ($scope.$parent && $scope.$parent.FabricanteForm) {
    		$scope.$parent.vmFabricante.entity.pessoaJuridica = vm.entity;
    	}
    });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    vm.loadEntityRelations = loadEntityRelations;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.endereco && vm.searchData.endereco != null) {
    		restrictions.push({field: 'endereco.id', operator: 'EQ', value: vm.searchData.endereco.id});
    	}
		if (vm.searchData.nomePessoa && vm.searchData.nomePessoa != "") {
    		restrictions.push({field: 'nomePessoa', operator: 'LI', value: vm.searchData.nomePessoa});
    	}
		if (vm.searchData.cnpj && vm.searchData.cnpj != "") {
    		restrictions.push({field: 'cnpj', operator: 'LI', value: vm.searchData.cnpj});
    	}
		if (vm.searchData.nomeFantasia && vm.searchData.nomeFantasia != "") {
    		restrictions.push({field: 'nomeFantasia', operator: 'LI', value: vm.searchData.nomeFantasia});
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
	// Lista de Endereco
    vm.enderecoList = EnderecoService.enderecoList;
    vm.listarEndereco = EnderecoService.listarEndereco;
    vm.listarEndereco();
    
	vm.entity.contatos = vm.entity.contatos || [];
	vm.openModalPessoaContato = openModalPessoaContato;
    vm.addPessoaContato = addPessoaContato;
    vm.removePessoaContato = removePessoaContato;
    
    function openModalPessoaContato() {
    	vm.modalPessoaContato = $uibModal.open({
			templateUrl: 'app/pessoaContato/modal.tpl.html',
			controller: 'PessoaContatoModalController',
			controllerAs: 'vmPessoaContato',
			backdrop: false,
			scope: $scope
	    }).result.then(function(result) {
	    	vm.addPessoaContato(result);
		});    	
    };	
    
    function addPessoaContato(pessoaContato) {
    	if (pessoaContato && pessoaContato != null) {
    		vm.entity.contatos.push(pessoaContato);
    	}
    };
    
    function removePessoaContato(index) {
    	vm.entity.contatos.splice(index, 1);
    };
	
    /**
     * @override
     * Carrega os atributos de relacionamento e lança o evento: pessoaContato:loadItens
     */
    function loadEntityRelations(){
    	$scope.$broadcast('endereco:loadEntity', vm.entity.endereco);
    };
    
    /**
	 * Monitora o evento para carregamento da entidade, quando o controller de 
	 * pessoaJuridica é embarcado em outro controller.
	 */
    $scope.$on('pessoaJuridica:loadEntity', function (event, data) {
    	console.log("listen pessoaJuridica:loadEntity", data);
    	vm.entity = data;
    	vm.getById(data.id).$promise.then(function(value){
    		console.log(value);
    		vm.original = value;
    		vm.loadEntityRelations();
    	});
	});
 
  }

})();