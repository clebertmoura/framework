<#list entitiesMetamodel as entity>
	<!-- Import dos JS da entidade: ${entity.simpleName}. -->
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.module.js"></script>
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.controller.js"></script>
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}-modal.controller.js"></script>
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.factory.js"></script>
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.service.js"></script>
	<script src="app/${entity.simpleNameCamelCase}/${entity.simpleNameCamelCase}.routes.js"></script>
	
</#list>