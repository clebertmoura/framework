(function() {
    'use strict';

    angular
        .module('app.shared')
        .controller('FlashController', flashController);

    flashController.$inject = ['$scope', 'FlashFactory'];

    function flashController ($scope, FlashFactory) {
        var vm = this;
        vm.FlashFactory = FlashFactory;
        vm.showAlert = false;
        vm.hideAlert = hideAlert;

        $scope.$watch('vm.FlashFactory.getMessage()', function(newVal) {
            var message = newVal;
            if(message && message.text) {
                vm.showAlert = message.text.length > 0;
            } else {
                vm.showAlert = false;
            }
        });

        function hideAlert() {
            vm.showAlert = false;
        }
    };

})();