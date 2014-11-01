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
});