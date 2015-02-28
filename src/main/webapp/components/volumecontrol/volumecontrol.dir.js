angular.module('EvilMusicApp')
.directive('emVolumeControl', function() {
    'use strict';

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
