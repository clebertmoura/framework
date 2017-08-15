(function() {
    'use strict';

 angular
        .module('app.shared')
        .factory('FlashFactory', flashFactory);

    
  flashFactory.$inject = ['$rootScope'];

  function flashFactory ($rootScope) {
    var messages = [];
    var currentMessage = {};

    var service = {
        setMessage: setMessage,
        getMessage: getMessage
    };

    $rootScope.$on('$routeChangeSuccess', function() {
      currentMessage = messages.shift() || {};
    });

    function getMessage() {
      return currentMessage;
    }
      
    function setMessage(message, pop) {
      switch(message.type) {
          case "error" : message.cssClass = "danger"; break;
          case "success" : message.cssClass = "success"; break;
          case "info" : message.cssClass = "info"; break;
          case "warning" : message.cssClass = "warning"; break;
      }
      messages.push(message);
      if(pop) {
        currentMessage = messages.shift() || {};
      }
    }

    return service;
      
  };

})();

/*
'use strict';

angular.module(project.app.name).factory('flash', ['$rootScope', function ($rootScope) {
    var messages = [];
    var currentMessage = {};

    $rootScope.$on('$routeChangeSuccess', function() {
      currentMessage = messages.shift() || {};
    });

    return {
      getMessage: function () {
        return currentMessage;
      },
      setMessage: function(message, pop) {
        switch(message.type) {
            case "error" : message.cssClass = "danger"; break;
            case "success" : message.cssClass = "success"; break;
            case "info" : message.cssClass = "info"; break;
            case "warning" : message.cssClass = "warning"; break;
        }
        messages.push(message);
        if(pop) {
          currentMessage = messages.shift() || {};
        }
      }
    };
}]);


<div ng-controller="FlashController" class="alert alert-dismissible" ng-class="'alert-' + flash.getMessage().cssClass" role="alert" ng-show="showAlert">
                <button type="button" class="close" ng-click="hideAlert()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span ng-if="flash.getMessage() !== ''">
              {{flash.getMessage().text}}
          </span>

            </div>
*/