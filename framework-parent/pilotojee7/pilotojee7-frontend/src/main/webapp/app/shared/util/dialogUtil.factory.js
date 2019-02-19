(function() {
    'use strict';

    angular.module('app.shared').factory('DialogUtil', function(){
    	function show(dialog){
    		alert(dialog.message);
    	}
    	
    	return {
    		show: show
    	}
    });

})();