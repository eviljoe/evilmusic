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

import {Component, ViewContainerRef} from '@angular/core';
import {HTTP_PROVIDERS} from '@angular/http';
import {MODAL_DIRECTVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';

import {AlertComponent} from 'components/alert/alert.component';
import {MediaComponent} from 'components/media/media.component';
import {PlayerComponent} from 'components/player/player.component';
import {ProgressBarComponent} from 'components/progress-bar/progress-bar.component';
import {QueueComponent} from 'components/queue/queue.component';

import {Alerts} from 'services/alerts';
import {EMUtils} from 'services/emutils';
import {Equalizers} from 'services/equalizers';
import {Libraries} from 'services/libraries';
import {Players} from 'services/players';
import {Playlists} from 'services/playlists';
import {Queues} from 'services/queues';

import {EqualizerCalls} from 'services/server-calls/equalizer-calls';
import {LibraryCalls} from 'services/server-calls/library-calls';
import {PlaylistCalls} from 'services/server-calls/playlist-calls';
import {QueueCalls} from 'services/server-calls/queue-calls';
import {VolumeCalls} from 'services/server-calls/volume-calls';

export class EvilMusicComponent {
    constructor(viewContainerRef) {
        this.viewContainerRef = viewContainerRef; // This is necessary for ng2-bootstrap modals.
    }
    
    static get annotations() {
        return [new Component({
            selector: 'evil-music',
            templateUrl: 'components/evilmusic/evilmusic.html',
            providers: [HTTP_PROVIDERS],
            viewProviders: [
                BS_VIEW_PROVIDERS,
                Alerts,
                EMUtils,
                EqualizerCalls,
                Equalizers,
                Libraries,
                LibraryCalls,
                Players,
                PlaylistCalls,
                Playlists,
                QueueCalls,
                Queues,
                VolumeCalls
            ],
            directives: [
                MODAL_DIRECTVES,
                AlertComponent,
                MediaComponent,
                PlayerComponent,
                ProgressBarComponent,
                QueueComponent
            ]
        })];
    }
    
    static get parameters() {
        return [[ViewContainerRef]];
    }
}

export default EvilMusicComponent;
