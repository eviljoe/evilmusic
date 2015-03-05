angular.module('EvilMusicApp')
.directive('evilMusic', function(){
    'use strict';
    
    return {
        restrict : 'E',
        scope : {},
        controller : 'EMController',
        controllerAs : 'ctrl',
        templateUrl : '/components/evilmusic/evilmusic.html'
    };
})
.controller('EMController', ['player', function(player) {
    'use strict';
    
    var that = this;
    that.player = player;
}]);