'use strict';

angular.module('EvilMusicApp', [])
.controller('EMLibraryController', ['$scope', '$http', 'emUtils', 'queue', 'eq',
function($scope, $http, emUtils, queue, eq) {

    $scope.library = [];
    $scope.player = null;
    $scope.playerProgress = 0;
    $scope.currentSong = null;
    $scope.webAudioNodes = {};
    $scope.volume = 100;
    $scope.eq = eq;
    $scope.queue = queue;
    $scope.emUtils = emUtils;

    /** Loads the contents of the library using a REST call. */
    $scope.loadLibrary = function() {
        $http.get('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.library = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get library.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
     * be reloaded.
     */
    $scope.clearLibrary = function() {
        $http.delete('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                queue.load(true);
            })
            .error(function (data, status, headers, config) {
                alert('Clear library failed.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Makes a REST call to rebuild the library.  This will delete all songs from the library.  Next, the server's music
     * directories (from the preferences file) will be read to repopulate the library.  After the library has been
     * rebuild, the it and the queue will be reloaded.
     */
    $scope.rebuildLibrary = function() {
        $http.post('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                queue.load(true);
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            });
    };

    $scope.play = function(queueIndex) {
        if($scope.player) {
            $scope.player.stop();
        }

        if(queue.q && queue.q.id) {
            var song = queue.getSong(queueIndex);

            $scope.player = AV.Player.fromURL(
                '/rest/queue/' + queue.q.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');

            $scope.player.nodeCreationCallback = eq.createEQNodes;
            $scope.player.volume = $scope.volume;
            $scope.player.on('progress', function(progress) {
                $scope.playerProgress = progress / song.millis * 100;
                $scope.$apply();
            });
            $scope.player.on('end', function() {
                $scope.player = null;
                $scope.currentSong = null;
            });

            $scope.currentSong = song;
            $scope.player.play();
            queue.load();
        }
    };

    $scope.seekToMillis = function(millis) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.player.seek(millis);
        }

        return newMillis;
    };

    $scope.seekToPercent = function(percent) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.seekToMillis(Math.floor($scope.player.duration * percent));
        }

        return newMillis;
    };

    $scope.progressBarClicked = function(event) {
        var x = event.offsetX;
        var width;

        if(event.target.id === 'progressBarGutter') {
            width = event.target.offsetWidth;
        } else {
            width = document.getElementById('progressBarGutter').offsetWidth;
        }

        $scope.seekToPercent(x / width);
    };

    $scope.togglePlayback = function() {
        var toggled = false;

        if($scope.player) {
            $scope.player.togglePlayback();
            toggled = true;
        }

        return toggled;
    };

    $scope.setVolume = function(volume) {
        if($scope.player) {
            $scope.player.volume = volume;
        }

        $scope.volume = volume;

        $http.put('/rest/config/volume/' + volume)
            .error(function (data, status, headers, config) {
                alert('Could not update volume.\n\n' + JSON.stringify(data));
            });
    };

    $scope.loadVolume = function() {
      $http.get('/rest/config/volume')
            .success(function (data, status, headers, config) {
                $scope.volume = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not load volume.\n\n' + JSON.stringify(data));
            });
    };

    $scope.loadVolume();
    $scope.loadLibrary();
}]);
