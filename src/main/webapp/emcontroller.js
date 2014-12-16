'use strict';

angular.module('EvilMusicApp', [])
.controller('EMController',
['$scope', 'queue', 'eq', 'player', function($scope, player) {

    $scope.player = player;

}]);
