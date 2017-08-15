(function () {
  'use strict';
    var inheritance = angular.module('app.empresa');

    inheritance.controller('EmpresaController', EmpresaController);

    EmpresaController.$inject = ['$controller', '$scope', '$rootScope', '$routeParams', '$location', '$filter', 'Constants', 'FlashFactory', 'EmpresaFactory', 'EmpresaService', 'PessoaJuridicaFactory', 'PessoaJuridicaService'];


  /**
   * Controlador do Processo que estende as funcionalidades do BaseController. 
   */
  function EmpresaController($controller, $scope, $rootScope, $routeParams, $location, $filter, Constants, FlashFactory, 
  	 EmpresaFactory, EmpresaService, PessoaJuridicaFactory, PessoaJuridicaService) {

    var vm = this;
    vm.scope = $scope;

    vm.empresa = vm.empresa || new EmpresaFactory();

    $controller('BaseController', { vm: vm, factory: EmpresaFactory, restrictions: getRestrictions(), entityName: 'Empresa', entity: vm.empresa });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    vm.loadEntityRelations = loadEntityRelations;
    
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
	
	/**
     * @override
     * Carrega os atributos de relacionamento e lança o evento: pessoaJuridica:loadEntity
     */
    function loadEntityRelations(){
    	$scope.$broadcast('pessoaJuridica:loadEntity', vm.entity.pessoaJuridica);
    };
 
  }

})();