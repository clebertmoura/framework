(function () {
  'use strict';
    var inheritance = angular.module('app.pessoaContato');

    inheritance.controller('PessoaContatoController', PessoaContatoController);

    PessoaContatoController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', 'DialogUtil', 'ValidacaoUtil', 'FlashFactory',  'PessoaContatoFactory', 'PessoaContatoService'];


  /**
   * Controlador do Processo que estende as funcionalidades do BaseController. 
   */
  function PessoaContatoController($controller, $routeParams, $rootScope, $scope, $filter, $location, DialogUtil, ValidacaoUtil, FlashFactory, 
  	 PessoaContatoFactory, PessoaContatoService) {

    var vm = this;
    vm.scope = $scope;

    vm.pessoaContato = vm.pessoaContato || {};
    vm.contatos = [];

    $controller('BaseController', { vm: vm, factory: PessoaContatoFactory, restrictions: getRestrictions(), entityName: 'PessoaContato', entity: vm.pessoaContato });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    vm.newItem = newItem;
    vm.addItem = addItem;
    vm.removeItem = removeItem;
    vm.isItemValid = isItemValid;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoa && vm.searchData.pessoa != null) {
    		restrictions.push({field: 'pessoa.id', operator: 'EQ', value: vm.searchData.pessoa.id});
    	}
		if (vm.searchData.email && vm.searchData.email != "") {
    		restrictions.push({field: 'email', operator: 'LI', value: vm.searchData.email});
    	}
		if (vm.searchData.ddd && vm.searchData.ddd != "") {
    		restrictions.push({field: 'ddd', operator: 'LI', value: vm.searchData.ddd});
    	}
		if (vm.searchData.numero && vm.searchData.numero != "") {
    		restrictions.push({field: 'numero', operator: 'LI', value: vm.searchData.numero});
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
	 * Monitora o evento de carregamento dos itens
	 */
	$scope.$on('pessoaContato:loadItens', function (event, data) {
    	console.log("listen pessoaContato:loadItens", data);
    	for (var i = 0; i < data.length; i++) {
    		vm.contatos[i] = data[i];
		}
    	vm.contatos.length = data.length;
	});
	
	/**
	 * Cria o novo item.
	 */
	function newItem() {
		vm.entity = {
			email: "",
			ddd: "",
			numero: ""
		};
	}
    
	/**
	 * Adiciona um item e lança o evento: pessoaContato:addItem
	 */
    function addItem() {
    	// validações 
    	if(vm.entity.email || (vm.entity.ddd && vm.entity.numero)){
    		if (vm.entity.email) {
    			if(!ValidacaoUtil.isEmail(vm.entity.email)){
    	    		DialogUtil.show({message:'O e-mail informado é inválido.'}); 
    	    		return;
    	    	}
    		}
    		if (vm.entity.ddd && vm.entity.numero) {
    			if(vm.entity.ddd == "" || vm.entity.numero == ""){
    	    		DialogUtil.show({message:'É necessário informar o DDD + Número do telefone.'}); 
    	    		return;
    	    	}
    		} else if (vm.entity.ddd || vm.entity.numero) {
    			DialogUtil.show({message:'É necessário informar o DDD + Número do telefone.'}); 
	    		return;
    		}
    		vm.contatos.push(vm.entity);
    		$scope.$emit('pessoaContato:addItem', vm.entity);
    		vm.newItem();
    	}
	};
	
	/**
	 * Remove um item e lança o evento: pessoaContato:removeItem
	 */
	function removeItem(index) {
    	console.log("removeItem: ", index);
    	vm.contatos.splice(index, 1);
    	$scope.$emit('pessoaContato:removeItem', index);
	};
	
	/**
	 * Valida se o item está válido
	 */
	function isItemValid() {
    	return (vm.entity.email && vm.entity.email != "") || (vm.entity.ddd && vm.entity.ddd != "" && vm.entity.numero && vm.entity.numero != "");
	};
	
  }

})();