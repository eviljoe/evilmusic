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
import {HTTP_PROVIDERS} from '@angular/http';

import {LibraryComponent} from 'components/library/LibraryComponent';
import {PlayerComponent} from 'components/player/PlayerComponent';
import {ProgressBarComponent} from 'components/progress-bar/ProgressBarComponent';
import {QueueComponent} from 'components/queue/QueueComponent';

import {EMUtils} from 'services/EMUtils';
import {EqualizerCalls} from 'services/server-calls/EqualizerCalls';
import {Equalizers} from 'services/Equalizers';
import {Libraries} from 'services/Libraries';
import {LibraryCalls} from 'services/server-calls/LibraryCalls';
import {Players} from 'services/Players';
import {QueueCalls} from 'services/server-calls/QueueCalls';
import {Queues} from 'services/Queues';
import {VolumeCalls} from 'services/server-calls/VolumeCalls';

export class EvilMusicComponent {
    static get annotations() {
        return [new Component({
            selector: 'evil-music',
            templateUrl: 'components/evilmusic/evilmusic.html',
            providers: [HTTP_PROVIDERS],
            viewProviders: [
                EMUtils,
                EqualizerCalls,
                Equalizers,
                Libraries,
                LibraryCalls,
                Players,
                QueueCalls,
                Queues,
                VolumeCalls
            ],
            directives: [
                LibraryComponent,
                PlayerComponent,
                ProgressBarComponent,
                QueueComponent
            ]
        })];
    }
}

export default EvilMusicComponent;
