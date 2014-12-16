'use strict';

angular.module('EvilMusicApp')
.directive('emEq', function() {
    return {
        restrict : 'E',
        scope : {},
        controller : 'EMEQController',
        controllerAs : 'ctrl',
        templateUrl : '/components/eq/eq.html'
    };
})
.controller('EMEQController', ['eq', 'emUtils', function(eq, emUtils) {
    var that = this;
    that.eq = eq;
    that.emUtils = emUtils;
}]);
