angular.module('EvilMusicApp')
.factory('Queues', ['$resource', function($resource) {
    'use strict';
    
    function emptyRequest(data) {
        return undefined;
    }
    
    return $resource(
        '/rest/queue/:id',
        { id : '@id' },
        {
            addLast : {
                method : 'PUT',
                url : '/rest/queue/:id/last',
                transformRequest : emptyRequest
            },
            remove : {
                method : 'DELETE',
                url : '/rest/queue/:id/queueindex/:qIndex',
                params : { id : '@id', qIndex : '@qIndex'},
                transformRequest : emptyRequest
            },
            clear : {
                method : 'DELETE',
                url : '/rest/queue/:id/elements',
                transformRequest : emptyRequest
            }
        }
    );
}]);
