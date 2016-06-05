/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2016 Joe Falascino
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

import {EventEmitter, Injectable} from '@angular/core';
import _ from 'lodash';

import {Alerts} from './alerts';
import {EqualizerCalls} from './server-calls/equalizer-calls';

export class Equalizers {
    constructor(alerts, equalizerCalls) {
        this.alerts = alerts;
        this.equalizerCalls = equalizerCalls;
        this.webAudioNodes = {};
        this.eq = null;
        
        this.loadingChanges = new EventEmitter();
        
        this.init();
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Alerts], [EqualizerCalls]];
    }
    
    init() {
        Equalizers.instance = this;
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
        
        this.loading = true;
        
        this.equalizerCalls.get(id).subscribe(
            (eq) => this._loaded(eq),
            (err) => this.alerts.error('Could not get equalizer.', err)
        );
    }
    
    _loaded(eq) {
        this.eq = eq;
        this.loading = false;
    }
    
    /**
     * Creates Web Audio API filter nodes for each equalizer slider.
     *
     * @param {AudioContext} context Will be used to create each filter node.
     *
     * @return {BiquadFilterNode[]} Returns an array of filter nodes.  That should be connected to the audio graph.
     */
    createEQNodes(context) {
        let instance = Equalizers.instance;
        let map = {};
        let webAudioNodes = [];

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
        let ob = this.equalizerCalls.save(this.eq.id, this.eq);
        
        this.loading = true;
        
        ob.subscribe(
            (eq) => this._saved(eq),
            (err) => this.alerts.error('An error occurred, and the equalizer could not be saved.', err)
        );
        
        return ob;
    }
    
    _saved(eq) {
        this.eq = eq;
        this.loading = false;
    }
    
    reset() {
        _.forEach(this.eq.nodes, (node) => {
            _.set(node, 'gain', 0);
            this.updateNodeGain(node);
        });
    }
    
    get loading() {
        return this._loading;
    }
    
    set loading(loading) {
        let oldLoading = this._loading;
        
        this._loading = loading;
        this.loadingChanges.emit({
            old: oldLoading,
            new: this._loading
        });
    }
}

export default Equalizers;
