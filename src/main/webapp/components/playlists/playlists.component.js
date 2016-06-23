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

import {LoadingOverlayComponent} from 'components/loading-overlay/loading-overlay.component';

import {Playlists} from 'services/playlists';
import {SortPipe} from 'pipes/sort.pipe';

export class PlaylistsComponent {
    constructor(changeDetector, playlists) {
        this.changeDetector = changeDetector;
        this.playlists = playlists;
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-playlists',
            templateUrl: 'components/playlists/playlists.html',
            directives: [LoadingOverlayComponent],
            pipes: [SortPipe]
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [Playlists]];
    }
    
    init() {
        this.playlists.playlistsChanges.subscribe(() => this._playlistsChanged());
        this.playlists.loadingChanges.subscribe(() => this._playlistsLoadingChanged());
    }
    
    _playlistsChanged() {
        this.changeDetector.markForCheck();
    }
    
    _playlistsLoadingChanged() {
        this.loading = this.playlists.loading;
    }
    
    playlistClicked(playlist) {
        this.playlist = playlist;
    }
    
    createPlaylist() {
        this.playlists.create(`playlist ${new Date()}`);
    }
    
    deletePlaylist(playlist) {
        this.playlists.delete(playlist.id);
    }
}
