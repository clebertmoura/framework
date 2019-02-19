(function() {
    'use strict';

    angular.module('app.shared').factory('StringUtil', function(){
    	function removerPontuacao(texto){
    		return texto.replace(/\./g, "").replace(/\-/g, "").replace(/\,/g, "").replace(/\\/g, "").replace(/\//g, "");
    	}
    	function formatarCNPJ(cnpj){
    		var cnpjFormatado = cnpj.substr(0,2) + '.' + cnpj.substr(2,3) + '.' + cnpj.substr(5,3) + '/' + cnpj.substr(8,4) + '-' + cnpj.substr(12,2);
    		return cnpjFormatado
    	}
    	
    	return {
    		removerPontuacao: removerPontuacao,
    		formatarCNPJ: formatarCNPJ
    	}
    });

})();