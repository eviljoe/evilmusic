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

import {LibraryComponent} from 'components/library/library.component';

describe(LibraryComponent.name, () => {
    let comp;
    let _libraries;
    
    beforeEach(() => {
        _libraries = {
            libraryChanges: Observable.create(() => {}),
            
            loadingChanges: Observable.create(() => {}),
            
            getArtists() {},
            
            getAlbumsForArtist() {}
        };
        
        comp = new LibraryComponent(_libraries);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibraryComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibraryComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        let libraryObserver;
        let loadingObserver;
        
        beforeEach(() => {
            spyOn(comp, '_libraryChanged').and.stub();
            spyOn(comp, '_libraryLoadingChanged').and.stub();
            _libraries.libraryChanges = Observable.create((observer) => libraryObserver = observer);
            _libraries.loadingChanges = Observable.create((observer) => loadingObserver = observer);
        });
        
        it('calls a function when the library changes', () => {
            comp.init();
            
            libraryObserver.next();
            libraryObserver.complete();
            
            expect(comp._libraryChanged).toHaveBeenCalled();
        });
        
        it('updates based on the library loading status', () => {
            comp.init();
            expect(comp._libraryLoadingChanged).toHaveBeenCalled();
        });
        
        it('calls a function when the library loading changes', () => {
            comp.init();
            comp._libraryLoadingChanged.calls.reset();
            
            loadingObserver.next();
            loadingObserver.complete();
            
            expect(comp._libraryLoadingChanged).toHaveBeenCalled();
        });
    });
    
    describe('_libraryChanged', () => {
        beforeEach(() => {
            spyOn(comp, 'isArtistInLibrary').and.returnValue(true);
            spyOn(comp, 'isAlbumInLibrary').and.returnValue(true);
            spyOn(comp, 'backToAlbums').and.stub();
            spyOn(comp, 'backToArtists').and.stub();
        });
        
        it('does not go anywhere if the library still contains the artist and album', () => {
            comp._libraryChanged();
            expect(comp.backToAlbums).not.toHaveBeenCalled();
            expect(comp.backToArtists).not.toHaveBeenCalled();
        });
        
        it('goes back to the albums if the library contains the artist but not the album', () => {
            comp.isAlbumInLibrary.and.returnValue(false);
            comp._libraryChanged();
            expect(comp.backToAlbums).toHaveBeenCalled();
        });
        
        it('goes back to the artists if the library does not contain the artist', () => {
            comp.isArtistInLibrary.and.returnValue(false);
            comp._libraryChanged();
            expect(comp.backToArtists).toHaveBeenCalled();
        });
    });
    
    describe('_libraryLoadingChanged', () => {
        it("sets the loading to the value of the libraries' loading status", () => {
            _libraries.loading = 'foo';
            comp.loading = null;
            comp._libraryLoadingChanged();
            expect(comp.loading).toEqual(_libraries.loading);
        });
    });
    
    describe('isArtistInLibrary', () => {
        beforeEach(() => {
            comp.artist = 'a';
            spyOn(_libraries, 'getArtists').and.returnValue(new Set(['a', 'b', 'c']));
        });
        
        it('returns true if the library contains the artist', () => {
            expect(comp.isArtistInLibrary()).toEqual(true);
        });
        
        it('returns false if the library does not contain the artist', () => {
            _libraries.getArtists.and.returnValue(new Set(['x', 'y', 'z']));
            expect(comp.isArtistInLibrary()).toEqual(false);
        });
        
        it('returns false if the artist is falsy', () => {
            comp.artist = null;
            expect(comp.isArtistInLibrary()).toEqual(false);
        });
    });
    
    describe('isAlbumInLibrary', () => {
        beforeEach(() => {
            comp.artist = 'foo';
            comp.album = 'a';
            spyOn(_libraries, 'getAlbumsForArtist').and.returnValue(new Set(['a', 'b', 'c']));
        });
        
        it('returns true if the library contains the album', () => {
            expect(comp.isAlbumInLibrary()).toEqual(true);
        });
        
        it('returns false if the library does not contain the album', () => {
            _libraries.getAlbumsForArtist.and.returnValue(new Set(['x', 'y', 'z']));
            expect(comp.isAlbumInLibrary()).toEqual(false);
        });
        
        it('returns false if the artist is falsy', () => {
            comp.artist = null;
            expect(comp.isAlbumInLibrary()).toEqual(false);
        });
        
        it('returns false if the album is falsy', () => {
            comp.album = null;
            expect(comp.isAlbumInLibrary()).toEqual(false);
        });
    });
    
    describe('backToArtists', () => {
        beforeEach(() => {
            comp.artist = 'foo';
            comp.album = 'bar';
        });
        
        it('sets the artist to null', () => {
            comp.backToArtists();
            expect(comp.artist).toBeNull();
        });
        
        it('sets the album to null', () => {
            comp.backToArtists();
            expect(comp.album).toBeNull();
        });
    });
    
    describe('backToAlbums', () => {
        beforeEach(() => {
            comp.album = 'bar';
        });
        
        it('sets the album to null', () => {
            comp.backToAlbums();
            expect(comp.album).toBeNull();
        });
    });
    
    describe('artistChanged', () => {
        it('sets the artist', () => {
            comp.artist = null;
            comp.artistChanged('foo');
            expect(comp.artist).toEqual('foo');
        });
    });
    
    describe('albumChanged', () => {
        it('sets the album', () => {
            comp.album = null;
            comp.albumChanged('foo');
            expect(comp.album).toEqual('foo');
        });
    });
});
