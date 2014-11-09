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
    $scope.songInfos;

    $http.get('rest/library')
        .success(function (data, status, headers, config) {
            $scope.songInfos = data;
        })
        .error(function (data, status, headers, config) {
            alert('Could not get library.\n\n' + JSON.stringify(data));
        });

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