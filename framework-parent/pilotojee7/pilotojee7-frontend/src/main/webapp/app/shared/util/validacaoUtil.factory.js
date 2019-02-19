(function() {
    'use strict';

    angular.module('app.shared').factory('ValidacaoUtil', function(){
    	// função que verifica se o email informado é válido	
    	function isEmail(email){		
    		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    		return re.test(email);
    	};
    	// função que verifica se o CPF informado é válido
    	function isCPF(cpf){		
    		// garante que o CPFseja validado sem pontuação
    		cpf = cpf.replace(/\./g, "");
    		cpf = cpf.replace(/\-/g, "");		
    		
    		var numeros,digitos,soma,i,resultado,digitosIguais;
    		digitosIguais = 1;
    		
    		if (cpf.length < 11){
    			return false;
    		}
    		
    		for (i = 0; i < cpf.length - 1; i++){
    			if (cpf.charAt(i) != cpf.charAt(i + 1)){
    				digitosIguais = 0;
    				break;
    			}
    		}
    		
    		if (!digitosIguais){
    			numeros = cpf.substring(0,9);
    			digitos = cpf.substring(9);
    			soma = 0;
    			
    			for (i = 10; i > 1; i--){
    				soma += numeros.charAt(10 - i) * i;
    			}
    			
    			resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    			
    			if (resultado != digitos.charAt(0)){
    				return false;
    			}
    			
    			numeros = cpf.substring(0,10);			
    			soma = 0;
    			
    			for (i = 11; i > 1; i--){
    				soma += numeros.charAt(11 - i) * i;
    			}

    			resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

    			if (resultado != digitos.charAt(1)){
    				return false;
    			}

    			return true;
    		}else{
    			return false;
    		}
    	}
    	function isCNPJ(cnpj){		
    		cnpj = cnpj.replace(/[^\d]+/g,'');

    		if(cnpj == ''){
    			return false;
    		}

    		if (cnpj.length != 14){
    			return false;
    		}

    		// elimina CNPJs inválidos conhecidos
    		if (
    				cnpj == "00000000000000" || 
    				cnpj == "11111111111111" || 
    				cnpj == "22222222222222" || 
    				cnpj == "33333333333333" || 
    				cnpj == "44444444444444" || 
    				cnpj == "55555555555555" || 
    				cnpj == "66666666666666" || 
    				cnpj == "77777777777777" || 
    				cnpj == "88888888888888" || 
    				cnpj == "99999999999999"
    		){
    			return false;
    		}

    		// valida DVs
    		tamanho = cnpj.length - 2;
    		numeros = cnpj.substring(0,tamanho);
    		digitos = cnpj.substring(tamanho);
    		soma    = 0;
    		pos     = tamanho - 7;

    		for(i = tamanho; i >= 1; i--){
    			soma += numeros.charAt(tamanho - i) * pos--;

    			if (pos < 2){
    				pos = 9;
    			}
    		}

    		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

    		if (resultado != digitos.charAt(0)){
    			return false;
    		}

    		tamanho = tamanho + 1;
    		numeros = cnpj.substring(0,tamanho);
    		soma    = 0;
    		pos     = tamanho - 7;

    		for (i = tamanho; i >= 1; i--) {
    			soma += numeros.charAt(tamanho - i) * pos--;
    			if (pos < 2){
    				pos = 9;
    			}
    		}

    		resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;

    		if (resultado != digitos.charAt(1)){
    			return false;
    		}

    		return true;
    	}
    	
    	return {
    		isEmail: isEmail,
    		isCPF: isCPF,
    		isCNPJ: isCNPJ
    	};
    });

})();