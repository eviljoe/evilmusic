'use strict';

angular.module('EvilMusicApp')
.factory('eq', ['$http', function($http){

    var that = this;

    that.webAudioNodes = {};
    that.eq = null;

    /**
     * Loads the nodes/sliders of the equalizer using a REST call.  A new equalizer can be loaded, or the same equalizer
     * can be re-loaded.
     *
     * @param {Boolean} loadNew Whether or not to load a new equalizer or reload the current equalizer.
     */
    that.load = function(loadNew) {
        var url = '/rest/eq/';

        if(loadNew) {
            url += 'current';
        } else {
            url += that.eq.id;
        }

        $http.get(url)
        .success(function (data, status, headers, config) {
            if(data && data.nodes) {
                data.nodes.sort(function(a, b) {
                    return a.frequency - b.frequency;
                });
            }

            that.eq = data;
        })
        .error(function (data, status, headers, config) {
            alert('Could not get equalizer.\n\n' + JSON.stringify(data));
        });
    };

    /**
     * Creates Web Audio API filter nodes for each equalizer slider.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     *
     * @return {BiquadFilterNode[]} Returns an array of filter nodes.  That should be connected to the audio graph.
     */
    that.createEQNodes = function(context) {
        var map = {};
        var webAudioNodes = [];

        for(var x = 0; x < that.eq.nodes.length; x++) {
            var emNode = that.eq.nodes[x];
            var webAudioNode = that.createEQNode(context, emNode);

            map[emNode.id] = webAudioNode;
            webAudioNodes.push(webAudioNode);
        }

        that.webAudioNodes = map;

        return webAudioNodes;
    };

    /**
     * Creates a Web Audio API filter node using the given context and settings node.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     * @param {EqualizerNode} emNode Contains the settings that will be used to create the filter node.
     *
     * @return {BiquadFilterNode} The created filter node.
     */
    that.createEQNode = function(context, emNode) {
        var node = null;

        if(context && emNode) {
            node = context.createBiquadFilter();
            node.type = 'peaking';
            node.frequency.value = emNode.frequency;
            node.Q.value = emNode.q;
            node.gain.value = emNode.gain;
        }

        return node;
    };

    /**
     * Updates the gain for each Web Audio API filter node contained within the given EqualizerNode.
     *
     * @param {EqualizerNode} emNode The node that can contain 0 or more Web Audio API filter nodes.
     */
    that.updateNodeGain = function(emNode) {
        var updated = false;

        if(emNode) {
            var webAudioNode = that.webAudioNodes[emNode.id];

            if(typeof emNode.gain !== 'number') {
                emNode.gain = parseInt(emNode.gain);
            }

            if(webAudioNode) {
                webAudioNode.gain.value = emNode.gain;
                updated = true;
            }
        }

        return updated;
    };

    that.load(true);

    return that;
}]);