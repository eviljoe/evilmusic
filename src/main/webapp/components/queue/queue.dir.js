angular.module('EvilMusicApp')
.directive('emQueue', function() {
    'use strict';

    return {
        scope : {},
        restrict : 'E',
        templateUrl : '/components/queue/queue.html',
        controller : 'EMQueueController',
        controllerAs : 'ctrl'
    };
})
.controller('EMQueueController', ['queue', 'player', function(queue, player) {
    'use strict';
    
    var that = this;
    that.queue  = queue;
    that.player = player;
}]);
