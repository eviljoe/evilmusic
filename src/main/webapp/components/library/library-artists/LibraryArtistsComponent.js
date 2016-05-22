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

import {Libraries} from 'services/Libraries';

export class LibraryArtistsComponent {
    constructor(libraries) {
        this.libraries = libraries;
        this.artistChanged = new EventEmitter();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'library-artists',
            templateUrl: 'components/library/library-artists/library-artists.html',
            inputs: ['artist'],
            outputs: ['artistChanged']
        })];
    }
    
    static get parameters() {
        return [[Libraries]];
    }
    
    // TODO instead of sorting here, try using a pipe to sort this stuff on the view.
    getArtists() {
        let artists = this.libraries.getArtists();
        
        if(artists) {
            artists.sort();
        }
        
        return artists;
    }
    
    artistClicked(artist) {
        this.artistChanged.emit(artist);
    }
}

export default LibraryArtistsComponent;
