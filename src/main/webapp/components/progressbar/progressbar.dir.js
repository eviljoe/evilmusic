angular.module('EvilMusicApp')
.directive('emProgressBar', function() {
    'use strict';

    return {
        restrict : 'E',
        templateUrl : '/components/progressbar/progressbar.html',
        scope : {
            onseek : '='
        },
        link : function (scope, element, attrs) {
            scope.barElem = element;
            scope.gutterElem = angular.element(element[0].querySelector('.em-progress-gutter'));
            scope.meterElem = angular.element(element[0].querySelector('.em-progress-meter'));

            scope.gutterElem.on('click', function(event) {
                event.stopPropagation();
                scope.progressMeterClicked(event.offsetX, scope.gutterElem.width());
            });

            scope.meterElem.on('click', function(event) {
                event.stopPropagation();
                scope.progressMeterClicked(event.offsetX, scope.gutterElem.width());
            });

            scope.updateMeterWidth();
        },
        controller : 'EMProgressBarController',
        controllerAs : 'ctrl'
    };
})
.controller('EMProgressBarController',
['$scope', '$rootScope', 'player', 'emUtils', function($scope, $rootScope, player, emUtils) {
    
    'use strict';

    $scope.progressMeterClicked = function(xPos, width) {
        if(emUtils.isNumber(xPos) && emUtils.isNumber(width)) {
            $scope.onseek(xPos / width);
        }
    };

    $scope.updateMeterWidth = function() {
        var p = player.playerProgress;

        if(!emUtils.isNumber(p)) {
            p = 0;
        } else {
            p = Math.max(0, p);
            p = Math.min(100, p);
        }

        $scope.meterElem.width(p + '%');

    };

    $rootScope.$on(player.playerProgressChangedEventName, $scope.updateMeterWidth);
}]);
