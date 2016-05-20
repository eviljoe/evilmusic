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

import {Component} from '@angular/core';
import {HertzPipe} from 'pipes/HertzPipe';
import {Equalizers} from 'services/Equalizers';

export class EQComponent {
    constructor(equalizers) {
        this.equalizers = equalizers;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-eq',
            templateUrl: 'components/eq/eq.html',
            pipes: [HertzPipe]
        })];
    }
    
    static get parameters() {
        return [Equalizers];
    }
    
    getEQNodes() {
        let nodes = null;
        
        if(this.equalizers && this.equalizers.eq && this.equalizers.eq.nodes) {
            nodes = this.equalizers.eq.nodes;
            nodes.sort((a, b) => a.frequency - b.frequency);
        }
        
        return nodes;
    }
    
    nodeChanged(node) {
        this.equalizers.updateNodeGain(node);
    }
}

export default EQComponent;
