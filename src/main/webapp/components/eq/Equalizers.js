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

import _ from 'lodash';

export default class Equalizers {
    constructor(alerts, Equalizer) {
        Equalizers.instance = this;
        
        this.alerts = alerts;
        this.Equalizer = Equalizer;
        this.webAudioNodes = {};
        this.eq = null;
        
        this.init();
    }
    
    static get $inject() {
        return ['alerts', 'Equalizer'];
    }
    
    static get injectID() {
        return 'equalizers';
    }
    
    static getInstance() {
        return Equalizers.instance;
    }
    
    init() {
        this.load(true);
    }
    
    /**
     * Loads the nodes/sliders of the equalizer using a REST call.  A new equalizer can be loaded, or the same equalizer
     * can be re-loaded.
     *
     * @param {boolean} loadNew Whether or not to load a new equalizer or reload the current equalizer.
     */
    load(loadNew) {
        let id = loadNew ? 'default' : this.eq.id;

        this.eq = this.Equalizer.get({id});
        this.eq.$promise.catch((data) => {
            this.alerts.error('Could not get equalizer.', data);
        });
    }
    
    /**
     * Creates Web Audio API filter nodes for each equalizer slider.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     *
     * @return {BiquadFilterNode[]} Returns an array of filter nodes.  That should be connected to the audio graph.
     */
    createEQNodes(context) {
        let map = {};
        let webAudioNodes = [];
        let instance = Equalizers.getInstance();

        for(let x = 0; x < instance.eq.nodes.length; x++) {
            let emNode = instance.eq.nodes[x];
            let webAudioNode = instance.createEQNode(context, emNode);

            map[emNode.id] = webAudioNode;
            webAudioNodes.push(webAudioNode);
        }

        instance.webAudioNodes = map;

        return webAudioNodes;
    }

    /**
     * Creates a Web Audio API filter node using the given context and settings node.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     * @param {EqualizerNode} emNode Contains the settings that will be used to create the filter node.
     *
     * @return {BiquadFilterNode} The created filter node.
     */
    createEQNode(context, emNode) {
        let node = null;

        if(context && emNode) {
            node = context.createBiquadFilter();
            node.type = 'peaking';
            node.frequency.value = emNode.frequency;
            node.Q.value = emNode.q;
            node.gain.value = emNode.gain;
        }

        return node;
    }

    /**
     * Updates the gain for each Web Audio API filter node contained within the given EqualizerNode.
     *
     * @param {EqualizerNode} emNode The node that can contain 0 or more Web Audio API filter nodes.
     */
    updateNodeGain(emNode) {
        let updated = false;

        if(emNode) {
            let webAudioNode = this.webAudioNodes[emNode.id];

            if(typeof emNode.gain !== 'number') {
                emNode.gain = parseInt(emNode.gain, 10);
            }

            if(webAudioNode) {
                webAudioNode.gain.value = emNode.gain;
                updated = true;
            }
        }

        return updated;
    }
    
    save() {
        let promise = this.eq.$update();
        
        promise.catch((data) => {
            this.alerts.error('An error occurred, and the equalizer could not be saved.', data);
        });
        
        return promise;
    }
    
    reset() {
        _.forEach(this.eq.nodes, (node) => {
            _.set(node, 'gain', 0);
            this.updateNodeGain(node);
        });
    }
}
