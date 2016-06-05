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

import {Component} from '@angular/core';

import {Players} from 'services/players';

export class VolumeControlComponent {
    constructor(players) {
        this.players = players;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-volume-control',
            templateUrl: 'components/volume-control/volume-control.html'
        })];
    }
    
    static get parameters() {
        return [[Players]];
    }
    
    volumeChanged() {
        // TODO this feels super weird.  I know setVolume(...) does a server put, but it feels like I am just
        // setting the volume to what it already is.
        this.players.setVolume(this.players.volume);
    }
}

export default VolumeControlComponent;
