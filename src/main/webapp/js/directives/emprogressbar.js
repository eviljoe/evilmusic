'use strict';

angular.module('EvilMusicApp').directive('emProgressBar', function() {
    return {
        'restrict' : 'E',
        'templateUrl' : 'emprogressbar.html',
        'scope' : {
            'progress' : '=',
            'onseek' : '='
        },
        'controller' : function($scope) {
            $scope.progressMeterClicked = function(xPos, width) {
                $scope.onseek(xPos / width);
            };
        }
    };
})
.directive('emProgressGutter', function() {
    return {
        'restrict' : 'A',
        'require' : ['^emProgressBar'],
        'link' : function link(scope, element, attrs) {
            element.on('click', function(event) {
                event.stopPropagation();
                scope.progressMeterClicked(event.offsetX, element.width());
            });
        }
    };
})
.directive('emProgressMeter', function() {
    return {
        'restrict' : 'A',
        'require' : ['^emProgressBar'],
        'link' : function link(scope, element, attrs) {
            scope.$watch('progress', function(value) {
                element.width(value + '%');
            });

            element.on('click', function(event) {
                event.stopPropagation();
                scope.progressMeterClicked(event.offsetX, element.parent().width());
            });
        }
    };
});
