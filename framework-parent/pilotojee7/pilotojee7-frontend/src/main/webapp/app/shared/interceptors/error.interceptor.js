(function() {
    'use strict';

    angular
        .module('app.shared')
        .factory('ErrorInterceptor', errorInterceptor);

    errorInterceptor.$inject = ['$q', '$window', '$location', '$injector', '$rootScope'];

    function errorInterceptor ($q, $window, $location, $injector, $rootScope) {

        $rootScope.modalError = null;
        $rootScope.closeModalError = function() {
            $rootScope.modalError.close(null);
        };

        return {
            // request: function (config) {
            //     console.log("intercept - request", config.url);
            //     config.headers = config.headers || {};
            //     // insert code to populate your request header for instance
            //     config.headers.serviceKey = appConfig.serviceKey;
            //     var isLoginRequest = config.url.indexOf(appConfig.backendUrl + "auth/login") > -1;
            //     var isRecuperarSenhaRequest = config.url.indexOf(appConfig.backendUrl + "auth/recuperarSenha") > -1;
            //     if (!isLoginRequest && !isRecuperarSenhaRequest && $window.localStorage.getItem(appConfig.tokenKey) !== null) {
            //         config.headers.authToken = $window.localStorage.getItem(appConfig.tokenKey);
            //     }
            //     return config;
            // },
            response: function (response) {
                console.log("intercept - response", response.status);
                if (response.status === 403 || response.status === 401) {
                    console.log("Sem acesso");
                    // insert code to redirect to custom unauthorized page
                    $location.path("/");
                }
                return response || $q.when(response);
            }
            // responseError: function (response) {
            //     var uibModal = $injector.get('$uibModal');
            //     console.log("intercept - responseError", response.status);
            //     if (response.status === 403 || response.status === 401) {
            //         console.log("intercept - responseError. Sem acesso");
            //         //var isLoginRequest = response.config.url.indexOf(appConfig.backendUrl + "auth/login") > -1;
            //         //if (!isLoginRequest) {
            //             // insert code to redirect to custom unauthorized page
            //             var login = angular.element('$login-holder');
            //             var content = angular.element('$content-holder');
            //             login.slideDown('slow', function() {
            //                 content.hide();
            //             });
            //         //}
            //     } else if (response.status >= 500) {
            //         if ($rootScope.modalError == null) {
            //             $rootScope.modalError = uibModal.open({
            //                 template: "<div class='error-modal modal-danger'><div class='modal-header'><h4 class='modal-title'>Erro inesperado!</h4></div><div class='modal-body'><div class='alert alert-error'>Desculpe, ocorreu um erro inesperado! Por favor, tente novamente em alguns minutos ou entre em contato com o suporte.</div></div><div class='modal-footer'><button type='button' class='btn btn-outline' ng-click='closeModalError()'>Fechar</button></div></div>"
            //             });
            //             $rootScope.modalError.closed.then(function() {
            //                 $rootScope.modalError = null;
            //             });
            //         }
            //     } else {
            //         if ($rootScope.modalError == null) {
            //             $rootScope.modalError = uibModal.open({
            //                 template: "<div class='error-modal modal-danger'><div class='modal-header'><h4 class='modal-title'>Erro de conexão!</h4></div><div class='modal-body'><p>Por favor, verifique sua conexão com a internet!</p></div><div class='modal-footer'><button type='button' class='btn btn-outline' ng-click='closeModalError()'>Fechar</button></div></div>"
            //             });
            //             $rootScope.modalError.closed.then(function() {
            //                 $rootScope.modalError = null;
            //             });
            //         }
            //     }
            //     return response || $q.when(response);
            // }
        };
    };

})();