angular.module('EvilMusicApp')
.factory('Libraries', ['$resource', function($resource) {
    return $resource(
        '/rest/library',
        null,
        {
            rebuild : { method : 'POST' }
        }
    );
}]);
