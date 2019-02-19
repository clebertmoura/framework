<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>
		${entityName} <small>Cadastro</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li><a href="#">Cadastros</a></li>
		<li class="active">${entityName}</li>
	</ol>
</section>

<!-- Main content -->
<section class="content">

	<div class="box">
		<div class="box-header with-border">
			<h3 ng-show="vm${entityName}.editMode && !vm${entityName}.entity.id">Criar ${entityName}</h3>
			<h3 ng-show="vm${entityName}.editMode && vm${entityName}.entity.id">Editar ${entityName}</h3>
			<h3 ng-show="vm${entityName}.viewMode">Visualizar ${entityName}</h3>
		</div>
		<div class="box-body">
			<form id="${entityName}Form" name="${entityName}Form" class="form-horizontal" role="form">
			    
				<ng-include src="'app/${entityNameCamelCase}/detail-inc.html'"></ng-include>
				
	    		<div class="row">
					<div class="col-md-12">
					    <div class="form-group">
					        <div class="col-sm-12">
					            <button id="edit" name="edit" class="btn btn-warning" ng-show="vm${entityName}.viewMode" ng-click="vm${entityName}.editInView()"><span class="glyphicon glyphicon-ok-sign"></span> Editar</button>
					            <button id="save" name="save" class="btn btn-primary" ng-disabled="vm${entityName}.viewMode || vm${entityName}.isClean() || ${entityName}Form.$invalid" ng-click="vm${entityName}.save()"><span class="glyphicon glyphicon-ok-sign"></span> Salvar</button>
					            <button id="cancel" name="cancel" class="btn btn-default" ng-click="vm${entityName}.cancel()"><span class="glyphicon glyphicon-remove-sign"></span> Cancelar</button>
					        </div>
					    </div>
					</div>
				</div>
			</form>
		</div>
		
	</div>

</section>
<!-- /.content -->