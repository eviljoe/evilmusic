var emApp = angular.module('EvilMusicApp', []);

emApp.controller('EvilMusicController', function($scope, $http) {
    $scope.addSongAndList = function() {
        $http.get('addsongandlist')
            .success(function (data, status, headers, config) {
                alert(JSON.stringify(data));
            })
            .error(function (data, status, headers, config) {
                alert("Spring JPA test failed.\n\n" + JSON.stringify(data));
            });
    }

    $scope.removeAllSongs = function() {
        $http.delete('songs')
            .success(function (data, status, headers, config) {
                alert('Songs removed!');
            })
            .error(function (data, status, headers, config) {
                alert('Remove all songs failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.rebuildLibrary = function() {
        $http.put('rest/library')
            .success(function (data, status, headers, config) {
                alert('Library rebuilt')
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            })
    }
});

emApp.controller('EMLibraryController', function($scope, $http) {
    $scope.library = [];
    $scope.queue = [];
    $scope.player = null;
    $scope.testOutput = null;
    $scope.nodes = [];

    $scope.loadLibrary = function() {
        $scope.library = null;

        $http.get('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.library = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get library.\n\n' + JSON.stringify(data));
            });
    }

    $scope.loadQueue = function() {
        $http.get('/rest/queue')
            .success(function (data, status, headers, config) {
                $scope.queue = data;
            })
            .error(function (data, status, headers, config) {
                alert('Could not get queue.\n\n' + JSON.stringify(data));
            });
    }

    $scope.clearLibrary = function() {
        $http.delete('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Clear library failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.clearQueue = function() {
        $http.delete('/rest/queue')
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Clear queue failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.rebuildLibrary = function() {
        $http.post('/rest/library')
            .success(function (data, status, headers, config) {
                $scope.loadLibrary();
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Library rebuilding failed.\n\n' + JSON.stringify(data));
            });
    }

    $scope.enqueueLast = function(songID) {
        $http.put('/rest/queue/last', [songID])
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to enqueue last.\n\n' + JSON.stringify(data));
            });
    }

    $scope.removeFromQueue = function(queueIndex) {
        $http.delete('/rest/queue/queueindex/' + queueIndex)
            .success(function (data, status, headers, config) {
                $scope.loadQueue();
            })
            .error(function (data, status, headers, config) {
                alert('Failed to remove from queue (' + queueIndex + ')\n\n' + JSON.stringify(data));
            });
    }

    $scope.play = function(queueIndex) {
        if($scope.player) {
            $scope.togglePlayback();
        } else {
            $scope.player = AV.Player.fromURL('/rest/queue/stream/queueindex/' + queueIndex + '?updatePlayIndex=true');
            $scope.player.nodeCreationCallback = $scope.createEQNodes;

            $scope.player.on('end', function() {
                $scope.player = null;
            });

            $scope.player.play();
            $scope.loadQueue();
        }

        $scope.testOutput = JSON.stringify($scope.player.Player, undefined, 2);
    }

    $scope.seek = function(millis) {
        var newMillis = null;

        if($scope.player) {
            newMillis = $scope.player.seek(millis);
        }

        return newMillis;
    }

    $scope.togglePlayback = function() {
        var toggled = false;

        if($scope.player) {
            $scope.player.togglePlayback();
            toggled = true;
        }

        return toggled;
    }

    $scope.createEMEQNodes = function() {
        var prevNode = null;
        var freqs = [55, 77, 110, 156, 311, 440, 622, 880, 1200, 1800, 3500, 5000, 7000, 10000, 14000, 20000];

        for(var x = 0; x < freqs.length; x++) {
            var eqNode = this.createEMEQNode(freqs[x], 2.5);
            $scope.nodes.push(eqNode);
        }

        return $scope.nodes;
    };

    $scope.createEMEQNode = function(freq, q) {
        var node = new EM.EQNode();

        node.frequency = freq;
        node.q = q;

        return node;
    };

    $scope.createEQNodes = function(context) {
        var prevEQNode = null;
        var eqNodes = [];

        for(var x = 0; x < $scope.nodes.length; x++) {
            var emNode = $scope.nodes[x];
            var eqNode = $scope.createEQNode(context, emNode);

            emNode.nodes.push(eqNode);
            eqNodes.push(eqNode);
            prevNode = eqNode;
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

    $scope.hertzToString = function(hertz) {
        var str = null;

        if(hertz) {
            if(hertz < 1000) {
                str = hertz + " Hz";
            } else if(hertz < 100000) {
                str = hertz / 1000 + " kHz"
            } else {
                str = hertz + " Hz";
            }
        }

        return str;
    };

    $scope.updateNodeGain = function(emNode) {
        emNode.gain = parseInt(emNode.gain);

        for(var x = 0; x < emNode.nodes.length; x++) {
            var eqNode = emNode.nodes[x];
            eqNode.gain.value = emNode.gain;
        }
    };

    $scope.loadLibrary();
    $scope.loadQueue();
    $scope.createEMEQNodes();
});
