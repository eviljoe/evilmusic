'use strict';

angular.module('EvilMusicApp', [])
.controller('EMLibraryController', function($scope, $http) {
    $scope.library = [];
    $scope.queue = null;
    $scope.player = null;
    $scope.playerProgress = 0;
    $scope.currentSong = null;
    $scope.testOutput = null;
    $scope.webAudioNodes = {};
    $scope.eq = null;
    $scope.volume = 100;

    /** Loads the contents of the library using a REST call. */
    $scope.loadLibrary = function() {
        $http.get('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.library = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get library.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Loads the contents of the queue using a REST call.  A new queue can be loaded, or the same queue can be
     * re-loaded.
     *
     * @param {Boolean} loadNew Whether or not to load a new queue or reload the current queue.
     */
    $scope.loadQueue = function(loadNew) {
        var url = '/rest/queue/';

        if(loadNew) {
            url += 'current';
        } else {
            url += $scope.queue.id;
        }

        $http.get(url)
            .success(function (data, status, headers, config) {
                $scope.queue = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get queue.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
     * be reloaded.
     */
    $scope.clearLibrary = function() {
        $http.delete('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue(true);
            })
            .error(function (data, status, headers, config) {
                alert('Clear library failed.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Makes a REST call to empty out the queue.  After the queue has been emptied on the server, the queue will be
     * reloaded.
     */
    $scope.clearQueue = function() {
        if($scope.queue && $scope.queue.id) {
            $http.delete('/rest/queue/' + $scope.queue.id)
                .success(function (data, status, headers, config) {
                    $scope.loadQueue();
                })
                .error(function (data, status, headers, config) {
                    alert('Clear queue failed.\n\n' + JSON.stringify(data));
                });
        }
    };

    /**
     * Makes a REST call to rebuild the library.  This will delete all songs from the library.  Next, the server's music
     * directories (from the preferences file) will be read to repopulate the library.  After the library has been
     * rebuild, the it and the queue will be reloaded.
     */
    $scope.rebuildLibrary = function() {
        $http.post('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue(true);
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            });
    };

    /**
     * Makes a REST call to add the song with the given ID to the end of the queue.  After the song has been enqueued,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be enqueued because there
     * is no way to know which queue should be altered.
     *
     * @param {Number} songID The ID of the song to be enqueued.
     */
    $scope.enqueueLast = function(songID) {
        if($scope.queue && $scope.queue.id) {
            $http.put('/rest/queue/' + $scope.queue.id + '/last', [songID])
                .success(function (data, status, headers, config) {
                    $scope.loadQueue();
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
    $scope.removeFromQueue = function(queueIndex) {
        if($scope.queue && $scope.queue.id) {
            $http.delete('/rest/queue/' + $scope.queue.id + '/queueindex/' + queueIndex)
                .success(function (data, status, headers, config) {
                    $scope.loadQueue();
                })
                .error(function (data, status, headers, config) {
                    alert('Failed to remove from queue (' + queueIndex + ')\n\n' + JSON.stringify(data));
                });
        }
    };

    /**
     * Loads the nodes/sliders of the equalizer using a REST call.  A new equalizer can be loaded, or the same equalizer
     * can be re-loaded.
     *
     * @param {Boolean} loadNew Whether or not to load a new equalizer or reload the current equalizer.
     */
    $scope.loadEqualizer = function(loadNew) {
        var url = '/rest/eq/';

        if(loadNew) {
            url += 'current';
        } else {
            url += $scope.eq.id;
        }

        $http.get(url)
            .success(function (data, status, headers, config) {
                if(data && data.nodes) {
                    data.nodes.sort(function(a, b) {
                        return a.frequency - b.frequency;
                    });
                }

                $scope.eq = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get equalizer.\n\n' + JSON.stringify(data));
            });
    };

    $scope.play = function(queueIndex) {
        if($scope.player) {
            $scope.player.stop();
        }

        if($scope.queue && $scope.queue.id) {
            var song = $scope.getSongFromQueue(queueIndex);

            $scope.player = AV.Player.fromURL(
                '/rest/queue/' + $scope.queue.id + '/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');

            $scope.player.nodeCreationCallback = $scope.createEQNodes;
            $scope.player.volume = $scope.volume;
            $scope.player.on('progress', function(progress) {
                $scope.playerProgress = progress / song.millis * 100;
                $scope.$apply();
            });
            $scope.player.on('end', function() {
                $scope.player = null;
                $scope.currentSong = null;
            });

            $scope.currentSong = song;
            $scope.player.play();
            $scope.loadQueue();
        }
    };

    $scope.seekToMillis = function(millis) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.player.seek(millis);
        }

        return newMillis;
    };

    $scope.seekToPercent = function(percent) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.seekToMillis(Math.floor($scope.player.duration * percent));
        }

        return newMillis;
    };

    $scope.progressBarClicked = function(event) {
        var x = event.offsetX;
        var width;

        if(event.target.id === 'progressBarGutter') {
            width = event.target.offsetWidth;
        } else {
            width = document.getElementById('progressBarGutter').offsetWidth;
        }

        $scope.seekToPercent(x / width);
    };

    $scope.togglePlayback = function() {
        var toggled = false;

        if($scope.player) {
            $scope.player.togglePlayback();
            toggled = true;
        }

        return toggled;
    };

    /**
     * Creates Web Audio API filter nodes for each equalizer slider.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     *
     * @return {BiquadFilterNode[]} Returns an array of filter nodes.  That should be connected to the audio graph.
     */
    $scope.createEQNodes = function(context) {
        var map = {};
        var eqNodes = [];

        for(var x = 0; x < $scope.eq.nodes.length; x++) {
            var emNode = $scope.eq.nodes[x];
            var eqNode = $scope.createEQNode(context, emNode);

            map[emNode.id] = eqNode;
            eqNodes.push(eqNode);
        }

        $scope.webAudioNodes = map;

        return eqNodes;
    };

    /**
     * Creates a Web Audio API filter node using the given context and settings node.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     * @param {EqualizerNode} emNode Contains the settings that will be used to create the filter node.
     *
     * @return {BiquadFilterNode} The created filter node.
     */
    $scope.createEQNode = function(context, emNode) {
        var node = context.createBiquadFilter();

        node.type = 'peaking';
        node.frequency.value = emNode.frequency;
        node.Q.value = emNode.q;
        node.gain.value = emNode.gain;

        return node;
    };

    /**
     * Updates the gain for each Web Audio API filter node contained within the given EqualizerNode.
     *
     * @param {EqualizerNode} emNode The node that can contain 0 or more Web Audio API filter nodes.
     */
    $scope.updateNodeGain = function(emNode) {
        var webAudioNode = $scope.webAudioNodes[emNode.id];

        if(typeof emNode.gain !== 'number') {
            emNode.gain = parseInt(emNode.gain);
        }

        if(webAudioNode) {
            webAudioNode.gain.value = emNode.gain;
        }
    };

    /**
     * Converts the given Hertz magnitude to a string with units.  The following table describes the string that will be
     * returned:
     *
     *    HERTZ | OUTPUT                 | EXAMPLE
     * ---------+------------------------+--------
     *  < 1,000 | <magnitude> Hz         | 128 Hz
     * >= 1,000 | <magnitude / 1000> kHz | 1.8 kHz
     *
     * @param {Number} hertz The Hertz magnitude.  If null, undefined, or NaN, null will be returned.
     *
     * @return {String} Returns the string representation of the Hertz magnitude.  If the magnitude is null, undefined,
     *         or NaN, null will be returned.
     */
    $scope.hertzToString = function(hertz) {
        var str = null;

        if(hertz !== null && hertz !== undefined && !isNaN(hertz)) {
            str = hertz < 0 ? '-' : '';

            hertz = Math.abs(hertz);

            if(hertz < 1000) {
                str += hertz + ' Hz';
            } else {
                str += hertz / 1000 + ' kHz';
            }
        }

        return str;
    };

    /**
     * Converts the given seconds magnitude to a string with units.  The following table describes the string that will
     * be returned:
     *
     * SECONDS | OUTPUT              | EXAMPLE
     * --------+---------------------+------
     *    < 60 | 0:<seconds>         | 0:05
     *   >= 60 | <minutes>:<seconds> | 3:59
     *
     * In the above format, <seconds> will always be at least two digits.  If it is representing a value less than ten,
     * a leading zero will be added.  For example, "07" will be used to represent seven.
     *
     * @param {Number} seconds The seconds magnitude.  If null, undefined, or NaN, null will be returned.
     *
     * @return {String} Returns the string representation of the seconds magnitude.  If the magnitude is null,
     *         undefined, or NaN, null will be returned.
     */
    $scope.millisecondsToString = function(millis) {
        var str = null;

         if(millis !== null && millis !== undefined &&  !isNaN(millis)) {
            str = millis < 0 ? '-' : '';

            millis = Math.abs(millis);

            if(millis === 0) {
                str = '0:00';
            } else if(millis <= 1000) {
                str += '0:01';
            } else if(millis < 60000) {
                str += millis < 10000 ? '0:0' : '0:';
                str += Math.ceil(millis / 1000);
            } else {
                var minutes = Math.floor(millis / 60000);

                millis = millis % 60000;
                str += minutes;
                str += millis < 10000 ? ':0' : ':';
                str += Math.ceil(millis / 1000);
            }
        }

        return str;
    };

    $scope.getSongFromQueue = function(queueIndex) {
        var song = null;

        if($scope.queue && $scope.queue.elements) {
            for(var x = 0; x < $scope.queue.elements.length; x++) {
                var elem = $scope.queue.elements[x];

                if(elem && elem.queueIndex === queueIndex) {
                    song = elem.song;
                }
            }
        }

        return song;
    };

    $scope.setVolume = function(volume) {
        if($scope.player) {
            $scope.player.volume = volume;
        }

        $scope.volume = volume;

        $http.put('/rest/config/volume/' + volume)
            .error(function (data, status, headers, config) {
                alert('Could not update volume.\n\n' + JSON.stringify(data));
            });
    };

    $scope.loadVolume = function() {
      $http.get('/rest/config/volume')
            .success(function (data, status, headers, config) {
                $scope.volume = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not load volume.\n\n' + JSON.stringify(data));
            });
    };

    $scope.loadVolume();
    $scope.loadEqualizer(true);
    $scope.loadQueue(true);
    $scope.loadLibrary();
});