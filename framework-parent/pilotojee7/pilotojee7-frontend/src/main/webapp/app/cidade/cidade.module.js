(function() {
    'use strict';
    
angular
	.module('app.cidade', [
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
		'angularUtils.directives.dirPagination', 
		'ngRoute',
		'app.shared'
	]);
		
})();		