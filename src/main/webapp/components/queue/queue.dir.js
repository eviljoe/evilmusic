'use strict';

angular.module('EvilMusicApp')
.directive('emQueue', function() {
    return {
        scope: {},
        restrict: 'E',
        templateUrl: '/components/queue/queue.html',
        controller: 'EMQueueController',
        controllerAs: 'ctrl'
    };
})
.controller('EMQueueController', ['queue', function(queue) {
    var that = this;
    that.queue  = queue;
}]);