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

import angular from 'angular';
import Libraries from 'components/library/Libraries';

describe(Libraries.name, () => {
    let libraries = null;
    let _alerts = null;
    let _queues = null;
    let _Library = null;
    let $q = null;
    let $rootScope = null;
    
    beforeEach(angular.mock.inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
        
        _alerts = {
            error() {}
        };
        _queues = {
            load() {}
        };
        _Library = {
            get: () => {
                return {$promise: $q.defer().promise};
            }
        };
        
        libraries = new Libraries(_alerts, _queues, _Library);
    }));
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(Libraries.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(Libraries.injectID).toEqual(jasmine.any(String));
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
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            spyOn(_Library, 'get').and.returnValue({
                $promise: libDefer.promise
            });
            spyOn(libraries, 'rebuildCache').and.stub();
        });
        
        it('creates and sets a library', () => {
            libraries.library = null;
            libraries.load();
            expect(libraries.library).toBeDefined();
            expect(libraries.library).not.toBeNull();
        });
        
        it('rebuilds the cache when the library is successfully loaded', () => {
            spyOn(_alerts, 'error').and.stub();
            
            libraries.load();
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(libraries.rebuildCache).toHaveBeenCalled();
        });
        
        it('displays an error message if the library fails to load', () => {
            spyOn(_alerts, 'error').and.stub();
            
            libraries.load();
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('clear', () => {
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            libraries.library = {$promise: libDefer.promise};
            spyOn(libraries, 'clearNow').and.stub();
        });
        
        it('clears the library after it has been loaded', () => {
            libraries.clear();
            libDefer.resolve();
            $rootScope.$apply();
            expect(libraries.clearNow).toHaveBeenCalled();
        });
    });
    
    describe('clearNow', () => {
        let libDefer = null;
        let _library = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            _library = {$delete: () => {}};
            
            spyOn(_library, '$delete').and.returnValue(libDefer.promise);
            spyOn(libraries, 'load').and.stub();
            spyOn(_queues, 'load').and.stub();
            spyOn(_alerts, 'error').and.stub();
            
            libraries.library = _library;
        });
        
        it('makes a call to delete the library', function() {
            libraries.clearNow();
            expect(_library.$delete).toHaveBeenCalled();
        });
        
        it('reloads the library if the library was successfully cleared', () => {
            libraries.clearNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(libraries.load).toHaveBeenCalled();
        });
        
        it('reloads the queue if the library was successfully cleared', () => {
            libraries.clearNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(_queues.load).toHaveBeenCalledWith(true);
        });
        
        it('displays an error message if the library failed to clear', () => {
            libraries.clearNow();
            
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('rebuild', () => {
        let libDefer = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            libraries.library = {$promise: libDefer.promise};
            spyOn(libraries, 'rebuildNow').and.stub();
        });
        
        it('rebuilds the library after it has been loaded', () => {
            libraries.rebuild();
            libDefer.resolve();
            $rootScope.$apply();
            expect(libraries.rebuildNow).toHaveBeenCalled();
        });
    });
    
    describe('rebuildNow', () => {
        let libDefer = null;
        let _library = null;
        
        beforeEach(() => {
            libDefer = $q.defer();
            _library = {$rebuild: () => {}};
            
            spyOn(_library, '$rebuild').and.returnValue(libDefer.promise);
            spyOn(libraries, 'load').and.stub();
            spyOn(_queues, 'load').and.stub();
            spyOn(_alerts, 'error').and.stub();
            
            libraries.library = _library;
        });
        
        it('makes a call to rebuild the library', function() {
            libraries.rebuildNow();
            expect(_library.$rebuild).toHaveBeenCalled();
        });
        
        it('reloads the library if the library was successfully rebuilt', () => {
            libraries.rebuildNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(libraries.load).toHaveBeenCalled();
        });
        
        it('reloads the queue if the library was successfully rebuilt', () => {
            libraries.rebuildNow();
            
            libDefer.resolve();
            $rootScope.$apply();
            
            expect(_queues.load).toHaveBeenCalledWith(true);
        });
        
        it('displays an error message if the library failed to rebuild', () => {
            libraries.rebuildNow();
            
            libDefer.reject();
            $rootScope.$apply();
            
            expect(_alerts.error).toHaveBeenCalled();
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
            spyOn(libraries, 'getSongKey').and.callFake((artist, album) => {
                return `${artist}_${album}`;
            });
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
        it('adds each artist to an array', () => {
            libraries.cache.artists = new Set(['a', 'b', 'c']);
            expect(libraries.getArtists()).toEqual(['a', 'b', 'c']);
        });
    });
    
    describe('getAlbumsForArtist', () => {
        beforeEach(() => {
            libraries.cache.albumsForArtists = new Map();
            libraries.cache.albumsForArtists.set('artist_1', new Set(['a', 'b']));
            libraries.cache.albumsForArtists.set('artist_2', new Set());
        });
        
        it('returns an empty array when the given artist has no albums', () => {
            expect(libraries.getAlbumsForArtist('artist_2')).toEqual([]);
        });
        
        it('returns an empty array when the given artist is not in the cache', () => {
            expect(libraries.getAlbumsForArtist('asdf')).toEqual([]);
        });
        
        it('returns an array of albums for the given artist', () => {
            expect(libraries.getAlbumsForArtist('artist_1')).toEqual(['a', 'b']);
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
            
            spyOn(libraries, 'getSongKey').and.callFake((artist, album) => {
                return `${artist}_${album}`;
            });
        });
        
        it('returns an empty array when the given artist+album has no songs', () => {
            expect(libraries.getSongsForAlbum('artist2', 'album2')).toEqual([]);
        });
        
        it('returns an empty array when the given artist+album is not in the cache', () => {
            expect(libraries.getSongsForAlbum('asdf', 'jkl')).toEqual([]);
        });
        
        it('returns an array of albums for the given artist+album', () => {
            expect(libraries.getSongsForAlbum('artist1', 'album1')).toEqual([song1, song2]);
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
