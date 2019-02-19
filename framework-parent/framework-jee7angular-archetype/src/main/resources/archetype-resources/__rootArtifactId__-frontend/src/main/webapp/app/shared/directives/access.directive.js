/**
 */
'use strict';

angular.module('app.shared').directive('access',
    function (AuthorizationFactory, Constants) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var makeVisible = function () {
                        element.removeClass('hidden');
                    },
                    makeHidden = function () {
                        element.addClass('hidden');
                    },
                    determineVisibility = function (resetFirst) {
                        var result;
                        if (resetFirst) {
                            makeVisible();
                        }

                        result = AuthorizationFactory.authorize(true, roles, attrs.accessPermissionType);
                        if (result === Constants.enums.authorised.authorised) {
                            makeVisible();
                        } else {
                            makeHidden();
                        }
                    },
                    roles = attrs.access.split(',');

                if (roles.length > 0) {
                    determineVisibility(true);
                }
            }
        };
    });