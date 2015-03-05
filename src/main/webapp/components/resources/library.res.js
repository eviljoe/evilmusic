angular.module('EvilMusicApp')
.factory('Libraries', ['$resource', function($resource) {
    'use strict';
    
    return $resource(
        '/rest/library',
        null,
        {
            rebuild : { method : 'POST' }
        }
    );
}]);
