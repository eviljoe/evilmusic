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

import {Component, EventEmitter} from '@angular/core';

export class PlaylistBreadcrumbComponent {
    constructor() {
        this.playlistCleared = new EventEmitter();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-playlist-breadcrumb',
            templateUrl: 'components/playlists/playlist-breadcrumb/playlist-breadcrumb.html',
            inputs: ['playlist'],
            outputs: ['playlistCleared']
        })];
    }
    
    static get parameters() {
        return [];
    }
    
    hasPlaylist() {
        return !!this.playlist;
    }
    
    isSingleActive() {
        return !!this.hasPlaylist();
    }
    
    isMultiActive() {
        return !this.hasPlaylist();
    }
    
    multiClicked() {
        this.playlistCleared.emit();
    }
    
    getPlaylistName() {
        return this.playlist ? this.playlist.name : null;
    }
}
