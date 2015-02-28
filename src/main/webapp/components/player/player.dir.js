angular.module('EvilMusicApp')
.directive('emPlayerControls', function() {
    'use strict';

    return {
        restrict : 'E',
        scope : {},
        controller : 'EMPlayerControlsController',
        controllerAs : 'ctrl',
        templateUrl : '/components/player/player.html'
    };
})
.controller('EMPlayerControlsController', ['player', function(player) {
    'use strict';

    var that = this;
    that.player = player;
}]);
