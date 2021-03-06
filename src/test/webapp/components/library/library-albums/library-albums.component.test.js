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

import {LibraryAlbumsComponent} from 'components/library/library-albums/library-albums.component';

describe(LibraryAlbumsComponent.name, () => {
    let comp;
    let _changeDetector;
    let _libraries;
    
    beforeEach(() => {
        _changeDetector = {
            detectChanges() {}
        };
        
        _libraries = {
            libraryChanges: Observable.create(() => {}),
            getAlbumsForArtist() {},
            getSongCountForAlbum() {}
        };
        
        comp = new LibraryAlbumsComponent(_changeDetector, _libraries);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibraryAlbumsComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibraryAlbumsComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        let libraryObserver;
        
        beforeEach(() => {
            _libraries.libraryChanges = Observable.create((observer) => libraryObserver = observer);
            spyOn(comp, '_libraryChanged').and.stub();
        });
        
        it('updates after the library changes', () => {
            comp.init();
            
            libraryObserver.next();
            libraryObserver.complete();
            
            expect(comp._libraryChanged).toHaveBeenCalled();
        });
    });
    
    describe('_libraryChanged', () => {
        beforeEach(() => {
            spyOn(_changeDetector, 'detectChanges').and.stub();
        });
        
        it('runs the change detection', () => {
            comp._libraryChanged();
            expect(_changeDetector.detectChanges).toHaveBeenCalled();
        });
    });
    
    describe('getAlbums', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getAlbumsForArtist').and.returnValue(['c', 'b', 'a']);
        });
        
        it('returns the albums from the library', () => {
            expect(comp.getAlbums()).toContain('c', 'b', 'a');
        });
    });
    
    describe('albumClicked', () => {
        beforeEach(() => {
            comp.albumChanged = jasmine.createSpyObj('albumChanged', ['emit']);
        });
        
        it('emits an album change event', () => {
            comp.albumClicked('foo');
            expect(comp.albumChanged.emit).toHaveBeenCalledWith('foo');
        });
    });
    
    describe('getSongCount', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getSongCountForAlbum').and.returnValue(7);
        });
        
        it('returns the song count', () => {
            expect(comp.getSongCount('foo')).toEqual(7);
        });
    });
});
