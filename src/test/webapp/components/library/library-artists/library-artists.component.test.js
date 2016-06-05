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

import {LibraryArtistsComponent} from 'components/library/library-artists/library-artists.component';

describe(LibraryArtistsComponent.name, () => {
    let comp;
    let _changeDetector;
    let _libraries;
    
    beforeEach(() => {
        _changeDetector = {
            detectChanges() {}
        };
        
        _libraries = {
            libraryChanges: Observable.create(() => {}),
            
            getArtists() {}
        };
        
        comp = new LibraryArtistsComponent(_changeDetector, _libraries);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibraryArtistsComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibraryArtistsComponent.parameters).toEqual(jasmine.any(Array));
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
    
    describe('artistClicked', () => {
        beforeEach(() => {
            comp.artistChanged = jasmine.createSpyObj('artistChanged', ['emit']);
        });
        
        it('emits an artist change event', () => {
            comp.artistClicked('foo');
            expect(comp.artistChanged.emit).toHaveBeenCalledWith('foo');
        });
    });
});
