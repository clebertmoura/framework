/**
 * Created by rbssa on 21/03/2016.
 */

'use strict';

angular.module('app.shared').factory('AuthInterceptor', function($q, AuthenticationService, Constants) {
    return {
        request: function (config) {
            var deferred = $q.defer();
            if (AuthenticationService.authz.token) {
                AuthenticationService.authz.updateToken(15).success(function(refreshed) {
                    //if (refreshed) {
                    config.headers = config.headers || {};
                    config.headers.Authorization = 'Bearer ' + AuthenticationService.authz.token;
                    var item = localStorage.getItem(Constants.headerTokenName);
                    if(undefined === item || null === item || item.length === 0) {
                        localStorage.setItem(Constants.headerTokenName, config.headers.Authorization);
                    }
                    //}
                    deferred.resolve(config);
                }).error(function() {
                    deferred.reject('Failed to refresh token');
                });
            }
            return deferred.promise;
        }
    };
});

