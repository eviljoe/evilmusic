var emApp = angular.module('EvilMusicApp', []);

emApp.controller('EvilMusicController', function($scope, $http) {
    $scope.addSongAndList = function() {
        $http.get('addsongandlist')
            .success(function (data, status, headers, config) {
                alert(JSON.stringify(data));
            })
            .error(function (data, status, headers, config) {
                alert("Spring JPA test failed.\n\n" + JSON.stringify(data));
            });
    }

    $scope.removeAllSongs = function() {
        $http.delete('songs')
            .success(function (data, status, headers, config) {
                alert('Songs removed!');
            })
            .error(function (data, status, headers, config) {
                alert('Remove all songs failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.rebuildLibrary = function() {
        $http.put('rest/library')
            .success(function (data, status, headers, config) {
                alert('Library rebuilt')
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            })
    }
});

emApp.controller('EMLibraryController', function($scope, $http) {
    $scope.library = null;
    $scope.queue = null;

    $scope.loadLibrary = function() {
        $scope.library = null;

        $http.get('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.library = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get library.\n\n' + JSON.stringify(data));
            });
    }

    $scope.loadQueue = function() {
        $http.get('/rest/queue')
            .success(function (data, status, headers, config) {
                $scope.queue = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get queue.\n\n' + JSON.stringify(data));
            });
    }

    $scope.clearLibrary = function() {
        $http.delete('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Clear library failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.clearQueue = function() {
        $http.delete('/rest/queue')
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Clear queue failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.rebuildLibrary = function() {
        $http.post('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.enqueueLast = function(songID) {
        $http.put('/rest/queue/last', [songID])
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to enqueue last.\n\n' + JSON.stringify(data));
            });
    }

    $scope.removeFromQueue = function(queueIndex) {
        $http.delete('/rest/queue/queueindex/' + queueIndex)
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to remove from queue (' + queueIndex + ')\n\n' + JSON.stringify(data));
            });
    }

    $scope.play = function(queueIndex) {
        $http.put('/rest/queue/playing/queueindex/' + queueIndex)
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to remove from queue (' + queueIndex + ')\n\n' + JSON.stringify(data));
            });
    }

    $scope.loadLibrary();
    $scope.loadQueue();
});