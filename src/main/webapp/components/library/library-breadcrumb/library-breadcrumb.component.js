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

export class LibraryBreadcrumbComponent {
    constructor() {
        this.artistCleared = new EventEmitter();
        this.albumCleared = new EventEmitter();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library-breadcrumb',
            templateUrl: 'components/library/library-breadcrumb/library-breadcrumb.html',
            inputs: ['artist', 'album'],
            outputs: ['artistCleared', 'albumCleared']
        })];
    }
    
    static get parameters() {
        return [];
    }
    
    hasArtist() {
        return !!this.artist;
    }
    
    hasAlbum() {
        return !!this.album;
    }
    
    isLibraryActive() {
        return !this.hasArtist() && !this.hasAlbum();
    }
    
    isArtistActive() {
        return this.hasArtist() && !this.hasAlbum();
    }
    
    isAlbumActive() {
        return this.hasArtist() && this.hasAlbum();
    }
    
    libraryClicked() {
        this.artistCleared.emit();
    }
    
    artistClicked() {
        this.albumCleared.emit();
    }
}
