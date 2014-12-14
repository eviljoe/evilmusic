'use strict';

angular.module('EvilMusicApp', [])
.controller('EMLibraryController',
['$scope', '$http', 'emUtils', 'queue', 'eq', 'player',
function($scope, $http, emUtils, queue, eq, player) {

    $scope.library = [];
    $scope.player = player;
    $scope.currentSong = null;
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

    $scope.progressBarClicked = function(event) {
        var x = event.offsetX;
        var width;

        if(event.target.id === 'progressBarGutter') {
            width = event.target.offsetWidth;
        } else {
            width = document.getElementById('progressBarGutter').offsetWidth;
        }

        $scope.player.seekToPercent(x / width);
    };

    $scope.loadLibrary();
}]);
