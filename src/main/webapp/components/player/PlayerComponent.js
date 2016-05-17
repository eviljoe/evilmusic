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
import {VolumeControlComponent} from 'components/volume-control/VolumeControlComponent';

// TODO rename this to match the selector (which I think is better)...
//   * directory: player-controls
//   * component: PlayerControlsComponent
//   * template: player-controls.html
export class PlayerComponent {
    constructor(players) {
        this.players = players;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-player-controls',
            templateUrl: 'components/player/player.html',
            directives: [VolumeControlComponent]
        })];
    }
    
    static get parameters() {
        return [[Players]];
    }
}

export default PlayerComponent;
