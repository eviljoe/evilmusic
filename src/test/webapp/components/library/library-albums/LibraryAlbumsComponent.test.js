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

import {LibraryAlbumsComponent} from 'components/library/library-albums/LibraryAlbumsComponent';

describe(LibraryAlbumsComponent.name, () => {
    let comp = null;
    let _libraries = null;
    
    beforeEach(() => {
        _libraries = {
            getAlbumsForArtist() {}
        };
        
        comp = new LibraryAlbumsComponent(_libraries);
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
    
    describe('getAlbums', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getAlbumsForArtist').and.returnValue(['c', 'b', 'a']);
        });
        
        it('returns the albums from the library', () => {
            expect(comp.getAlbums()).toContain('c', 'b', 'a');
        });
        
        it('sorts the albums when they exist', () => {
            expect(comp.getAlbums()).toEqual(['a', 'b', 'c']);
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
});
