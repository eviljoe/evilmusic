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
import {Playlists} from 'services/playlists';

describe(Playlists.name, () => {
    let playlists;
    let _alerts;
    let _playlistCalls;
    
    beforeEach(() => {
        _alerts = {
            error() {}
        };
        
        _playlistCalls = {
            getAll: () => Observable.create(() => {}),
            create() {},
            delete() {},
            setName() {},
            addLast() {}
        };
        
        playlists = new Playlists(_alerts, _playlistCalls);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Playlists.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Playlists.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(playlists, 'load').and.stub();
        });
        
        it('loads the playlists', () => {
            playlists.init();
            expect(playlists.load).toHaveBeenCalled();
        });
    });
    
    describe('load', () => {
        let loadObserver;
        
        beforeEach(() => {
            spyOn(playlists, '_loaded').and.stub();
            spyOn(_playlistCalls, 'getAll').and.returnValue(Observable.create((observer) => loadObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            playlists.load();
            expect(playlists.loading).toEqual(true);
        });
        
        it('gets all of the playlists', () => {
            playlists.load();
            expect(_playlistCalls.getAll).toHaveBeenCalled();
        });
        
        it('reacts when the playlists are loaded', () => {
            playlists.load();
            
            loadObserver.next();
            loadObserver.complete();
            
            expect(playlists._loaded).toHaveBeenCalled();
        });
        
        it('displays an error when the playlists cannot be loaded', () => {
            playlists.load();
            
            loadObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_loaded', () => {
        beforeEach(() => {
            spyOn(playlists, '_firePlaylistsChanged').and.stub();
        });
        
        it('sets the playlists', () => {
            playlists._loaded([{foo: 'bar'}]);
            expect(playlists.playlists).toEqual([{foo: 'bar'}]);
        });
        
        it('fires a playlists change event', () => {
            playlists._loaded({foo: 'bar'});
            expect(playlists._firePlaylistsChanged).toHaveBeenCalledWith('load');
        });
        
        it('sets the loading flag to false', () => {
            playlists._loaded({foo: 'bar'});
            expect(playlists.loading).toEqual(false);
        });
    });
    
    describe('create', () => {
        let createObserver;
        let createObservable;
        
        beforeEach(() => {
            createObservable = Observable.create((observer) => createObserver = observer);
            spyOn(_playlistCalls, 'create').and.returnValue(createObservable);
            spyOn(playlists, '_created').and.stub();
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            playlists.create('foo');
            expect(playlists.loading).toEqual(true);
        });
        
        it('creates a playlist with the given name', () => {
            playlists.create('foo');
            expect(_playlistCalls.create).toHaveBeenCalledWith('foo');
        });
        
        it('reacts when the playlist is created', () => {
            playlists.create('foo');
            
            createObserver.next();
            createObserver.complete();
            
            expect(playlists._created).toHaveBeenCalled();
        });
        
        it('displays an error when the playlists cannot be created', () => {
            playlists.create('foo');
            
            createObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
        
        it('returns the observable', () => {
            expect(playlists.create('foo')).toBe(createObservable);
        });
    });
    
    describe('_created', () => {
        beforeEach(() => {
            playlists.playlists = [];
            spyOn(playlists, '_firePlaylistsChanged').and.stub();
        });
        
        it('adds the playlist to the array of playlists', () => {
            playlists._created({foo: 'bar'});
            expect(playlists.playlists).toEqual([{foo: 'bar'}]);
        });
        
        it('fires a playlists change event', () => {
            playlists._created({foo: 'bar'});
            expect(playlists._firePlaylistsChanged).toHaveBeenCalledWith('add', {foo: 'bar'});
        });
        
        it('sets the loading flag to false', () => {
            playlists._created({foo: 'bar'});
            expect(playlists.loading).toEqual(false);
        });
    });
    
    describe('delete', () => {
        let delObserver;
        
        beforeEach(() => {
            spyOn(playlists, '_deleted').and.stub();
            spyOn(_playlistCalls, 'delete').and.returnValue(Observable.create((observer) => delObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            playlists.delete(123);
            expect(playlists.loading).toEqual(true);
        });
        
        it('deletes the playlist with the given ID', () => {
            playlists.delete(123);
            expect(_playlistCalls.delete).toHaveBeenCalledWith(123);
        });
        
        it('reacts when the playlist is deleted', () => {
            playlists.delete(123);
            
            delObserver.next();
            delObserver.complete();
            
            expect(playlists._deleted).toHaveBeenCalled();
        });
        
        it('displays an error when the playlists cannot be deleted', () => {
            playlists.delete(123);
            
            delObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_deleted', () => {
        beforeEach(() => {
            playlists.playlists = [{id: 1}, {id: 2}, {id: 3}];
            spyOn(playlists, '_firePlaylistsChanged').and.stub();
        });
        
        it('removes the playlist with the given ID from the array of playlists', () => {
            playlists._deleted(2);
            expect(playlists.playlists).toEqual([{id: 1}, {id: 3}]);
        });
        
        it('does not remove the playlist with the given ID from the array of playlists if it cannot be found', () => {
            playlists._deleted(4);
            expect(playlists.playlists).toEqual([{id: 1}, {id: 2}, {id: 3}]);
        });
        
        it('fires a playlists change event', () => {
            playlists._deleted(2);
            expect(playlists._firePlaylistsChanged).toHaveBeenCalledWith('remove');
        });
        
        it('sets the loading flag to false', () => {
            playlists._deleted(2);
            expect(playlists.loading).toEqual(false);
        });
    });
    
    describe('setPlaylistName', () => {
        let setObserver;
        
        beforeEach(() => {
            spyOn(_playlistCalls, 'setName').and.returnValue(Observable.create((observer) => setObserver = observer));
            spyOn(_alerts, 'error').and.stub();
            spyOn(playlists, '_playlistNameSet').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            playlists.setPlaylistName(123, 'foo');
            expect(playlists.loading).toEqual(true);
        });
        
        it('sets the playlist name', () => {
            playlists.setPlaylistName(123, 'foo');
            expect(_playlistCalls.setName).toHaveBeenCalled();
        });
        
        it('calls a function after the playlist name has been set', () => {
            playlists.setPlaylistName(123, 'foo');
            
            setObserver.next();
            setObserver.complete();
            
            expect(playlists._playlistNameSet).toHaveBeenCalled();
        });
        
        it('displays an error message if the playlist name set fails', () => {
            playlists.setPlaylistName(123, 'foo');
            
            setObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_playlistNameSet', () => {
        beforeEach(() => {
            spyOn(playlists, '_replacePlaylist').and.stub();
            spyOn(playlists, '_firePlaylistsChanged').and.stub();
        });
        
        it('replaces the playlist', () => {
            playlists._playlistNameSet({id: 1});
            expect(playlists._replacePlaylist).toHaveBeenCalledWith({id: 1});
        });
        
        it('fires a playlist change event', () => {
            playlists._playlistNameSet({id: 1});
            expect(playlists._firePlaylistsChanged).toHaveBeenCalled();
        });
        
        it('sets the loading flag to false', () => {
            playlists._playlistNameSet({id: 1});
            expect(playlists.loading).toEqual(false);
        });
    });
    
    describe('_firePlaylistsChanged', () => {
        beforeEach(() => {
            spyOn(playlists.playlistsChanges, 'emit').and.stub();
        });
        
        it('fires an event', () => {
            playlists._firePlaylistsChanged('foo', {foo: 'bar'});
            expect(playlists.playlistsChanges.emit).toHaveBeenCalled();
        });
    });
    
    describe('addSongsLast', () => {
        let addObserver;
        
        beforeEach(() => {
            spyOn(_playlistCalls, 'addLast').and.returnValue(Observable.create((observer) => addObserver = observer));
            spyOn(_alerts, 'error').and.stub();
            spyOn(playlists, '_addedSongsLast').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            playlists.loading = false;
            playlists.addSongsLast({id: 1}, 10, 11, 12);
            expect(playlists.loading).toEqual(true);
        });
        
        it('adds the songs to the playlist', () => {
            playlists.addSongsLast({id: 1}, 10, 11, 12);
            expect(_playlistCalls.addLast).toHaveBeenCalledWith(1, [10, 11, 12]);
        });
        
        it('reacts when the songs are added successfully', () => {
            playlists.addSongsLast({id: 1}, 10, 11, 12);
            
            addObserver.next();
            addObserver.complete();
            
            expect(playlists._addedSongsLast).toHaveBeenCalled();
        });
        
        it('displays an error message when the songs cannot be added', () => {
            playlists.addSongsLast({id: 1}, 10, 11, 12);
            
            addObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_addedSongsLast', () => {
        beforeEach(() => {
            spyOn(playlists, '_replacePlaylist').and.stub();
            spyOn(playlists, '_firePlaylistsChanged').and.stub();
        });
        
        it('replaces the playlist', () => {
            playlists._addedSongsLast({foo: 'bar'});
            expect(playlists._replacePlaylist).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('fires a playlist change event', () => {
            playlists._addedSongsLast({foo: 'bar'});
            expect(playlists._firePlaylistsChanged).toHaveBeenCalledWith('update', {foo: 'bar'});
        });
        
        it('sets the loading flag to false', () => {
            playlists.loading = true;
            playlists._addedSongsLast({});
            expect(playlists.loading).toEqual(false);
        });
    });
    
    describe('_replacePlaylist', () => {
        beforeEach(() => {
            playlists.playlists = [{id: 1}, {id: 2}, {id: 3}];
        });
        
        it('replaces the playlist with the same ID as the given playlist', () => {
            let playlist = {id: 1, foo: 'bar'};
            
            playlists._replacePlaylist(playlist);
            expect(playlists.playlists.find((pl) => pl.id === playlist.id)).toBe(playlist);
        });
        
        it('does not replace any playlist if none with the same ID can be found', () => {
            let playlist = {id: 4, foo: 'bar'};
            
            playlists._replacePlaylist(playlist);
            playlists.playlists.forEach((pl) => {
                expect(pl.foo).toEqual(undefined);
            });
        });
    });
    
    describe('get loading', () => {
        it('returns the loading flag', () => {
            playlists._loading = 'foo';
            expect(playlists.loading).toEqual('foo');
        });
    });
    
    describe('set loading', () => {
        beforeEach(() => {
            playlists.loadingChanges = jasmine.createSpyObj('loadingChanges', ['emit']);
        });
        
        it('sets the loading flag', () => {
            playlists._loading = null;
            playlists.loading = true;
            expect(playlists._loading).toEqual(true);
        });
        
        it('emits a loading change', () => {
            playlists.loading = true;
            expect(playlists.loadingChanges.emit).toHaveBeenCalled();
        });
    });
});
