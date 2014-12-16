'use strict';

angular.module('EvilMusicApp')
.directive('emPlayerControls', function() {
    return {
        restrict : 'E',
        scope : {},
        controller : 'EMPlayerControlsController',
        controllerAs : 'ctrl',
        templateUrl : '/components/player/player.html'
    };
})
.controller('EMPlayerControlsController', ['player', function(player) {
    var that = this;
    that.player = player;
}]);
