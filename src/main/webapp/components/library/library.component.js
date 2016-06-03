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

import {LibraryAlbumsComponent} from './library-albums/library-albums.component';
import {LibraryArtistsComponent} from './library-artists/library-artists.component';
import {LibraryBreadcrumbComponent} from './library-breadcrumb/library-breadcrumb.component';
import {LibrarySongsComponent} from './library-songs/library-songs.component';
import {LoadingOverlayComponent} from 'components/loading-overlay/loading-overlay.component';

import {Libraries} from 'services/libraries';

export class LibraryComponent {
    constructor(libraries) {
        this.libraries = libraries;
        this.artist = null;
        this.album = null;
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library',
            templateUrl: 'components/library/library.html',
            directives: [
                LibraryAlbumsComponent,
                LibraryArtistsComponent,
                LibraryBreadcrumbComponent,
                LibrarySongsComponent,
                LoadingOverlayComponent
            ]
        })];
    }
    
    static get parameters() {
        return [[Libraries]];
    }
    
    init() {
        this.libraries.libraryChanges.subscribe(() => this._libraryChanged());
    }
    
    _libraryChanged() {
        if(this.isArtistInLibrary()) {
            if(!this.isAlbumInLibrary()) {
                this.backToAlbums();
            }
        } else {
            this.backToArtists();
        }
    }
    
    isArtistInLibrary() {
        return !!this.artist && this.libraries.getArtists().has(this.artist);
    }
    
    isAlbumInLibrary() {
        return !!this.artist && !!this.album && this.libraries.getAlbumsForArtist(this.artist).has(this.album);
    }
    
    backToArtists() {
        this.artist = null;
        this.album = null;
    }
    
    backToAlbums() {
        this.album = null;
    }
    
    artistChanged(newArtist) {
        this.artist = newArtist;
    }
    
    albumChanged(newAlbum) {
        this.album = newAlbum;
    }
}

export default LibraryComponent;
