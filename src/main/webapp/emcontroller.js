'use strict';

angular.module('EvilMusicApp', [])
.controller('EMController',
['$scope', 'queue', 'eq', 'player', function($scope, queue, eq, player) {

    $scope.player = player;
    $scope.currentSong = null;
    $scope.eq = eq;
    $scope.queue = queue;

}]);
