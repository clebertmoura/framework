<#list entitiesMetamodel as entity>
	// Import dos JS da entidade: ${entity.simpleName}.
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.module.js"><\/script>');
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.controller.js"><\/script>');
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}-modal.controller.js"><\/script>');
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.factory.js"><\/script>');
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.service.js"><\/script>');
	document.write('<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.routes.js"><\/script>');
	
</#list>