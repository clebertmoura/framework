(function() {
    'use strict';

    angular
        .module('app.shared')
        .controller('MenuController', menuController);

    menuController.$inject = ['ParametroSistemaService'];

    function menuController (ParametroSistemaService) {
        var vm = this;

        vm.openLink = openLink

        function openLink() {
            ParametroSistemaService.consultarParametroSistema('URL_VIAGEM_HOSPEDAGEM').then(function(param) {
                if(param != undefined && param != null) {
                    if(param.valor != null && param.valor != "") {
                        window.open(param.valor)
                    }
                }
            })
        }
    };

})();