<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		${entityName} <small>Listagem</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li><a href="#">Cadastros</a></li>
		<li class="active">${entityName}</li>
	</ol>
</section>

<!-- Main content -->
<section class="content">

	<!-- Box das ações no formulário -->
	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">Ações</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
		</div>
		<div class="box-body">
			<a class="btn btn-primary btn-sm" href="#/${entityName}/new"><i class="fa fa-plus"></i> Criar</a>
		</div>
	</div>

	<!-- Box dos filtros -->
	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">Filtro</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
		</div>
		<div class="box-body">
			<form id="${entityName}Search">
			
			
	<#list entityProperties as entityProperty>
		<#if !entityProperty.transient && !entityProperty.imageAttribute>
			<#if entityProperty.simpleAttribute>
				<#if entityProperty.enumerated>
				
				<div class="row">
					<div class="col-md-12">
			    		<div class="form-group">
				    		<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				            <ui-select id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}" theme="bootstrap">
								<ui-select-match allow-clear="true" placeholder="Selecione...">{{$select.selected.label}}</ui-select-match>
							    <ui-select-choices repeat="item.key as item in vm${entityName}.${entityProperty.propertyNameCamelCase}List | filter: { label: $select.search }">
							        <div ng-bind-html="item.label | highlight: $select.search"></div>
							    </ui-select-choices>
							</ui-select>
						</div>
					</div>
			    </div>
			    
			    <#elseif entityProperty.temporalAttribute>
			    
			    <div class="row">
					<div class="col-md-12">
			    		<div class="form-group">
				    		<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
					<#if entityProperty.temporalDateAttribute>   
								<p class="input-group">
									<input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" type="text" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}"
										ng-model-options="{timezone: 'utc'}" is-open="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened" close-text="Fechar" />
									<span class="input-group-btn">
										<button type="button" class="btn btn-default" ng-click="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened = true"><i class="glyphicon glyphicon-calendar"></i></button>
									</span>
						        </p>
					<#elseif entityProperty.temporalTimeAttribute>
								<div id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" uib-timepicker ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}" ng-model-options="{timezone: 'utc'}" hour-step="1" minute-step="1" show-meridian="true"></div>
					<#else>
					            <p class="input-group">
									<input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" type="text" class="form-control" datetime-picker="dd/MM/yyyy HH:mm:ss" ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}" ng-model-options="{timezone: 'utc'}"
										is-open="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened" button-bar="{show: true, now: {show: true, text: 'Agora'}, today: {show: true, text: 'Hoje'}, clear: {show: true, text: 'Limpar'}, date: {show: true, text: 'Data'}, time: {show: true, text: 'Hora'}, close: {show: true, text: 'Fechar'}}" />
									<span class="input-group-btn">
										<button type="button" class="btn btn-default" ng-click="vm${entityName}.${entityProperty.propertyNameCamelCase}PopupOpened = true"><i class="glyphicon glyphicon-calendar"></i></button>
									</span>
						        </p>
					</#if>
						</div>
					</div>
			    </div>
			    
				<#else>
				
				<div class="row">
					<div class="col-md-12">
			    		<div class="form-group">
					    	<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
					        <input id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" class="form-control" type="text" ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}" placeholder="Filtrar por ${entityProperty.propertyNameCamelCase}"></input>
					    </div>
					</div>
			    </div>
			    
				</#if>
			<#else>
				<#if !entityProperty.listAttribute>
				
				<div class="row">
					<div class="col-md-12">
			    		<div class="form-group">
				    		<label for="${entityProperty.propertyNameCamelCase}">${entityProperty.propertyName}</label>					        
				            <ui-select id="${entityProperty.propertyNameCamelCase}" name="${entityProperty.propertyNameCamelCase}" ng-model="vm${entityName}.searchData.${entityProperty.propertyNameCamelCase}" theme="bootstrap">
								<ui-select-match allow-clear="true" placeholder="Selecione...">{{$select.selected.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase}}}</ui-select-match>
							    <ui-select-choices repeat="${entityProperty.propertyNameCamelCase} in vm${entityName}.${entityProperty.propertyNameCamelCase}List track by ${entityProperty.propertyNameCamelCase}.id"
							    	refresh="vm${entityName}.listar${entityProperty.propertyName}($select.search)" refresh-delay="300">
							        <div ng-bind-html="${entityProperty.propertyNameCamelCase}.${entityProperty.typeEntityPropertyLabel.propertyNameCamelCase} | highlight: $select.search"></div>
							    </ui-select-choices>
							</ui-select>
						</div>
					</div>
			    </div>
				
				</#if>
			</#if>	
		</#if>
	</#list>
	
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
				            <a id="Search" name="Search" class="btn btn-primary" ng-click="vm${entityName}.search()"><span class="glyphicon glyphicon-search"></span> Buscar</a>
				        </div>
					</div>
				</div>		        
		    </form>
		</div>
	</div>

	<!-- Box da listagem -->
	<div class="box">
		<div class="box-header with-border">
			<h3 class="box-title">Listagem</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
		</div>
		<div class="box-body">
			<div id="search-results">
				<div class="table-responsive">
					<table
						class="table table-responsive table-bordered table-striped clearfix">
						<thead>
							<tr>
								<th>${entityMetamodel.propertyLabel.propertyName}</th>								
								<th width="20%">Ações</th>
							</tr>
						</thead>
						<tbody id="search-results-body">
							<tr ng-repeat="result in vm${entityName}.filteredResults | limitTo: vm${entityName}.pageSize">
								<td><a href="" ng-click="vm${entityName}.view(result)">{{result.${entityMetamodel.propertyLabel.propertyNameCamelCase}}}</a></td>
								<td align="center">
									<button type="button" class="btn btn-warning" ng-click="vm${entityName}.edit(result)"><i class="fa fa-edit"></i><span class="hidden-xs"> Editar</span></button>
									<button type="button" class="btn btn-danger" ng-click="vm${entityName}.remove(result)"><i class="fa fa-trash"></i><span class="hidden-xs"> Remover</span></button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<ul uib-pagination total-items="vm${entityName}.totalRecords"
					items-per-page="vm${entityName}.registersPerPage"
					first-text="<<" previous-text="<" next-text=">" last-text=">>"
					ng-change="vm${entityName}.setPage()"
					ng-model="vm${entityName}.currentPage" max-size="vm${entityName}.pageSize" class="pagination-sm"
					boundary-links="true" ></ul>
			</div>
		</div>
	</div>
</section>
<!-- /.content -->