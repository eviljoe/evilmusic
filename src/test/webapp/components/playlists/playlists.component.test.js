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

import {PlaylistsComponent} from 'components/playlists/playlists.component';

describe(PlaylistsComponent.name, () => {
    let comp;
    let _changeDetector;
    let _playlists;
    
    beforeEach(() => {
        _changeDetector = {
            markForCheck() {}
        };
        
        _playlists = {
            playlistsChanges: Observable.create(() => {}),
            
            loadingChanges: Observable.create(() => {}),
            
            create() {},

            delete() {}
        };
        
        comp = new PlaylistsComponent(_changeDetector, _playlists);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(PlaylistsComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(PlaylistsComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        let playlistsObserver;
        let loadingObserver;
        
        beforeEach(() => {
            spyOn(comp, 'setPlaylistDialogVisible').and.stub();
            spyOn(comp, '_playlistsChanged').and.stub();
            spyOn(comp, '_playlistsLoadingChanged').and.stub();
            _playlists.playlistsChanges = Observable.create((observer) => playlistsObserver = observer);
            _playlists.loadingChanges = Observable.create((observer) => loadingObserver = observer);
        });
        
        it('sets the playlist to not be visible', () => {
            comp.init();
            expect(comp.setPlaylistDialogVisible).toHaveBeenCalledWith(false);
        });
        
        it('reacts to playlist changes', () => {
            comp.init();
            
            playlistsObserver.next();
            playlistsObserver.complete();
            
            expect(comp._playlistsChanged).toHaveBeenCalled();
        });
        
        it('reacts to loading changes', () => {
            comp.init();
            
            loadingObserver.next();
            loadingObserver.complete();
            
            expect(comp._playlistsLoadingChanged).toHaveBeenCalled();
        });
    });
    
    describe('_playlistsChanged', () => {
        beforeEach(() => {
            spyOn(_changeDetector, 'markForCheck').and.stub();
        });
        
        it('marks the component for change detection', () => {
            comp._playlistsChanged();
            expect(_changeDetector.markForCheck).toHaveBeenCalled();
        });
    });
    
    describe('_playlistsLoadingChanged', () => {
        it("sets the loading flag to the playlist's loading flag", () => {
            _playlists.loading = 'foo';
            comp._playlistsLoadingChanged();
            expect(comp.loading).toEqual(_playlists.loading);
        });
    });
    
    describe('playlistClicked', () => {
        it('sets the playlist', () => {
            let pl = {foo: 'bar'};
            
            comp.playlistClicked(pl);
            expect(comp.playlist).toBe(pl);
        });
    });
    
    describe('createPlaylist', () => {
        beforeEach(() => {
            spyOn(comp, 'setPlaylistDialogVisible').and.stub();
        });
        
        it('sets the playlist dialog to be visible', () => {
            comp.createPlaylist();
            expect(comp.setPlaylistDialogVisible).toHaveBeenCalledWith(true);
        });
    });
    
    describe('deletePlaylist', () => {
        beforeEach(() => {
            spyOn(_playlists, 'delete').and.stub();
        });
        
        it('creates a new playlist', () => {
            comp.deletePlaylist({id: 123});
            expect(_playlists.delete).toHaveBeenCalledWith(123);
        });
    });
    
    describe('setPlaylistDialogVisible', () => {
        it('sets the flag', () => {
            comp.setPlaylistDialogVisible(true);
            expect(comp.playlistDialogVisible).toEqual(true);
        });
    });
});
