(function() {
    'use strict';

    angular
        .module('app')
        .service('AuthorizationService', authorizationFactory);
    
    authorizationFactory.$inject = ['AuthenticationService', 'Constants'];
    
    function authorizationFactory(AuthenticationService, Constants) {
        //console.debug('authorize ');
        var service = {
            authorize: authorize
        };

        return service;

        function authorize (loginRequired, requiredPermissions, permissionCheckType) {
            var result = Constants.enums.authorised.authorised,
                user = AuthenticationService.userInfo,
                loweredPermissions = [],
                hasPermission = true,
                permission, i;
            permissionCheckType = permissionCheckType || Constants.enums.permissionCheckType.atLeastOne;
            if (loginRequired === true && user === undefined) {
                result = Constants.enums.authorised.loginRequired;
            } else if ((loginRequired === true && user !== undefined) &&
                (requiredPermissions === undefined || requiredPermissions.length === 0)) {
                // Login is required but no specific permissions are specified.
                result = Constants.enums.authorised.authorised;
            } else if (requiredPermissions) {
                loweredPermissions = [];
                angular.forEach(user.permissions, function (permission) {
                    loweredPermissions.push(permission.toLowerCase());
                });

                for (i = 0; i < requiredPermissions.length; i += 1) {
                    permission = requiredPermissions[i].toLowerCase();

                    if (permissionCheckType === Constants.enums.permissionCheckType.combinationRequired) {
                        hasPermission = hasPermission && loweredPermissions.indexOf(permission) > -1;
                        // if all the permissions are required and hasPermission is false there is no point carrying on
                        if (hasPermission === false) {
                            break;
                        }
                    } else if (permissionCheckType === Constants.enums.permissionCheckType.atLeastOne) {
                        hasPermission = loweredPermissions.indexOf(permission) > -1;
                        // if we only need one of the permissions and we have it there is no point carrying on
                        if (hasPermission) {
                            break;
                        }
                    }
                }

                result = hasPermission ? Constants.enums.authorised.authorised : Constants.enums.authorised.notAuthorised;
            }

            return result;
        };
    };

})();