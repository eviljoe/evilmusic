var emApp = angular.module('EvilMusicApp', []);

emApp.controller('EvilMusicController', function($scope, $http) {
    $scope.angularTestText = 'Hello, World (from angular)!';

    $http.get('songinfo')
        .success(function (data, status, headers, config) {
            $scope.angularTestText = JSON.stringify(data);
        })
        .error(function (data, status, headers, config) {
            $scope.angularTestText = 'AngularJS error: ' + JSON.stringify(data);
        });
});