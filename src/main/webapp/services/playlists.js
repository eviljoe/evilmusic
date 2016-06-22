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
    REMOVE: 'update',
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
    
    static get annotations() { // JOE ju
        return [new Injectable()];
    }
    
    static get parameters() { // JOE ju
        return [[Alerts], [PlaylistCalls]];
    }
    
    init() { // JOE ju
        this.load();
    }
    
    load() { // JOE ju
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
    
    create(name) { // JOE ju
        this.loading = true;
        
        this.playlistCalls.create(name).subscribe(
            (playlist) => this._created(playlist),
            (err) => this.alerts.error('Could not create playlist', err)
        );
    }
    
    _created(playlist) { // JOE ju
        this.playlists.push(playlist);
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.ADD, playlist);
        this.loading = false;
    }
    
    _firePlaylistsChanged(type, playlist) { // JOE ju
        this.playlistsChanges.emit({
            type,
            playlist,
            playlists: this.playlists
        });
    }
    
    delete(id) { // JOE ju
        this.loading = true;
        
        this.playlistCalls.delete(id).subscribe(
            () => this._deleted(id),
            (err) => this.alerts.error('Could not delete playlist', err)
        );
    }
    
    _deleted(id) { // JOE ju
        let plIndex = this.playlists.findIndex((pl) => pl.id === id);
        
        if(plIndex > -1) {
            this.playlists.splice(plIndex, 1);
        }
        
        this._firePlaylistsChanged(PLAYLIST_CHANGE_TYPES.REMOVE);
        this.loading = false;
    }
    
    get loading() { // JOE ju
        return this._loading;
    }
    
    set loading(loading) { // JOE ju
        let oldLoading = this._loading;
        
        this._loading = loading;
        this.loadingChanges.emit({
            old: oldLoading,
            new: this._loading
        });
    }
}
