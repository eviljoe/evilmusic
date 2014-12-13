'use strict';

angular.module('EvilMusicApp')
.factory('queue', ['$http', 'emUtils', function($http, emUtils) {
    var that = this;

    that.q = null;

    /**
     * Loads the contents of the queue using a REST call.  A new queue can be loaded, or the same queue can be
     * re-loaded.
     *
     * @param {Boolean} loadNew Whether or not to load a new queue or reload the current queue.
     */
    that.load = function(loadNew) {
        var url = '/rest/queue/';

        if(loadNew) {
            url += 'current';
        } else {
            url += that.q.id;
        }

        $http.get(url)
        .success(function (data, status, headers, config) {
            that.q = data;
        })
        .error(function (data, status, headers, config) {
            alert('Could not get queue.\n\n' + JSON.stringify(data));
        });
    };

    /**
     * Makes a REST call to add the song with the given ID to the end of the queue.  After the song has been enqueued,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be enqueued because there
     * is no way to know which queue should be altered.
     *
     * @param {Number} songID The ID of the song to be enqueued.
     */
    that.addLast = function(songID) {
        if(that.q && that.q.id) {
            $http.put('/rest/queue/' + that.q.id + '/last', [songID])
            .success(function (data, status, headers, config) {
                that.load(false);
            })
            .error(function (data, status, headers, config) {
                alert('Failed to enqueue last.\n\n' + JSON.stringify(data));
            });
        }
    };

    /**
     * Makes a REST call to remove the song at the given queue index from the queue.  After the queue has been updated,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be removed because there
     * is no way to know which queue should be altered.
     *
     * @param {Number} queueIndex The index within the queue that should be removed.
     */
    that.remove = function(queueIndex) {
        if(that.q && that.q.id) {
            $http.delete('/rest/queue/' + that.q.id + '/queueindex/' + queueIndex)
            .success(function (data, status, headers, config) {
                that.load();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to remove from queue (' + queueIndex + ')\n\n' + JSON.stringify(data));
            });
        }
    };

    /**
     * Makes a REST call to empty out the queue.  After the queue has been emptied on the server, the queue will be
     * reloaded.
     */
    that.clear = function() {
        if(that.q && that.q.id) {
            $http.delete('/rest/queue/' + that.q.id + '/elements')
            .success(function (data, status, headers, config) {
                that.load();
            })
            .error(function (data, status, headers, config) {
                alert('Clear queue failed.\n\n' + JSON.stringify(data));
            });
        }
    };

    /**
     * Returns a song from the queue.
     *
     * @param  {Number} queueIndex The queue index of the song to be found.  If null, undefined, or NaN, null will be
     *         returned.
     *
     * @return {Song} If a song can be found at the given queue index, it will be returned.  If a song cannot be found,
     *         null will be returned.
     */
    that.getSong = function(queueIndex) {
        var song = null;

        if(emUtils.isNumber(queueIndex) && that.q && that.q.elements) {
            for(var x = 0; x < that.q.elements.length; x++) {
                var elem = that.q.elements[x];

                if(elem && elem.queueIndex === queueIndex) {
                    song = elem.song;
                }
            }
        }

        return song;
    };

    that.load(true);

    return that;
}]);