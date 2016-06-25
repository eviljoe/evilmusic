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

import {Observable} from 'rxjs';

import {PlaylistDialogComponent, PLAYLIST_DIALOG_MODE, PLAYLIST_DIALOG_CLOSE_STATUS}
    from 'components/playlists/playlist-dialog/playlist-dialog.component';

describe(PlaylistDialogComponent.name, () => {
    let comp;
    let _playlists;
    
    beforeEach(() => {
        _playlists = {
            create() {}
        };
        
        comp = new PlaylistDialogComponent(_playlists);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(PlaylistDialogComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(PlaylistDialogComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('ngAfterContentInit', () => {
        it('creates a playlist if one is not defined', () => {
            comp.playlist = null;
            comp.ngAfterContentInit();
            expect(comp.playlist).toEqual({});
        });
        
        it('does not create a playlist of one is already defined', () => {
            let pl = {foo: 'bar'};
            
            comp.playlist = pl;
            comp.ngAfterContentInit();
            
            expect(comp.playlist).toBe(pl);
        });
    });
    
    describe('ngOnChanges', () => {
        beforeEach(() => {
            spyOn(comp, 'setDialogVisible').and.stub();
        });
        
        it('sets the dialog visibility when the visibility change is defined', () => {
            comp.ngOnChanges({
                visible: {
                    currentValue: true
                }
            });
            expect(comp.setDialogVisible).toHaveBeenCalledWith(true);
        });
        
        it('does not set the dialog visibility when the visibility change is undefined', () => {
            comp.ngOnChanges({});
            expect(comp.setDialogVisible).not.toHaveBeenCalled();
        });
    });
    
    describe('setDialogVisible', () => {
        beforeEach(() => {
            spyOn(comp, '_showDialog').and.stub();
            spyOn(comp, '_hideDialog').and.stub();
            comp.playlistDialog = {};
        });
        
        it('does nothing when the playlist dialog is falsy', () => {
            comp.playlistDialog = null;
            comp.setDialogVisible(true);
            expect(comp._showDialog).not.toHaveBeenCalled();
            expect(comp._hideDialog).not.toHaveBeenCalled();
        });
        
        it('shows the dialog when given true', () => {
            comp.setDialogVisible(true);
            expect(comp._showDialog).toHaveBeenCalled();
            expect(comp._hideDialog).not.toHaveBeenCalled();
        });
        
        it('hides the dialog when given false', () => {
            comp.setDialogVisible(false);
            expect(comp._showDialog).not.toHaveBeenCalled();
            expect(comp._hideDialog).toHaveBeenCalled();
        });
    });
    
    describe('_showDialog', () => {
        beforeEach(() => {
            comp.playlistDialog = jasmine.createSpyObj('playlistDialog', ['show']);
        });
        
        it('creates a new playlist when in create mode', () => {
            comp.playlist = null;
            comp.mode = PLAYLIST_DIALOG_MODE.CREATE;
            comp._showDialog();
            expect(comp.playlist).toEqual({});
        });
        
        it('does not create a new playlist when not in create mode', () => {
            let pl = {foo: 'bar'};
            
            comp.playlist = pl;
            comp.mode = PLAYLIST_DIALOG_MODE.EDIT;
            comp._showDialog();
            
            expect(comp.playlist).toBe(pl);
        });
        
        it('shows the playlist dialog', () => {
            comp._showDialog();
            expect(comp.playlistDialog.show).toHaveBeenCalled();
        });
    });
    
    describe('_hideDialog', () => {
        beforeEach(() => {
            comp.playlistDialog = jasmine.createSpyObj('playlistDialog', ['hide']);
        });
        
        it('hides the dialog', () => {
            comp._hideDialog();
            expect(comp.playlistDialog.hide).toHaveBeenCalled();
        });
    });
    
    describe('cancel', () => {
        beforeEach(() => {
            spyOn(comp.onCancel, 'emit').and.stub();
        });
        
        it('emits a cancel event', () => {
            comp.cancel();
            expect(comp.onCancel.emit).toHaveBeenCalledWith(PLAYLIST_DIALOG_CLOSE_STATUS.CANCELED);
        });
    });
    
    describe('save', () => {
        beforeEach(() => {
            comp.playlistForm = {
                form: {}
            };
            spyOn(comp, '_saveNow').and.stub();
        });
        
        it('saves when the form is valid', () => {
            comp.playlistForm.form.valid = true;
            comp.save();
            expect(comp._saveNow).toHaveBeenCalled();
        });
        
        it('does not save when the form is invalid', () => {
            comp.playlistForm.form.valid = false;
            comp.save();
            expect(comp._saveNow).not.toHaveBeenCalled();
        });
    });
    
    describe('_saveNow', () => {
        let saveObserver;
        
        beforeEach(() => {
            spyOn(_playlists, 'create').and.returnValue(Observable.create((observer) => saveObserver = observer));
            spyOn(comp, '_saved').and.stub();
            comp.playlist = {};
            comp.mode = PLAYLIST_DIALOG_MODE.CREATE;
        });
        
        it('creates a playlist when in create mode', () => {
            comp._saveNow();
            expect(_playlists.create).toHaveBeenCalled();
        });
        
        it('calls a function after the playlist has been saved', () => {
            comp._saveNow();
            
            saveObserver.next();
            saveObserver.complete();
            
            expect(comp._saved).toHaveBeenCalled();
        });
    });
    
    describe('_saved', () => {
        beforeEach(() => {
            spyOn(comp.onSave, 'emit').and.stub();
        });
        
        it('emits a cancel event', () => {
            comp._saved();
            expect(comp.onSave.emit).toHaveBeenCalledWith(PLAYLIST_DIALOG_CLOSE_STATUS.SAVED);
        });
    });
    
    describe('getTitle', () => {
        it('returns "Playlist" when the mode cannot be determined', () => {
            comp.mode = null;
            expect(comp.getTitle()).toEqual('Playlist');
        });
        
        it('returns "Create Playlist" when in create mode', () => {
            comp.mode = PLAYLIST_DIALOG_MODE.CREATE;
            expect(comp.getTitle()).toEqual('Create Playlist');
        });
        
        it('returns "Edit Playlist" when in edit mode', () => {
            comp.mode = PLAYLIST_DIALOG_MODE.EDIT;
            expect(comp.getTitle()).toEqual('Edit Playlist');
        });
    });
});
