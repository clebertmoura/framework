(function() {
    'use strict';
    
angular
	.module('app.relatorio.filtro', [
		'angular-button-spinner',	
		'displayMask',
		'ngResource', 
		'ngSanitize',
		'ngFileUpload', 
		'ngImgCrop',
		'ui.bootstrap', 
		'ui.bootstrap.datepickerPopup', 
		'ui.bootstrap.datepicker', 
		'ui.bootstrap.datetimepicker',
		'ui.mask',
		'ui.select',
		'ui.thumbnail',
		'ui.checkbox',
		'angularUtils.directives.dirPagination', 
		'ngRoute',
		'app.shared'
	]);
		
})();		