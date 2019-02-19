/**
 */
(function() {
    'use strict';

    
angular
    .module('app.shared')
    .controller('LoginController', loginController);

loginController.$inject = ['$scope', 'AuthenticationService'];

function loginController ($scope, AuthenticationService) {
    $scope.logout = function(){
        console.log('*** LOGOUT ***');
        localStorage.removeItem('X-Authorization-Token');
        AuthenticationService.loggedIn = false;
        AuthenticationService.authz.logout();
        //AuthenticationFactory.authz = null;
        //window.location = AuthenticationFactory.logoutUrl;

    };

    $scope.getLoginAutenticado = function() {
        return AuthenticationService.userInfo.preferred_username;
    };
    
    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }

    $scope.getNomeCompleto = function() {
        return AuthenticationService.userInfo.name;
    };
    
    $scope.getPrimeiroUltimoNome = function() {
    	var firstName = AuthenticationService.userInfo.given_name;
    	var lastName = AuthenticationService.userInfo.name.split(' ').slice(-1).join(' ');
    	//console.debug(firstName + ' ' + lastName);
    	return firstName.toLowerCase().capitalize() + ' ' + lastName.toLowerCase().capitalize();
    };

    $scope.getPrimeiroNome = function() {
        return AuthenticationService.userInfo.given_name.toLowerCase().capitalize();
    };

    $scope.getEmail = function() {
        return AuthenticationService.userInfo.email;
    };

};


})();