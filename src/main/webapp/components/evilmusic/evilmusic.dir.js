'use strict';

angular.module('EvilMusicApp')
.directive('evilMusic', function(){
    return {
        restrict : 'E',
        scope : {},
        controller : 'EMController',
        controllerAs : 'ctrl',
        templateUrl : '/components/evilmusic/evilmusic.html'
    };
})
.controller('EMController', ['player', function(player) {
    var that = this;
    that.player = player;
}]);