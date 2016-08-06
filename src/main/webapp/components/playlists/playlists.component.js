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
import {PlaylistDialogComponent} from 'components/playlists/playlist-dialog/playlist-dialog.component';
import {PlaylistBreadcrumbComponent} from 'components/playlists/playlist-breadcrumb/playlist-breadcrumb.component';
import {PlaylistComponent} from 'components/playlists/playlist/playlist.component';

import {Playlists} from 'services/playlists';
import {SortPipe} from 'pipes/sort.pipe';

export class PlaylistsComponent {
    constructor(changeDetector, playlists) {
        this.changeDetector = changeDetector;
        this.playlists = playlists;
        
        this.playlist = null;
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-playlists',
            templateUrl: 'components/playlists/playlists.html',
            directives: [
                LoadingOverlayComponent,
                PlaylistDialogComponent,
                PlaylistBreadcrumbComponent,
                PlaylistComponent
            ],
            pipes: [SortPipe]
        })];
    }
    
    static get parameters() {
        return [[ChangeDetectorRef], [Playlists]];
    }
    
    init() {
        this.setPlaylistDialogVisible(false);
        this._playlistsLoadingChanged();
        this.playlists.playlistsChanges.subscribe(() => this._playlistsChanged());
        this.playlists.loadingChanges.subscribe(() => this._playlistsLoadingChanged());
    }
    
    _playlistsChanged() {
        this.changeDetector.markForCheck();
    }
    
    _playlistsLoadingChanged() {
        this.loading = this.playlists.loading;
    }
    
    showPlaylists() {
        this.playlist = null;
    }
    
    playlistClicked(playlist) {
        this.playlist = playlist;
    }
    
    createPlaylist() {
        this.setPlaylistDialogVisible(true);
    }
    
    deletePlaylist(playlist) {
        this.playlists.delete(playlist.id);
    }
    
    setPlaylistDialogVisible(visible) {
        this.playlistDialogVisible = visible;
    }
}
