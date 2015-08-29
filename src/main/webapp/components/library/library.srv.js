/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2015 Joe Falascino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

angular.module('EvilMusicApp')
.factory('library', ['$http', 'queues', 'Library', function($http, queues, Library) {

    'use strict';

    var that = this;
    that.library = null;

    /** Loads the contents of the library using a REST call. */
    that.load = function() {
        that.library = Library.get();
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
                    queues.load(true);
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
                    queues.load(true);
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
