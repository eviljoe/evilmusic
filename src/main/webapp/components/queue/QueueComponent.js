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

import {Players} from 'services/Players';
import {Queues} from 'services/Queues';

export class QueueComponent {
    constructor(players, queues) {
        this.players = players;
        this.queues = queues;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-queue',
            templateUrl: 'components/queue/queue.html'
        })];
    }
    
    static get parameters() {
        return [[Players], [Queues]];
    }
    
    clear() {
        this.queues.clear();
    }
    
    play(index) {
        this.players.play(index);
    }
    
    remove(index) {
        this.queues.remove(index);
    }
    
    getQueueElements() {
        return this.queues.q ? this.queues.q.elements : null;
    }
    
    isElementPlaying(elem) {
        return elem && elem.playIndex === 0;
    }
}

export default QueueComponent;
