angular.module('EvilMusicApp')
.directive('emEq', function() {
    'use strict';
    
    return {
        restrict : 'E',
        scope : {},
        controller : 'EMEQController',
        controllerAs : 'ctrl',
        templateUrl : '/components/eq/eq.html'
    };
})
.controller('EMEQController', ['eq', 'emUtils', function(eq, emUtils) {
    'use strict';
    
    var that = this;
    that.eq = eq;
    that.emUtils = emUtils;
}]);
