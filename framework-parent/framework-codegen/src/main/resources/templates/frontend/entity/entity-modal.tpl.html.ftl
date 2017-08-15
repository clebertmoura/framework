<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header modal-primary">
			<div class="row">
				<div class="col-lg-9">
					<h3>${entityName}</h3>
				</div>
			</div>
		</div>
		<div class="modal-body">
			<form id="${entityName}Form" name="${entityName}Form" class="form-horizontal" role="form">
			
				<ng-include src="'app/${entityNameCamelCase}/detail-inc.html'"></ng-include>
				
				<div class="modal-footer">
					<button class="btn btn-primary" ng-click="vm${entityName}.onClickModalConfirmar()">Confirmar</button>
					<button class="btn btn-warning" ng-click="vm${entityName}.onClickModalCancelar()">Cancelar</button>
				</div>
			</form>
		</div>
	</div>
</div>
