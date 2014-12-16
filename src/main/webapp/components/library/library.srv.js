'use strict';

angular.module('EvilMusicApp')
.factory('library', ['$http', 'queue', function($http, queue) {

    var that = this;
    that.library = null;

    /** Loads the contents of the library using a REST call. */
    that.load = function() {
        $http.get('/rest/library')
        .success(function (data, status, headers, config) {
            that.library = data;
        })
        .error(function (data, status, headers, config) {
            alert('Could not get library.\n\n' + JSON.stringify(data));
        });
    };

    /**
     * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
     * be reloaded.
     */
    that.clear = function() {
        $http.delete('/rest/library')
        .success(function (data, status, headers, config) {
            that.load();
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
    that.rebuild = function() {
        $http.post('/rest/library')
        .success(function (data, status, headers, config) {
            that.load();
            queue.load(true);
        })
        .error(function (data, status, headers, config) {
            alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
        });
    };

    that.load();

    return that;
}]);