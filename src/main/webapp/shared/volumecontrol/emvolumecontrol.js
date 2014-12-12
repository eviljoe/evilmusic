'use strict';

angular.module('EvilMusicApp').directive('emVolumeControl', function() {
    return {
        'restrict' : 'A',
        'templateUrl' : '/shared/volumecontrol/emvolumecontrol.html',
        'scope' : {
            'volume' : '=',
            'onvolumechange' : '='
        },
        'controller' : function($scope) {
            $scope.volumeChanged = function() {
                if($scope.onvolumechange) {
                    $scope.onvolumechange($scope.volume);
                }
            };
        }
    };
});
