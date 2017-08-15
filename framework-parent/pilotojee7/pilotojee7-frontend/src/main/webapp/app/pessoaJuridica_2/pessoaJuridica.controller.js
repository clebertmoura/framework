(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaJuridica');

    inheritance.controller('PessoaJuridicaController', PessoaJuridicaController);

    PessoaJuridicaController.$inject = ['$controller', '$scope', '$rootScope', '$routeParams', '$location', '$filter', 'Constants', 'FlashFactory', 'DialogUtil', 'PessoaJuridicaFactory', 'PessoaJuridicaService', 'EnderecoFactory', 'PessoaContatoFactory', 'EnderecoService', 'PessoaContatoService'];


  /**
   * Controlador do Processo que estende as funcionalidades do BaseController. 
   */
  function PessoaJuridicaController($controller, $scope, $rootScope, $routeParams, $location, $filter, Constants, FlashFactory, DialogUtil, 
  	 PessoaJuridicaFactory, PessoaJuridicaService, EnderecoFactory, PessoaContatoFactory, EnderecoService, PessoaContatoService) {

    var vm = this;
    vm.scope = $scope;

    vm.pessoaJuridica = vm.pessoaJuridica || new PessoaJuridicaFactory();
    vm.pessoaJuridica.contatos = vm.pessoaJuridica.contatos || [];
    vm.pessoaJuridica.endereco = vm.pessoaJuridica.endereco || new EnderecoFactory();
    
    $scope.$watch('vm.pessoaJuridica', function(){
    	if ($scope.$parent && $scope.$parent.ClienteForm) {
    		$scope.$parent.vm.cliente.pessoaJuridica = vm.pessoaJuridica;
    	}
    	if ($scope.$parent && $scope.$parent.EmpresaForm) {
    		$scope.$parent.vm.empresa.pessoaJuridica = vm.pessoaJuridica;
    	}
    	if ($scope.$parent && $scope.$parent.FilialForm) {
    		$scope.$parent.vm.filial.pessoaJuridica = vm.pessoaJuridica;
    	}
    	if ($scope.$parent && $scope.$parent.FabricanteForm) {
    		$scope.$parent.vm.fabricante.pessoaJuridica = vm.pessoaJuridica;
    	}
    });

    $controller('BaseController', { vm: vm, factory: PessoaJuridicaFactory, restrictions: getRestrictions(), entityName: 'PessoaJuridica', entity: vm.pessoaJuridica });
    
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
	
    /**
     * @override
     * Carrega os atributos de relacionamento e lança o evento: pessoaContato:loadItens
     */
    function loadEntityRelations(){
    	$scope.$broadcast('pessoaContato:loadItens', vm.entity.contatos);
    	$scope.$broadcast('endereco:loadEntity', vm.entity.endereco);
    };
    
    /**
	 * Monitora o evento para carregamento da entidade, quando o controller de 
	 * pessoaJuridica é embarcado em outro controller.
	 */
    $scope.$on('pessoaJuridica:loadEntity', function (event, data) {
    	console.log("listen pessoaJuridica:loadEntity", data);
    	vm.getById(data.id).$promise.then(function(){
    		$scope.$parent.vm.pessoaJuridica = vm.entity;
    		vm.loadEntityRelations();
    	});
	});
    /**
	 * Monitora os eventos adição e remoção de itens.
	 */
    $scope.$on('pessoaContato:addItem', function (event, data) {
    	console.log("listen pessoaContato:addItem", data);
    	vm.entity.contatos.push(data);
	});
    $scope.$on('pessoaContato:removeItem', function (event, data) {
    	console.log("listen pessoaContato:removeItem", data);
    	vm.entity.contatos.splice(data, 1);
	});
 
  }

})();