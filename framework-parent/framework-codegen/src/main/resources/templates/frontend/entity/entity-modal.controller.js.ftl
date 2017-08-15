(function () {
  'use strict';
    var inheritance = angular.module('app.${entityNameCamelCase}');

    inheritance.controller('${entityName}ModalController', ${entityName}ModalController);

    ${entityName}ModalController.$inject = ['$controller', '$rootScope', '$scope', '$uibModalInstance', <#if entityMetamodel.enumModules?size gt 0><#list entityMetamodel.enumModules as enumModuleName>'${enumModuleName}EnumsService', </#list></#if> '${entityName}Factory', '${entityName}Service'<#if entityMetamodel.propertiesDaos?size gt 0>, </#if><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>'${entityProperty.typeArgumentSimpleClassName}Factory', <#else>'${entityProperty.typeClassSimpleName}Factory', </#if></#if></#list><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>'${entityProperty.typeArgumentSimpleClassName}Service'<#if !entityProperty?is_last>, </#if><#else>'${entityProperty.typeClassSimpleName}Service'<#if !entityProperty?is_last>, </#if></#if></#if></#list>];


  /**
   * Controlador da entidade ${entityName} que estende as funcionalidades do BaseModalController. 
   */
  function ${entityName}ModalController($controller, $rootScope, $scope, $uibModalInstance, 
  	<#if entityMetamodel.enumModules?size gt 0><#list entityMetamodel.enumModules as enumModuleName>${enumModuleName}EnumsService, </#list></#if> ${entityName}Factory, ${entityName}Service<#if entityMetamodel.propertiesDaos?size gt 0>, </#if><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>${entityProperty.typeArgumentSimpleClassName}Factory, <#else>${entityProperty.typeClassSimpleName}Factory, </#if></#if></#list><#list entityMetamodel.propertiesDaos as entityProperty><#if !entityProperty.transient && !entityProperty.simpleAttribute><#if entityProperty.listAttribute>${entityProperty.typeArgumentSimpleClassName}Service<#if !entityProperty?is_last>, </#if><#else>${entityProperty.typeClassSimpleName}Service<#if !entityProperty?is_last>, </#if></#if></#if></#list>) {

    var vm = this;

    $controller('BaseModalController', { vm: vm, factory: ${entityName}Factory, entityName: '${entityName}', $scope: $scope, $uibModalInstance: $uibModalInstance });
    
	// Carregamento de relacionamentos
    <#list entityMetamodel.propertiesDaos as entityProperty>
		<#if !entityProperty.transient && !entityProperty.simpleAttribute && !entityProperty.imageAttribute>
			<#if entityProperty.listAttribute>
	// Lista de ${entityProperty.typeArgumentSimpleClassName}
    vm.${entityProperty.typeArgumentSimpleClassNameCamelCase}List = ${entityProperty.typeArgumentSimpleClassName}Service.${entityProperty.typeArgumentSimpleClassNameCamelCase}List;
    vm.listar${entityProperty.typeArgumentSimpleClassName} = ${entityProperty.typeArgumentSimpleClassName}Service.listar${entityProperty.typeArgumentSimpleClassName};
    vm.listar${entityProperty.typeArgumentSimpleClassName}();
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
	
  }

})();