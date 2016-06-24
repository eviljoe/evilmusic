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
import {MODAL_DIRECTVES} from 'ng2-bootstrap/ng2-bootstrap';

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
    
    static get annotations() { // JOE ju
        return [new Component({
            selector: 'em-playlist-dialog',
            templateUrl: 'components/playlists/playlist-dialog/playlist-dialog.html',
            directives: [MODAL_DIRECTVES],
            inputs: ['visible', 'mode'],
            outputs: ['onCancel', 'onSave'],
            queries: {
                playlistDialog: new ViewChild('playlistDialog')
            }
        })];
    }
    
    static get parameters() { // JOE ju
        return [[Playlists]];
    }

    ngOnChanges(change) { // JOE ju
        if(change.visible !== undefined) {
            this.setDialogVisible(change.visible.currentValue);
        }
    }
    
    setDialogVisible(visible) { // JOE ju
        if(this.playlistDialog) {
            if(visible) {
                this.playlistDialog.show();
            } else {
                this.playlistDialog.hide();
            }
        }
    }
    
    isValid() {
        return true; // JOE todo return false if playlist name is empty
    }
    
    cancel() { // JOE ju
        this.onCancel.emit(PLAYLIST_DIALOG_CLOSE_STATUS.CANCELED);
    }
    
    save() { // JOE ju
        if(this.isValid()) {
            this._saveNow();
        }
    }
    
    _saveNow() { // JOE ju
        let observable;
        
        if(this.mode === PLAYLIST_DIALOG_MODE.CREATE) {
            observable = this.playlists.create(this.playlistName);
        } else if(this.mode === PLAYLIST_DIALOG_MODE.EDIT) {
            observable = null; // JOE todo
        }
        
        observable.subscribe(() => this._saved());
    }
    
    _saved() { // JOE ju
        this.onSave.emit(PLAYLIST_DIALOG_CLOSE_STATUS.SAVED);
    }
}
