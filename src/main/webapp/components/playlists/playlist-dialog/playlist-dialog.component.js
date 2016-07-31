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

import {Component, EventEmitter, ViewChild} from '@angular/core';
import {MODAL_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

import {Playlists} from 'services/playlists';

export const PLAYLIST_DIALOG_MODE = {
    CREATE: 'create',
    EDIT: 'edit'
};

export const PLAYLIST_DIALOG_CLOSE_STATUS = {
    SAVED: 'save',
    CANCELED: 'canceled'
};

export class PlaylistDialogComponent {
    constructor(playlists) {
        this.playlists = playlists;
        
        this.onCancel = new EventEmitter();
        this.onSave = new EventEmitter();
        
        this.visible = false;
        this.mode = null;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-playlist-dialog',
            templateUrl: 'components/playlists/playlist-dialog/playlist-dialog.html',
            directives: [MODAL_DIRECTIVES],
            inputs: ['visible', 'mode', 'playlist'],
            outputs: ['onCancel', 'onSave'],
            queries: {
                playlistDialog: new ViewChild('playlistDialog'),
                playlistForm: new ViewChild('playlistForm')
            }
        })];
    }
    
    static get parameters() {
        return [[Playlists]];
    }

    ngAfterContentInit() {
        this.playlist = this.playlist || {};
    }

    ngOnChanges(change) {
        if(change.visible) {
            this.setDialogVisible(change.visible.currentValue);
        }
    }
    
    setDialogVisible(visible) {
        if(this.playlistDialog) {
            if(visible) {
                this._showDialog();
            } else {
                this._hideDialog();
            }
        }
    }
    
    _showDialog() {
        if(this.mode === PLAYLIST_DIALOG_MODE.CREATE) {
            this.playlist = {};
        }

        this.playlistDialog.show();
    }
    
    _hideDialog() {
        this.playlistDialog.hide();
    }
    
    cancel() {
        this.onCancel.emit(PLAYLIST_DIALOG_CLOSE_STATUS.CANCELED);
    }
    
    save() {
        if(this.playlistForm.form.valid) {
            this._saveNow();
        }
    }
    
    _saveNow() {
        let observable;
        
        if(this.mode === PLAYLIST_DIALOG_MODE.CREATE) {
            observable = this.playlists.create(this.playlist.name);
        } else if(this.mode === PLAYLIST_DIALOG_MODE.EDIT) {
            observable = null; // JOE todo
        }
        
        observable.subscribe(() => this._saved());
    }
    
    _saved() {
        this.onSave.emit(PLAYLIST_DIALOG_CLOSE_STATUS.SAVED);
    }
    
    getTitle() {
        let title = 'Playlist';
        
        if(this.mode === PLAYLIST_DIALOG_MODE.CREATE) {
            title = 'Create Playlist';
        } else if(this.mode === PLAYLIST_DIALOG_MODE.EDIT) {
            title = 'Edit Playlist';
        }
        
        return title;
    }
}
