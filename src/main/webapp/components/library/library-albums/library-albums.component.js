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

import {Component, EventEmitter} from '@angular/core';

import {Libraries} from 'services/libraries';

export class LibraryAlbumsComponent {
    constructor(libraries) {
        this.libraries = libraries;
        this.albumChanged = new EventEmitter();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'library-albums',
            templateUrl: 'components/library/library-albums/library-albums.html',
            inputs: ['artist', 'album'],
            outputs: ['albumChanged']
        })];
    }
    
    static get parameters() {
        return [[Libraries]];
    }
    
    getAlbums() {
        let albums = this.libraries.getAlbumsForArtist(this.artist);
        
        if(albums) {
            albums.sort();
        }
        
        return albums;
    }
    
    albumClicked(album) {
        this.albumChanged.emit(album);
    }
}

export default LibraryAlbumsComponent;
