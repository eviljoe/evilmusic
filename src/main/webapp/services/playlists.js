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

import {EventEmitter, Injectable} from '@angular/core';

import {Alerts} from './alerts';
import {PlaylistCalls} from './server-calls/playlist-calls';

const PLAYLIST_CHANGE_TYPES = {
    LOAD: 'load',
    ADD: 'add',
    REMOVE: 'remove',
    UPDATE: 'update'
};

export class Playlists {
    constructor(alerts, playlistCalls) {
        this.alerts = alerts;
        this.playlistCalls = playlistCalls;
        
        this.loadingChanges = new EventEmitter();
        this.playlistsChanges = new EventEmitter();
        
        this.init();
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Alerts], [PlaylistCalls]];
    }
    
    init() {
        this.load();
    }
    
    load() {
        this.loading = true;
        
        this.playlistCalls.getAll().subscribe(
            (playlists) => this._loaded(playlists),
            (err) => this.alerts.error('Could not load playlists.', err)
        );
    }
    
    _loaded(playlists) {
        this.playlists = playlists;
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.LOAD);
        this.loading = false;
    }
    
    create(name) {
        let ob;
        
        this.loading = true;
        ob = this.playlistCalls.create(name);
        
        ob.subscribe(
            (playlist) => this._created(playlist),
            (err) => this.alerts.error('Could not create playlist', err)
        );
        
        return ob;
    }
    
    _created(playlist) {
        this.playlists.push(playlist);
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.ADD, playlist);
        this.loading = false;
    }
    
    delete(id) {
        this.loading = true;
        
        this.playlistCalls.delete(id).subscribe(
            () => this._deleted(id),
            (err) => this.alerts.error('Could not delete playlist', err)
        );
    }
    
    _deleted(id) {
        let plIndex = this.playlists.findIndex((pl) => pl.id === id);
        
        if(plIndex > -1) {
            this.playlists.splice(plIndex, 1);
        }
        
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.REMOVE);
        this.loading = false;
    }
    
    setPlaylistName(id, name) {
        let ob;
        
        this.loading = true;
        
        ob = this.playlistCalls.setName(id, name);
        ob.subscribe(
            (playlist) => this._playlistNameSet(playlist),
            (err) => this.alerts.error('Could not set playlist name', err)
        );
        
        return ob;
    }
    
    _playlistNameSet(playlist) {
        this._replacePlaylist(playlist); // JOE ju
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.UPDATE, playlist);
        this.loading = false;
    }
    
    _firePlaylistsChanged(type, playlist) {
        this.playlistsChanges.emit({
            type,
            playlist,
            playlists: this.playlists
        });
    }
    
    addSongsLast(playlist, ... songIDs) {
        this.loading = true;
        
        this.playlistCalls.addLast(playlist.id, songIDs).subscribe(
            (pl) => this._addedSongsLast(pl),
            (err) => this.alerts.error('Could not add songs to the playlist', err)
        );
    }
    
    _addedSongsLast(playlist) {
        this._replacePlaylist(playlist);
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.UPDATE, playlist);
        this.loading = false;
    }
    
    _replacePlaylist(playlist) {
        let plIndex = this.playlists.findIndex((pl) => pl.id === playlist.id);

        if(plIndex > -1) {
            this.playlists[plIndex] = playlist;
        }
    }
    
    get loading() {
        return this._loading;
    }
    
    set loading(loading) {
        let oldLoading = this._loading;
        
        this._loading = loading;
        this.loadingChanges.emit({
            old: oldLoading,
            new: this._loading
        });
    }
}
