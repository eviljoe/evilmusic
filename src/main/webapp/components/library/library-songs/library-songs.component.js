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

import {ChangeDetectorRef, Component} from '@angular/core';
import _ from 'lodash';

import {LibrarySongMenuButtonComponent} from '../library-song-menu-button/library-song-menu-button.component';

import {EMUtils} from 'services/emutils';
import {Libraries} from 'services/libraries';
import {Players} from 'services/players';
import {Queues} from 'services/queues';

import {HertzPipe} from 'pipes/hertz.pipe';
import {MinutesPipe} from 'pipes/minutes.pipe';
import {SortPipe} from 'pipes/sort.pipe';

export class LibrarySongsComponent {
    constructor(changeDetector, emUtils, libraries, players, queues) {
        this.changeDetector = changeDetector;
        this.emUtils = emUtils;
        this.libraries = libraries;
        this.players = players;
        this.queues = queues;
        
        this.enqueuedSongIDs = new Set();
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library-songs',
            templateUrl: 'components/library/library-songs/library-songs.html',
            inputs: ['album', 'artist'],
            directives: [LibrarySongMenuButtonComponent],
            pipes: [HertzPipe, MinutesPipe, SortPipe]
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [EMUtils], [Libraries], [Players], [Queues]];
    }
    
    init() {
        this.libraries.libraryChanges.subscribe(() => this._libraryChanged());
        this.players.currentSongChanges.subscribe(() => this._currentSongChanged());
        this.queues.queueChanges.subscribe(() => this._queueChanged());
    }
    
    _libraryChanged() {
        this.changeDetector.detectChanges();
    }
    
    _currentSongChanged() {
        this.changeDetector.detectChanges();
    }
    
    _queueChanged() {
        this.enqueuedSongIDs.clear();
        _.forEach(this.queues.q.elements, (elem) => this.enqueuedSongIDs.add(elem.song.id));
    }
    
    getSongs() {
        return this.libraries.getSongsForAlbum(this.artist, this.album);
    }
    
    createSongSet(song) {
        return new Set([song]);
    }
    
    isPlaying(song) {
        let playingSong = this.players.currentSong;

        return !!playingSong && playingSong.id === song.id;
    }
    
    isInQueue(song) {
        return this.enqueuedSongIDs.has(song.id);
    }
}
