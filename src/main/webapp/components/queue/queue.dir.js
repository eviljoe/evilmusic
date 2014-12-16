'use strict';

angular.module('EvilMusicApp')
.directive('emQueue', function() {
    return {
        scope : {},
        restrict : 'E',
        templateUrl : '/components/queue/queue.html',
        controller : 'EMQueueController',
        controllerAs : 'ctrl'
    };
})
.controller('EMQueueController', ['queue', 'player', function(queue, player) {
    var that = this;
    that.queue  = queue;
    that.player = player;
}]);