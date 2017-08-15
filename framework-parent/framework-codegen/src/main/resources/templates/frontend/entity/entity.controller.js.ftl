(function () {
  'use strict';
    var inheritance = angular.module('app.${entityNameCamelCase}');

    inheritance.controller('${entityName}Controller', ${entityName}Controller);

    ${entityName}Controller.$inject = ['$controller', '$routeParams', '$rootScope', '$scope', '$filter', '$location', '$uibModal', 'FlashFactory', <#if entityMetamodel.enumModules?size gt 0><#list entityMetamodel.enumModules as enumModuleName>'${enumModuleName}EnumsService', </#list></#if> '${entityName}Factory', '${entityName}Service'<#if entityMetamodel.propertiesDaos?size gt 0>, </#if><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>'${entityProperty.typeArgumentSimpleClassName}Factory', <#else>'${entityProperty.typeClassSimpleName}Factory', </#if></#if></#list><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>'${entityProperty.typeArgumentSimpleClassName}Service'<#if !entityProperty?is_last>, </#if><#else>'${entityProperty.typeClassSimpleName}Service'<#if !entityProperty?is_last>, </#if></#if></#if></#list>];


  /**
   * Controlador da entidade ${entityName} que estende as funcionalidades do BaseController. 
   */
  function ${entityName}Controller($controller, $routeParams, $rootScope, $scope, $filter, $location, $uibModal, FlashFactory, 
  	<#if entityMetamodel.enumModules?size gt 0><#list entityMetamodel.enumModules as enumModuleName>${enumModuleName}EnumsService, </#list></#if> ${entityName}Factory, ${entityName}Service<#if entityMetamodel.propertiesDaos?size gt 0>, </#if><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>${entityProperty.typeArgumentSimpleClassName}Factory, <#else>${entityProperty.typeClassSimpleName}Factory, </#if></#if></#list><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>${entityProperty.typeArgumentSimpleClassName}Service<#if !entityProperty?is_last>, </#if><#else>${entityProperty.typeClassSimpleName}Service<#if !entityProperty?is_last>, </#if></#if></#if></#list>) {

    var vm = this;

    $controller('BaseController', { vm: vm, factory: ${entityName}Factory, entityName: '${entityName}', $scope: $scope });
    
    vm.getRestrictions = getRestrictions;
    vm.getOrderings = getOrderings;
    
    vm.activate();

    function getRestrictions() {
      var restrictions = [];
      if(typeof(vm.searchData) !== "undefined") {
      <#list entityProperties as entityProperty>
		<#if !entityProperty.transient && !entityProperty.imageAttribute>
			<#if entityProperty.simpleAttribute>
				<#if entityProperty.stringAttribute>
		if (vm.searchData.${entityProperty.propertyNameCamelCase} && vm.searchData.${entityProperty.propertyNameCamelCase} != "") {
    		restrictions.push({field: '${entityProperty.propertyNameCamelCase}', operator: 'LI', value: vm.searchData.${entityProperty.propertyNameCamelCase}});
    	}
				<#else>
		if (vm.searchData.${entityProperty.propertyNameCamelCase} && vm.searchData.${entityProperty.propertyNameCamelCase} != null) {
    		restrictions.push({field: '${entityProperty.propertyNameCamelCase}', operator: 'EQ', value: vm.searchData.${entityProperty.propertyNameCamelCase}});
    	}
				</#if>
			<#else>
				<#if !entityProperty.listAttribute>
		if (vm.searchData.${entityProperty.propertyNameCamelCase} && vm.searchData.${entityProperty.propertyNameCamelCase} != null) {
    		restrictions.push({field: '${entityProperty.propertyNameCamelCase}.id', operator: 'EQ', value: vm.searchData.${entityProperty.propertyNameCamelCase}.id});
    	}
				</#if>
			</#if>	
		</#if>
	</#list>
      }
      return restrictions;
	};
	
	function getOrderings() {
		var orderings = [];
		orderings.push({field: '${entityMetamodel.propertyLabel.propertyNameCamelCase}', order: 'ASC'});
		return orderings;
	};
	
	// Carregamento de relacionamentos
    <#list entityMetamodel.propertiesDaos as entityProperty>
		<#if !entityProperty.transient && !entityProperty.simpleAttribute && !entityProperty.imageAttribute>
			<#if entityProperty.listAttribute>
				<#if entityProperty.oneToMany && entityProperty.oneToManyWithMappedBy>
	vm.entity.${entityProperty.propertyNameCamelCase} = vm.entity.${entityProperty.propertyNameCamelCase} || [];
	vm.openModal${entityProperty.typeArgumentSimpleClassName} = openModal${entityProperty.typeArgumentSimpleClassName};
    vm.add${entityProperty.typeArgumentSimpleClassName} = add${entityProperty.typeArgumentSimpleClassName};
    vm.remove${entityProperty.typeArgumentSimpleClassName} = remove${entityProperty.typeArgumentSimpleClassName};
    
    function openModal${entityProperty.typeArgumentSimpleClassName}() {
    	vm.modal${entityProperty.typeArgumentSimpleClassName} = $uibModal.open({
			templateUrl: 'app/${entityProperty.typeArgumentSimpleClassNameCamelCase}/modal.tpl.html',
			controller: '${entityProperty.typeArgumentSimpleClassName}ModalController',
			controllerAs: 'vm${entityProperty.typeArgumentSimpleClassName}',
			backdrop: false,
			scope: $scope
	    }).result.then(function(result) {
	    	vm.add${entityProperty.typeArgumentSimpleClassName}(result);
		});    	
    };	
    
    function add${entityProperty.typeArgumentSimpleClassName}(${entityProperty.typeArgumentSimpleClassNameCamelCase}) {
    	if (${entityProperty.typeArgumentSimpleClassNameCamelCase} && ${entityProperty.typeArgumentSimpleClassNameCamelCase} != null) {
    		vm.entity.${entityProperty.propertyNameCamelCase}.push(${entityProperty.typeArgumentSimpleClassNameCamelCase});
    	}
    };
    
    function remove${entityProperty.typeArgumentSimpleClassName}(index) {
    	vm.entity.${entityProperty.propertyNameCamelCase}.splice(index, 1);
    };
				<#else>
	// Lista de ${entityProperty.typeArgumentSimpleClassName}
    vm.${entityProperty.typeArgumentSimpleClassNameCamelCase}List = ${entityProperty.typeArgumentSimpleClassName}Service.${entityProperty.typeArgumentSimpleClassNameCamelCase}List;
    vm.listar${entityProperty.typeArgumentSimpleClassName} = ${entityProperty.typeArgumentSimpleClassName}Service.listar${entityProperty.typeArgumentSimpleClassName};
    vm.listar${entityProperty.typeArgumentSimpleClassName}();
				</#if>	
			<#else>
	// Lista de ${entityProperty.typeClassSimpleName}
    vm.${entityProperty.typeClassSimpleNameCamelCase}List = ${entityProperty.typeClassSimpleName}Service.${entityProperty.typeClassSimpleNameCamelCase}List;
    vm.listar${entityProperty.typeClassSimpleName} = ${entityProperty.typeClassSimpleName}Service.listar${entityProperty.typeClassSimpleName};
    vm.listar${entityProperty.typeClassSimpleName}();
			</#if>
		</#if>
	</#list>
	
	// Carregamento dos campos Enums
	<#list entityMetamodel.propertiesEnums as entityProperty>
		<#if !entityProperty.baseEntityAuditedStatusAttribute>
    vm.${entityProperty.propertyNameCamelCase}List = ${entityProperty.typeClassModuleNameCapitalized}EnumsService.${entityProperty.propertyNameCamelCase}List;
    	</#if>
	</#list>
	
	
	// Modal para upload de imagens
	<#list entityMetamodel.propertiesImages as entityProperty>
	/**
     * Modal ${entityProperty.propertyName}
     */
    vm.modal${entityProperty.propertyName} = {};
    vm.modal${entityProperty.propertyName}.item = {};
    vm.modal${entityProperty.propertyName}.modal = null;
    vm.modal${entityProperty.propertyName}.erros = [];
    
    vm.${entityProperty.propertyNameCamelCase}Src = "";
    
    vm.modal${entityProperty.propertyName}.open = function() {
    	vm.modal${entityProperty.propertyName}.modal = $uibModal.open({
    		templateUrl: 'modal${entityProperty.propertyName}.tpl',
    		scope: $scope
	    });
    };
    vm.modal${entityProperty.propertyName}.close = function() {
    	vm.modal${entityProperty.propertyName}.modal.dismiss('cancel');
    };
    vm.modal${entityProperty.propertyName}.upload = function(dataUrl, name) {
    	console.log("dataUrl: ", dataUrl);
    	console.log("name: ", name);
    	vm.modal${entityProperty.propertyName}.erros = [];
    	if (!name) {
    		vm.modal${entityProperty.propertyName}.erros.push("${entityProperty.propertyNameCamelCase} não foi selecionado(a)!");
    	}
    	if (vm.modal${entityProperty.propertyName}.erros.length == 0) {
    		vm.${entityProperty.propertyNameCamelCase}Src = dataUrl;
    		var dataUrlMetadata = dataUrl.substring(0, dataUrl.indexOf(","));
    		var data = dataUrl.slice(dataUrl.indexOf(",") + 1);
			console.log("dataUrlMetadata: ", dataUrlMetadata);
			var contentType = dataUrlMetadata.substring(dataUrlMetadata.indexOf(":") + 1, dataUrl.indexOf(";"));
			var fileExtension = name.slice(name.lastIndexOf(".") + 1);
			console.log("contentType: ", contentType);
			if (!vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}) {
				vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase} = {};
    		}
			vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}.nome = name;
			vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}.contentType = contentType;
			vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}.fileExtension = fileExtension.toLowerCase();
			//vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}.length = name;
			vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase}.dataBase64 = data;
			console.log("${entityProperty.propertyName}: ", vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase});
    		vm.modal${entityProperty.propertyName}.modal.close(vm.${entityNameCamelCase}.${entityProperty.propertyNameCamelCase});
    		vm.modal${entityProperty.propertyName}.item = {};
    	}
    };
	</#list>
 
  }

})();