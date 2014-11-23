'use strict';

var emApp = angular.module('EvilMusicApp', []);

emApp.controller('EMLibraryController', function($scope, $http) {
    $scope.library = [];
    $scope.queue = null;
    $scope.player = null;
    $scope.testOutput = null;
    $scope.nodes = [];
    $scope.eq = null;

    $scope.loadLibrary = function() {
        $http.get('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.library = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get library.\n\n' + JSON.stringify(data));
            });
    };

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
                    for(var x = 0; x < data.nodes.length; x++) {
                        data.nodes[x].webAuidioNodes = [];
                    }

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
            $scope.togglePlayback();
        } else if($scope.queue && $scope.queue.id) {
            $scope.player = AV.Player.fromURL(
                '/rest/queue/' + $scope.queue.id +
                '/stream/queueindex/' + queueIndex +
                '?updatePlayIndex=true');
            $scope.player.nodeCreationCallback = $scope.createEQNodes;
            $scope.player.on('end', function() {
                $scope.player = null;
            });

            $scope.player.play();
            $scope.loadQueue();
        }
    };

    $scope.seek = function(millis) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.player.seek(millis);
        }

        return newMillis;
    };

    $scope.togglePlayback = function() {
        var toggled = false;

        if($scope.player) {
            $scope.player.togglePlayback();
            toggled = true;
        }

        return toggled;
    };

    $scope.createEQNodes = function(context) {
        var eqNodes = [];

        for(var x = 0; x < $scope.eq.nodes.length; x++) {
            var emNode = $scope.eq.nodes[x];
            var eqNode = $scope.createEQNode(context, emNode);

            emNode.webAuidioNodes.push(eqNode);
            eqNodes.push(eqNode);
        }

        return eqNodes;
    };

    $scope.createEQNode = function(context, emNode) {
        var node = context.createBiquadFilter();

        node.type = 'peaking';
        node.frequency.value = emNode.frequency;
        node.Q.value = emNode.q;
        node.gain.value = emNode.gain;

        return node;
    };

    $scope.updateNodeGain = function(emNode) {
        if(typeof emNode.gain !== 'number') {
            emNode.gain = parseInt(emNode.gain);
        }

        for(var x = 0; x < emNode.webAuidioNodes.length; x++) {
            var eqNode = emNode.webAuidioNodes[x];
            eqNode.gain.value = emNode.gain;
        }
    };

    $scope.hertzToString = function(hertz) {
        var str = null;

        if(hertz) {
            if(hertz < 1000) {
                str = hertz + ' Hz';
            } else if(hertz < 100000) {
                str = hertz / 1000 + ' kHz';
            } else {
                str = hertz + ' Hz';
            }
        }

        return str;
    };

    $scope.loadEqualizer(true);
    $scope.loadQueue(true);
    $scope.loadLibrary();
});
