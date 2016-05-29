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

import {HertzPipe} from 'pipes/hertz.pipe';
import {MinutesPipe} from 'pipes/minutes.pipe';

import {EMUtils} from 'services/emutils';
import {Libraries} from 'services/libraries';
import {Queues} from 'services/queues';

export class LibraryComponent {
    constructor(emUtils, libraries, queues) {
        this.libraries = libraries;
        this.queues = queues;
        this.emUtils = emUtils;
        
        this.artist = null;
        this.album = null;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library',
            templateUrl: 'components/library/library.html',
            directives: [LibraryAlbumsComponent, LibraryArtistsComponent, LibraryBreadcrumbComponent],
            pipes: [HertzPipe, MinutesPipe]
        })];
    }
    
    static get parameters() {
        return [[EMUtils], [Libraries], [Queues]];
    }
    
    backToArtists() {
        this.artist = null;
        this.album = null;
    }
    
    backToAlbums() {
        this.album = null;
    }
    
    addLast(songID) {
        this.queues.addLast(songID);
    }
    
    // TODO instead of sorting here, try using a pipe to sort this stuff on the view.
    getSongs() {
        let songs = this.libraries.getSongsForAlbum(this.artist, this.album);
        
        if(songs) {
            songs.sort((a, b) => a.trackNumber - b.trackNumber);
        }
        
        return songs;
    }
    
    artistChanged(newArtist) {
        this.artist = newArtist;
    }
    
    albumChanged(newAlbum) {
        this.album = newAlbum;
    }
}

export default LibraryComponent;
