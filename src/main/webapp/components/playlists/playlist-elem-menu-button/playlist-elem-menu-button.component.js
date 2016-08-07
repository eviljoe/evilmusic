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
import {CORE_DIRECTIVES} from '@angular/common';
import {DROPDOWN_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

import {Playlists} from 'services/playlists';
import {Queues} from 'services/queues';

export class PlaylistElemMenuButtonComponent {
    constructor(playlists, queues) {
        this.playlists = playlists;
        this.queues = queues;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-playlist-elem-menu-button',
            templateUrl: 'components/playlists/playlist-elem-menu-button/playlist-elem-menu-button.html',
            inputs: ['playlist', 'element'],
            directives: [
                CORE_DIRECTIVES,
                DROPDOWN_DIRECTIVES
            ]
        })];
    }
    
    static get parameters() {
        return [[Playlists], [Queues]];
    }
    
    isForFullPlaylist() {
        return !!this.playlist && !this.element;
    }
    
    addToQueueLast() {
        let ids = this.getSongIDs();
        
        if(ids.length > 0) {
            this.queues.addLast(ids);
        }
    }
    
    getSongIDs() {
        let songs;
        
        if(this.element) {
            songs = this.getSongIDsFromElement();
        } else if(this.playlist) {
            songs = this.getSongIDsFromPlaylist();
        } else {
            songs = [];
        }
        
        return songs;
    }
    
    getSongIDsFromPlaylist() {
        return this.playlist.elements.map((elem) => elem.song.id);
    }
    
    getSongIDsFromElement() {
        return [this.element.song.id];
    }
    
    deletePlaylist() {
        this.playlists.delete(this.playlist.id);
    }
    
    removeFromPlaylist() { // JOE ju
        // JOE todo
    }
}
