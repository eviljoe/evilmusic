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

import {ChangeDetectorRef, Component, EventEmitter} from '@angular/core';

import {Libraries} from 'services/libraries';
import {SortPipe} from 'pipes/sort.pipe';

export class LibraryAlbumsComponent {
    constructor(changeDetector, libraries) {
        this.changeDetector = changeDetector;
        this.libraries = libraries;
        this.albumChanged = new EventEmitter();
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library-albums',
            templateUrl: 'components/library/library-albums/library-albums.html',
            pipes: [SortPipe],
            inputs: ['artist', 'album'],
            outputs: ['albumChanged']
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [Libraries]];
    }
    
    init() {
        this.libraries.libraryChanges.subscribe(() => this._libraryChanged());
    }
    
    _libraryChanged() {
        this.changeDetector.detectChanges();
    }
    
    getAlbums() {
        return this.libraries.getAlbumsForArtist(this.artist);
    }
    
    albumClicked(album) {
        this.albumChanged.emit(album);
    }
    
    getSongCount(album) {
        return this.libraries.getSongCountForAlbum(this.artist, album);
    }
}

export default LibraryAlbumsComponent;
