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

import {Component, ChangeDetectorRef} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {DROPDOWN_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

import {Playlists} from 'services/playlists';
import {Queues} from 'services/queues';

export class LibrarySongMenuButtonComponent {
    constructor(changeDetector, playlists, queues) {
        this.changeDetector = changeDetector;
        this.playlists = playlists;
        this.queues = queues;
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library-song-menu-button',
            templateUrl: 'components/library/library-song-menu-button/library-song-menu-button.html',
            inputs: ['songs'],
            directives: [
                CORE_DIRECTIVES,
                DROPDOWN_DIRECTIVES
            ]
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [Playlists], [Queues]];
    }
    
    init() {
        this.playlists.playlistsChanges.subscribe(() => this._playlistsChanged());
    }
    
    _playlistsChanged() {
        this.changeDetector.detectChanges();
    }
    
    getPlaylists() {
        return this.playlists.playlists;
    }
    
    addToQueueLast() {
        this.queues.addLast(Array.from(this.songs)
            .sort((songA, songB) => songA.trackNumber - songB.trackNumber)
            .map((song) => song.id));
    }
    
    addToPlaylistLast(playlist) { // JOE ju
        this.playlists.addSongsLast(playlist, Array.from(this.songs)
            .sort((a, b) => a.trackNumber - b.trackNumber)
            .map((song) => song.id));
    }
}
