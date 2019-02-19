(function () {
  'use strict';
  var inheritance = angular.module('app.shared');

  inheritance.controller('BaseModalController', BaseModalController);

  BaseModalController.$inject = ['vm', 'factory', '$rootScope', '$scope', '$uibModalInstance', 'entityName'];


  /**
   * Controlador básico para tela Modal.
   */
  function BaseModalController(vm, factory, $rootScope, $scope, $uibModalInstance, entityName) {

	vm.factory = factory;
	vm.entity = vm.entity || new factory();
	
    vm.getEnumLabel = getEnumLabel;

    console.debug('chamou o BaseModalController');

    function getEnumLabel(key, enumList) {
    	for (var item in enumList) {
			if (item.key == key) {
				return item.label;
			}
		}
    	return key;
    };
    
    vm.onClickModalConfirmar = onClickModalConfirmar;
    vm.onClickModalCancelar = onClickModalCancelar;
    
    function onClickModalConfirmar() {
    	$uibModalInstance.close(vm.entity);
    }
    function onClickModalCancelar() {
    	$uibModalInstance.dismiss('cancel');
    }
    
  }

})();