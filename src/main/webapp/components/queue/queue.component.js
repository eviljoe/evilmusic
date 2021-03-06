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

import {ChangeDetectorRef, Component} from '@angular/core';

import {LoadingOverlayComponent} from 'components/loading-overlay/loading-overlay.component';
import {QueueMenuButtonComponent} from './queue-menu-button/queue-menu-button.component';

import {Players} from 'services/players';
import {Queues} from 'services/queues';

export class QueueComponent {
    constructor(changeDetector, players, queues) {
        this.changeDetector = changeDetector;
        this.players = players;
        this.queues = queues;
    
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-queue',
            templateUrl: 'components/queue/queue.html',
            directives: [
                LoadingOverlayComponent,
                QueueMenuButtonComponent
            ]
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [Players], [Queues]];
    }
    
    init() {
        this._queueLoadingChanged();
        this.queues.loadingChanges.subscribe(() => this._queueLoadingChanged());
        this.queues.playIndexChanges.subscribe(() => this.changeDetector.detectChanges());
    }
    
    _queueLoadingChanged() {
        this.loading = this.queues.loading;
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
        return !!elem && elem.playIndex === 0;
    }
}

export default QueueComponent;
