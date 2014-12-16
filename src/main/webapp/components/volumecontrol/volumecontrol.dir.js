'use strict';

angular.module('EvilMusicApp')
.directive('emVolumeControl', function() {
    return {
        'restrict' : 'A',
        'templateUrl' : '/components/volumecontrol/volumecontrol.html',
        'scope' : {
            'volume' : '=',
            'onvolumechange' : '='
        },
        'controller' : ['$scope', function($scope) {
            $scope.volumeChanged = function() {
                if($scope.onvolumechange) {
                    $scope.onvolumechange($scope.volume);
                }
            };
        }]
    };
});
