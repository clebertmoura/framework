(function() {
    'use strict';
    
	angular
		.module('app.${entityNameCamelCase}')
		.service('${entityName}Service', ${entityName}Service);
		
	${entityName}Service.$inject = ['${entityName}Factory'];
	
	function ${entityName}Service(${entityName}Factory) {
	
		var vm = this;
		
		vm.${entityNameCamelCase}List = vm.${entityNameCamelCase}List || [];
		vm.listar${entityName} = listar${entityName};
		
		function listar${entityName}(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
	    		<#if entityMetamodel.propertyLabel.propertyName == "Id">
				vRestrictions.push({field: 'id', operator: 'EQ', value: valorCampo});
				<#else>
				vRestrictions.push({field: '${entityMetamodel.propertyLabel.propertyNameCamelCase}', operator: 'LI', value: valorCampo});
    			</#if>
	    	}
			<#if entityMetamodel.propertyLabel.propertyName == "Id">
			vOrderings.push({field: 'id', order: 'ASC'});
			<#else>
			vOrderings.push({field: '${entityMetamodel.propertyLabel.propertyNameCamelCase}', order: 'ASC'});
			</#if>
	    	return ${entityName}Factory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.${entityNameCamelCase}List);
	        	}
	        );
		}
		
	};

})();