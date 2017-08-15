(function() {
    'use strict';
    
	angular
		.module('app.imagem')
		.service('ImagemService', ImagemService);
		
	ImagemService.$inject = ['ImagemFactory'];
	
	function ImagemService(ImagemFactory) {
	
		var vm = this;
		
		vm.imagemList = vm.imagemList || [];
		vm.listarImagem = listarImagem;
		
		function listarImagem(valorCampo) {
			var vRestrictions = [];
	    	var vOrderings = [];
	    	if (valorCampo && valorCampo != null && valorCampo != "") {
				vRestrictions.push({field: 'nome', operator: 'LI', value: valorCampo});
	    	}
			vOrderings.push({field: 'nome', order: 'ASC'});
			return ImagemFactory.queryAll(
				{
					first: 0, max: 10, 
					restrictions: vRestrictions, 
	               	orderings: vOrderings
				}, 
				function(items){
					angular.copy(items.results, vm.imagemList);
	        	}
	        );
		}
		
	};

})();