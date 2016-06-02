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

import {LibrarySongsComponent} from 'components/library/library-songs/library-songs.component';

describe(LibrarySongsComponent.name, () => {
    let comp;
    let _changeDetector;
    let _emUtils;
    let _libraries;
    let _queues;
    
    beforeEach(() => {
        _changeDetector = {
            detectChanges() {}
        };
        
        _libraries = {
            libraryChanges: Observable.create(() => {}),
            
            getSongsForAlbum() {}
        };
        
        _queues = {addLast: () => {}};
        
        _emUtils = {};
        
        comp = new LibrarySongsComponent(_changeDetector, _emUtils, _libraries, _queues);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibrarySongsComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibrarySongsComponent.parameters).toEqual(jasmine.any(Array));
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
    
    describe('addLast', () => {
        it('adds the song with the given ID to the end of the queue', () => {
            spyOn(_queues, 'addLast');
            comp.addLast(17);
            expect(_queues.addLast).toHaveBeenCalledWith(17);
        });
    });
    
    describe('getSongs', () => {
        let song1;
        let song2;
        let song3;
        
        beforeEach(() => {
            song1 = {trackNumber: 1};
            song2 = {trackNumber: 2};
            song3 = {trackNumber: 3};
            spyOn(_libraries, 'getSongsForAlbum').and.returnValue([song3, song2, song1]);
        });
        
        it('returns the songs from the library', () => {
            expect(comp.getSongs()).toContain(song3, song2, song1);
        });
    });
});
