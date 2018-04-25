<#list entityProperties as entityProperty>
	<#if !entityProperty.transient>
		<#if entityProperty.simpleAttribute && !entityProperty.imageAttribute>
			<#if entityProperty.enumerated>
			<div class="row">
				<div class="col-md-12">
		    		<div class="form-group" ng-class="{'has-error': ${entityName}Form.${entityProperty.propertyNameCamelCase}.$invalid}">
			    		<div class="col-sm-12">
				        	<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				            <ui-select id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" theme="bootstrap" 
				            	ng-disabled="vm${entityName}.viewMode"<#if entityProperty.notNullAttribute> ng-required="true"</#if>>
								<ui-select-match allow-clear="true" placeholder="Selecione...">{{$select.selected.label}}</ui-select-match>
							    <ui-select-choices repeat="item.key as item in vm${entityName}.${entityProperty.propertyNameCamelCase}List | filter: { label: $select.search }">
							        <div ng-bind-html="item.label | highlight: $select.search"></div>
							    </ui-select-choices>
							</ui-select>
						<#if entityProperty.notNullAttribute>
			            	<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.required">obrigatório</span>
			            </#if>
				        </div>
					</div>
				</div>
		    </div>
		    
		    <#elseif entityProperty.temporalAttribute>
		    <div class="row">
				<div class="col-md-12">
		    		<div class="form-group" ng-class="{'has-error': ${entityName}Form.${entityProperty.propertyNameCamelCase}.$invalid}">
			    		<div class="col-sm-12">
				        	<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				<#if entityProperty.temporalDateAttribute>   
							<p class="input-group">
								<input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" type="text" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" ng-model-options="{timezone: 'utc'}" ng-disabled="vm${entityName}.viewMode"
									is-open="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened" close-text="Fechar"<#if entityProperty.notNullAttribute> ng-required="true"</#if>/>
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" ng-click="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened = true" ng-disabled="vm${entityName}.viewMode"><i class="glyphicon glyphicon-calendar"></i></button>
								</span>
					        </p>
				<#elseif entityProperty.temporalTimeAttribute>
							<div id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" uib-timepicker ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" ng-model-options="{timezone: 'utc'}" hour-step="1" minute-step="1" show-meridian="true" ng-disabled="vm${entityName}.viewMode"<#if entityProperty.notNullAttribute> ng-required="true"</#if>></div>
				<#else>
				            <p class="input-group">
								<input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" type="text" class="form-control" datetime-picker="dd/MM/yyyy HH:mm:ss" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" ng-model-options="{timezone: 'utc'}" ng-disabled="vm${entityName}.viewMode"
									is-open="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened" button-bar="{show: true, now: {show: true, text: 'Agora'}, today: {show: true, text: 'Hoje'}, clear: {show: true, text: 'Limpar'}, date: {show: true, text: 'Data'}, time: {show: true, text: 'Hora'}, close: {show: true, text: 'Fechar'}}"
									<#if entityProperty.notNullAttribute> ng-required="true"</#if>/>
								<span class="input-group-btn">
									<button type="button" class="btn btn-default" ng-click="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened = true" ng-disabled="vm${entityName}.viewMode"><i class="glyphicon glyphicon-calendar"></i></button>
								</span>
					        </p>
				</#if>
			            <#if entityProperty.notNullAttribute>
			            	<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.required">obrigatório</span>
			            </#if>
				        </div>
					</div>
				</div>
		    </div>
		    
			<#else>
			<div class="row">
				<div class="col-md-12">
		    		<div class="form-group" ng-class="{'has-error': ${entityName}Form.${entityProperty.propertyNameCamelCase}.$invalid}">
			    		<div class="col-sm-12">
				        	<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				            <input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" type="text" class="form-control" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}"
							<#if entityProperty.notNullAttribute>ng-required="true" </#if><#if entityProperty.stringAttribute>ng-maxlength="${entityProperty.attributeColumnLength}" </#if>ng-disabled="vm${entityName}.viewMode">
				            </input>
			            <#if entityProperty.notNullAttribute>
			            	<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.required">obrigatório</span>
			            </#if>
			            <#if entityProperty.stringAttribute>
			            	<#if entityProperty.notNullAttribute>
        					<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.minlength">O tamanho mínimo é 1</span>
			            	</#if>
			            	<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.maxlength">O tamanho máximo é ${entityProperty.attributeColumnLength}</span>
			            </#if>
				        </div>
					</div>
				</div>
		    </div>
		    
			</#if>
		<#else>
			<#if !entityProperty.listOrSetAttribute>
			<div class="row">
				<div class="col-md-12">
		    		<div class="form-group" ng-class="{'has-error': ${entityName}Form.${entityProperty.propertyNameCamelCase}.$invalid}">
			    		<div class="col-sm-12">
		    		<#if entityProperty.imageAttribute>
		    				<div class="row">
		    			<#if entityProperty.imageAttributePreview>
								<div class="col-md-4 box-solid">
									<img ng-src="{{vm${entityName}.${entityProperty.propertyNameCamelCase}Src ? vm${entityName}.${entityProperty.propertyNameCamelCase}Src : ''}}" ng-if="vm${entityName}.${entityProperty.propertyNameCamelCase}Src" />
								</div>
						</#if>
								<div class="col-md-2">
									<button id="alterar${entityProperty.propertyName}" name="alterar${entityProperty.propertyName}" 
										class="btn btn-block btn-primary" ng-disabled="vm${entityName}.viewMode" ng-click="modal${entityProperty.propertyName}.open()"><span class="fa fa-photo"></span> Selecionar</button>
								</div>
								
								<script type="text/ng-template" id="modal${entityProperty.propertyName}.tpl">
									<div class="modal-header">
										<h4 class="modal-title" id="modal${entityProperty.propertyName}Label">Selecionar ${entityProperty.propertyNameCamelCase}</h4>
									</div>
									<div class="modal-body">
										<div class="alert alert-error alert-dismissible" ng-show="vm${entityName}.modal${entityProperty.propertyName}.erros.length > 0">
											<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
											<h4><i class="icon fa fa-error"></i> Erro(s) encontrado(s)!</h4>
											<ul>
												<li ng-repeat="erro in vm${entityName}.modal${entityProperty.propertyName}.erros">{{erro}}</li>
											</ul>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
										    		<div class="col-sm-12">
											        	<button ngf-select ng-model="vm${entityName}.${entityProperty.propertyNameCamelCase}File" accept="image/*" class="btn btn-default" >Selecione o arquivo</button>
											        	<div ngf-drop ng-model="vm${entityName}.${entityProperty.propertyNameCamelCase}File" ngf-pattern="image/*" class="cropArea">
												            <img-crop image="vm${entityName}.${entityProperty.propertyNameCamelCase}File | ngfDataUrl"                 
												            	result-image="vm${entityName}.${entityProperty.propertyNameCamelCase}CroppedDataUrl" ng-init="vm${entityName}.${entityProperty.propertyNameCamelCase}CroppedDataUrl=''">
												            </img-crop>
												        </div>
											        </div>
												</div>
											</div>
									    </div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
										    		<div class="col-sm-12">
												        <div>
												            <img ng-src="{{vm${entityName}.${entityProperty.propertyNameCamelCase}CroppedDataUrl}}" />
												        </div>
											        </div>
												</div>
											</div>
									    </div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" ng-click="vm${entityName}.modal${entityProperty.propertyName}.close()">Cancelar</button>
										<button type="button" class="btn btn-primary" ng-click="vm${entityName}.modal${entityProperty.propertyName}.upload(vm${entityName}.${entityProperty.propertyNameCamelCase}CroppedDataUrl, vm${entityName}.${entityProperty.propertyNameCamelCase}File.name)">Confirmar</button>
									</div>
								</script>
							</div>
					<#else>
				    		<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				            <ui-select id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" theme="bootstrap" 
				            	ng-disabled="vm${entityName}.viewMode" <#if entityProperty.notNullAttribute> ng-required="true"</#if>>
								<ui-select-match allow-clear="true" placeholder="Selecione...">{{$select.selected.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase}}}</ui-select-match>
							    <ui-select-choices repeat="${entityProperty.propertyNameCamelCase} in vm${entityName}.${entityProperty.propertyNameCamelCase}List track by ${entityProperty.propertyNameCamelCase}.id"
							    	refresh="vm${entityName}.listar${entityProperty.propertyName}($select.search)" refresh-delay="300">
							        <div ng-bind-html="${entityProperty.propertyNameCamelCase}.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase} | highlight: $select.search"></div>
							    </ui-select-choices>
							</ui-select>
						<#if entityProperty.notNullAttribute>
			            	<span class="help-block error" ng-show="${entityName}Form.${entityProperty.propertyNameCamelCase}.$error.required">obrigatório</span>
		            	</#if>
			    	</#if>
				        </div>
					</div>
				</div>
		    </div>
		    
		    <#else>
		    
		    	<#if entityProperty.oneToMany && entityProperty.oneToManyWithMappedBy>
		    	
		    <div class="row">
				<div class="col-md-12">
					<div class="box">
						<div class="box-header">
							<h3 class="box-title">Lista de ${entityProperty.typeArgumentSimpleClassName}</h3>
							<div class="box-tools pull-right">
								<button id="openModal${entityProperty.typeArgumentSimpleClassName}" name="openModal${entityProperty.typeArgumentSimpleClassName}" class="btn btn-primary" ng-show="!vm${entityName}.viewMode" ng-click="vm${entityName}.openModal${entityProperty.typeArgumentSimpleClassName}()"
									title="Adicionar ${entityProperty.typeArgumentSimpleClassName}"><i class="fa fa-plus"></i><span class="hidden-xs"> Adicionar ${entityProperty.typeArgumentSimpleClassName}</span>
								</button>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="table-responsive">
								<table
									class="table table-responsive table-bordered table-striped clearfix">
									<thead>
										<tr>
											<th>${entityProperty.typeEntityMetamodel.propertyLabel.propertyName}</th>								
											<th></th>
										</tr>
									</thead>
									<tbody id="search-results-body">
										<tr ng-repeat="item in vm${entityName}.entity.${entityProperty.propertyNameCamelCase} track by $index">
											<td>{{item.${entityProperty.typeEntityMetamodel.propertyLabel.propertyNameCamelCase}}}</td>
											<td align="center">
												<button type="button" class="btn btn-danger" ng-click="vm${entityName}.remove${entityProperty.typeArgumentSimpleClassName}($index)"><i class="fa fa-trash"></i><span class="hidden-xs"> Remover</span></button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<!-- /.box-body -->
					</div>
				</div>
		    </div>
		    	
		    	<#else>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group" <#if entityProperty.notNullAttribute> ng-class="{'has-error': (vm${entityName}.entity.${entityProperty.propertyNameCamelCase}.length == 0)}"</#if>>
			    		<div class="col-sm-12">
				        	<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>
							<ui-select id="${entityProperty.propertyNameCamelCase}" multiple="true" ng-model="vm${entityName}.entity.${entityProperty.propertyNameCamelCase}" theme="bootstrap" ng-disabled="vm${entityName}.viewMode"<#if entityProperty.notNullAttribute> ng-required="true" required="true"</#if>>
								<ui-select-match placeholder="Selecione...">{{$item.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase}}}</ui-select-match>
							    <ui-select-choices repeat="${entityProperty.typeArgumentSimpleClassNameCamelCase} in vm${entityName}.${entityProperty.typeArgumentSimpleClassNameCamelCase}List track by ${entityProperty.typeArgumentSimpleClassNameCamelCase}.id"
							    	refresh="vm${entityName}.listar${entityProperty.typeArgumentSimpleClassName}($select.search)" refresh-delay="300">
							        <div ng-bind-html="${entityProperty.typeArgumentSimpleClassNameCamelCase}.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase} | highlight: $select.search"></div>
							    </ui-select-choices>
							</ui-select>
				        </div>
					</div>
				</div>
		    </div>
		    	</#if>
		    
			</#if>
		</#if>	
	</#if>
</#list>