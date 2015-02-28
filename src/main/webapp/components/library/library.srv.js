angular.module('EvilMusicApp')
.factory('library', ['$http', 'queue', 'Libraries', function($http, queue, Libraries) {

    'use strict';

    var that = this;
    that.library = null;

    /** Loads the contents of the library using a REST call. */
    that.load = function() {
        that.library = Libraries.get();
        that.library.$promise.catch(function(data) {
            alert('Could not get library.\n\n' + JSON.stringify(data));
        });
    };

    /**
     * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
     * be reloaded.
     */
    that.clear = function() {
        that.library.$promise.then(function() {
            that.library.$delete().then(
                function() {
                    that.load();
                    queue.load(true);
                },
                function(data) {
                    alert('Clear library failed.\n\n' + JSON.stringify(data));
                }
            );
        });
    };

    /**
     * Makes a REST call to rebuild the library.  This will delete all songs from the library.  Next, the server's music
     * directories (from the preferences file) will be read to repopulate the library.  After the library has been
     * rebuild, the it and the queue will be reloaded.
     */
    that.rebuild = function() {
        that.library.$promise.then(function() {
            that.library.$rebuild().then(
                function() {
                    that.load();
                    queue.load(true);
                },
                function(data) {
                    alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
                }
            );
        });
    };

    that.load();

    return that;
}]);
