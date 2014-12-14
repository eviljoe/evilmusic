'use strict';

angular.module('EvilMusicApp')
.factory('emPlayer', ['$http', 'queue', function($http, queue) {

    var that = this;
    var avPlayer = null;
    var currentSong = null;
    var playerProgress = null;

    that.play = function(queueIndex) {
        // JOE todo
    };

    that.togglePlayback = function() {
        // JOE todo
    };

    that.seekToMillis = function() {
        // JOE todo
    };

    that.seekToPercent = function() {
        // JOE todo
    };

    return that;
}]);
