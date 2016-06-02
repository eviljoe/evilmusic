/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2015 Joe Falascino
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
import {Libraries} from 'services/libraries';

describe(Libraries.name, () => {
    let libraries;
    let _alerts;
    let _queues;
    let _libraryCalls;
    
    beforeEach(() => {
        _alerts = {
            error() {}
        };
        
        _queues = {
            load() {}
        };
        
        _libraryCalls = {
            get: () => {
                return Observable.create((observer) => {
                    observer.next({});
                    observer.complete();
                });
            },
            
            clear() {},

            rebuild() {}
        };
        
        libraries = new Libraries(_alerts, _libraryCalls, _queues);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Libraries.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Libraries.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(libraries, 'load').and.stub();
        });
        
        it('loads the library', () => {
            libraries.init();
            expect(libraries.load).toHaveBeenCalled();
        });
    });
    
    describe('load', () => {
        let getObserver = null;
        
        beforeEach(() => {
            spyOn(_libraryCalls, 'get').and.returnValue(Observable.create((observer) => getObserver = observer));
            spyOn(libraries, '_loaded').and.stub();
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('loads the library', () => {
            libraries.load();
            expect(_libraryCalls.get).toHaveBeenCalled();
        });
        
        it('updates the library if the load is successful', () => {
            libraries.load();
            
            getObserver.next({foo: 'bar'});
            getObserver.complete();
            
            expect(libraries._loaded).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('displays an error message if the library fails to load', () => {
            libraries.load();
            getObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_loaded', () => {
        beforeEach(() => {
            libraries.libraryChanges = jasmine.createSpyObj('libraryChanges', ['emit']);
            spyOn(libraries, 'rebuildCache').and.stub();
        });
        
        it('sets the library', () => {
            libraries.library = null;
            libraries._loaded({foo: 'bar'});
            expect(libraries.library).toEqual({foo: 'bar'});
        });
        
        it('rebuilds the cache', () => {
            libraries.library = null;
            libraries._loaded({foo: 'bar'});
            expect(libraries.rebuildCache).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('emits a library change event', () => {
            libraries._loaded({foo: 'bar'});
            expect(libraries.libraryChanges.emit).toHaveBeenCalled();
        });
    });
    
    describe('clear', () => {
        let clearObserver;
        
        beforeEach(() => {
            spyOn(_alerts, 'error').and.stub();
            spyOn(_libraryCalls, 'clear').and.returnValue(Observable.create((observer) => clearObserver = observer));
            spyOn(libraries, '_cleared').and.stub();
        });
        
        it('clears the library', () => {
            libraries.clear();
            expect(_libraryCalls.clear).toHaveBeenCalled();
        });
        
        it('updates the library after it has been cleared', () => {
            libraries.clear();
            
            clearObserver.next({foo: 'bar'});
            clearObserver.complete();
            
            expect(libraries._cleared).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('displays an error message if the clear fails', () => {
            libraries.clear();
            clearObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_cleared', () => {
        beforeEach(() => {
            libraries.libraryChanges = jasmine.createSpyObj('libraryChanges', ['emit']);
            spyOn(libraries, 'rebuildCache').and.stub();
            spyOn(_queues, 'load').and.stub();
        });
        
        it('sets the library', () => {
            libraries.library = null;
            libraries._cleared({foo: 'bar'});
            expect(libraries.library).toEqual({foo: 'bar'});
        });
        
        it('reloads the queue', () => {
            libraries._cleared({foo: 'bar'});
            expect(_queues.load).toHaveBeenCalled();
        });
        
        it('rebuilds the cache', () => {
            libraries._cleared({foo: 'bar'});
            expect(libraries.rebuildCache).toHaveBeenCalled();
        });
        
        it('emits a library change event', () => {
            libraries._cleared({foo: 'bar'});
            expect(libraries.libraryChanges.emit).toHaveBeenCalled();
        });
    });
    
    describe('rebuild', () => {
        let rebuildObserver;
        
        beforeEach(() => {
            spyOn(_alerts, 'error').and.stub();
            spyOn(_libraryCalls, 'rebuild').and.returnValue(Observable.create((observer) => {
                rebuildObserver = observer;
            }));
            spyOn(libraries, '_rebuilt').and.stub();
        });
        
        it('rebuilds the library', () => {
            libraries.rebuild();
            expect(_libraryCalls.rebuild).toHaveBeenCalled();
        });
        
        it('updates the library after it has been rebuilt', () => {
            libraries.rebuild();
            
            rebuildObserver.next({foo: 'bar'});
            rebuildObserver.complete();
            
            expect(libraries._rebuilt).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('displays an error message if the rebuild fails', () => {
            libraries.rebuild();
            rebuildObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_rebuilt', () => {
        beforeEach(() => {
            libraries.libraryChanges = jasmine.createSpyObj('libraryChanges', ['emit']);
            spyOn(libraries, 'rebuildCache').and.stub();
            spyOn(_queues, 'load').and.stub();
        });
        
        it('sets the library', () => {
            libraries.library = null;
            libraries._rebuilt({foo: 'bar'});
            expect(libraries.library).toEqual({foo: 'bar'});
        });
        
        it('reloads the queue', () => {
            libraries._rebuilt({foo: 'bar'});
            expect(_queues.load).toHaveBeenCalled();
        });
        
        it('rebuilds the library cache', () => {
            libraries._rebuilt({foo: 'bar'});
            expect(libraries.rebuildCache).toHaveBeenCalled();
        });
        
        it('emits a library change event', () => {
            libraries._rebuilt({foo: 'bar'});
            expect(libraries.libraryChanges.emit).toHaveBeenCalled();
        });
    });
    
    describe('rebuildCache', () => {
        let lib = null;
        let songA = null;
        let songB = null;
        
        beforeEach(() => {
            spyOn(libraries.cache.artists, 'clear').and.callThrough();
            spyOn(libraries.cache.albumsForArtists, 'clear').and.callThrough();
            spyOn(libraries.cache.songsForAlbum, 'clear').and.callThrough();
            
            spyOn(libraries, 'cacheArtist').and.stub();
            spyOn(libraries, 'cacheAlbum').and.stub();
            spyOn(libraries, 'cacheSong').and.stub();
            
            songA = {a: 'A'};
            songB = {b: 'B'};
            lib = {songs: [songA, songB]};
        });
        
        it('clears the cached artists', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cache.artists.clear).toHaveBeenCalled();
        });
        
        it('clears the cached albums', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cache.albumsForArtists.clear).toHaveBeenCalled();
        });
        
        it('clears the cached songs', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cache.songsForAlbum.clear).toHaveBeenCalled();
        });
        
        it('caches the artist for each song in the library', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cacheArtist).toHaveBeenCalledWith(songA);
            expect(libraries.cacheArtist).toHaveBeenCalledWith(songB);
        });
        
        it('caches the album for each song in the library', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cacheAlbum).toHaveBeenCalledWith(songA);
            expect(libraries.cacheAlbum).toHaveBeenCalledWith(songB);
        });
        
        it('caches each song in the library', () => {
            libraries.rebuildCache(lib);
            expect(libraries.cacheSong).toHaveBeenCalledWith(songA);
            expect(libraries.cacheSong).toHaveBeenCalledWith(songB);
        });
    });
    
    describe('cacheArtist', () => {
        it('adds the song\'s artist the cache', () => {
            libraries.cacheArtist({artist: 'abc'});
            expect(libraries.cache.artists.has('abc')).toEqual(true);
        });
        
        it('does not add the same artist to the cache twice', () => {
            libraries.cacheArtist({artist: 'abc'});
            libraries.cacheArtist({artist: 'abc'});
            
            expect(libraries.cache.artists.size).toEqual(1);
        });
    });
    
    describe('cacheAlbum', () => {
        it('adds a new entry to the albums cache when one does not exist for the song\'s artist', () => {
            libraries.cache.albumsForArtists = new Map();
            libraries.cacheAlbum({artist: 'a', album: 'x'});
            expect(libraries.cache.albumsForArtists.get('a')).toEqual(new Set(['x']));
        });
        
        it('appends the album to the artist\'s entry in the albums cache when an entry exists', () => {
            libraries.cache.albumsForArtists = new Map();
            
            libraries.cacheAlbum({artist: 'a', album: 'x'});
            libraries.cacheAlbum({artist: 'a', album: 'y'});
            
            expect(libraries.cache.albumsForArtists.get('a')).toEqual(new Set(['x', 'y']));
        });
    });
    
    describe('cacheSong', () => {
        beforeEach(() => {
            spyOn(libraries, 'getSongKey').and.callFake((artist, album) => `${artist}_${album}`);
        });
        
        it('adds a new entry to the songs cache cache when one does not exist for the song', () => {
            let song = {artist: 'a', album: 'x'};
            
            libraries.cache.songsForAlbum = new Map();
            libraries.cacheSong(song);
            expect(libraries.cache.songsForAlbum.get('a_x')).toEqual(new Set([song]));
        });
        
        it('appends the song to the entry in the songs cache when an entry exists', () => {
            let songA = {artist: 'a', album: 'x', title: 'A'};
            let songB = {artist: 'a', album: 'x', title: 'B'};
            
            libraries.cache.songsForAlbum = new Map();
            libraries.cacheSong(songA);
            libraries.cacheSong(songB);
            expect(libraries.cache.songsForAlbum.get('a_x')).toEqual(new Set([songA, songB]));
        });
    });
    
    describe('getArtists', () => {
        it('adds each artist to a set', () => {
            libraries.cache.artists = new Set(['a', 'b', 'c']);
            expect(libraries.getArtists()).toEqual(new Set(['a', 'b', 'c']));
        });
    });
    
    describe('getAlbumsForArtist', () => {
        beforeEach(() => {
            libraries.cache.albumsForArtists = new Map();
            libraries.cache.albumsForArtists.set('artist_1', new Set(['a', 'b']));
            libraries.cache.albumsForArtists.set('artist_2', new Set());
        });
        
        it('returns an empty set when the given artist has no albums', () => {
            expect(libraries.getAlbumsForArtist('artist_2')).toEqual(new Set());
        });
        
        it('returns an empty set when the given artist is not in the cache', () => {
            expect(libraries.getAlbumsForArtist('asdf')).toEqual(new Set());
        });
        
        it('returns a set of albums for the given artist', () => {
            expect(libraries.getAlbumsForArtist('artist_1')).toEqual(new Set(['a', 'b']));
        });
    });
    
    describe('getSongsForAlbum', () => {
        let song1 = null;
        let song2 = null;
        
        beforeEach(() => {
            song1 = {a: 'A'};
            song2 = {b: 'B'};
            
            libraries.cache.songsForAlbum = new Map();
            libraries.cache.songsForAlbum.set('artist1_album1', new Set([song1, song2]));
            libraries.cache.songsForAlbum.set('artist2_album2', new Set());
            
            spyOn(libraries, 'getSongKey').and.callFake((artist, album) => `${artist}_${album}`);
        });
        
        it('returns an empty Set() when the given artist+album has no songs', () => {
            expect(libraries.getSongsForAlbum('artist2', 'album2')).toEqual(new Set());
        });
        
        it('returns an empty Set() when the given artist+album is not in the cache', () => {
            expect(libraries.getSongsForAlbum('asdf', 'jkl')).toEqual(new Set());
        });
        
        it('returns a set of albums for the given artist+album', () => {
            expect(libraries.getSongsForAlbum('artist1', 'album1')).toEqual(new Set([song1, song2]));
        });
    });
    
    describe('getSongKey', () => {
        it('returns the same has when given the same artist & album', () => {
            let artist = 'artist_1';
            let album = 'album_1';
            
            expect(libraries.getSongKey(artist, album)).toEqual(libraries.getSongKey(artist, album));
        });
    });
});
