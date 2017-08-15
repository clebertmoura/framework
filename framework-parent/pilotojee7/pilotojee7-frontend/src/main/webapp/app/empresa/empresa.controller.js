(function () {
  'use strict';
    var inheritance = angular.module('app.empresa');

    inheritance.controller('EmpresaController', EmpresaController);

    EmpresaController.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory',  'EmpresaFactory', 'EmpresaService', 'PessoaJuridicaFactory', 'PessoaJuridicaService'];


  /**
   * Controlador da entidade Empresa que estende as funcionalidades do BaseController. 
   */
  function EmpresaController($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	 EmpresaFactory, EmpresaService, PessoaJuridicaFactory, PessoaJuridicaService) {

    var vm = this;

    //vm.empresa = vm.empresa || new EmpresaFactory();

    $controller('BaseController', { vm: vm, factory: EmpresaFactory, entityName: 'Empresa', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    vm.loadEntityRelations = loadEntityRelations;
    //vm.isClean = isClean;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
		if (vm.searchData.pessoaJuridica && vm.searchData.pessoaJuridica != null) {
    		restrictions.push({field: 'pessoaJuridica.id', operator: 'EQ', value: vm.searchData.pessoaJuridica.id});
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
	// Lista de PessoaJuridica
    vm.pessoaJuridicaList = PessoaJuridicaService.pessoaJuridicaList;
    vm.listarPessoaJuridica = PessoaJuridicaService.listarPessoaJuridica;
    vm.listarPessoaJuridica();
	
    /**
     * @override
     * Carrega os atributos de relacionamento e lança o evento: pessoaJuridica:loadEntity
     */
    function loadEntityRelations(){
    	$scope.$broadcast('pessoaJuridica:loadEntity', vm.entity.pessoaJuridica);
    };
    
    /*function isClean() {
        return angular.equals(vm.original, vm.entity) && 
        	angular.equals(vm.original.pessoaJuridica, vm.entity.pessoaJuridica) &&
        		angular.equals(vm.original.pessoaJuridica.endereco, vm.entity.pessoaJuridica.endereco);
    };*/
 
  }

})();