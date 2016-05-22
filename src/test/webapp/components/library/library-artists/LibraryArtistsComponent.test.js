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

import {LibraryArtistsComponent} from 'components/library/library-artists/LibraryArtistsComponent';

describe(LibraryArtistsComponent.name, () => {
    let comp = null;
    let _libraries = null;
    
    beforeEach(() => {
        _libraries = {
            getArtists() {}
        };
        
        comp = new LibraryArtistsComponent(_libraries);
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
    
    describe('getArtists', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getArtists').and.returnValue(['c', 'b', 'a']);
        });
        
        it('returns the artists from the library', () => {
            expect(comp.getArtists()).toContain('c', 'b', 'a');
        });
        
        it('sorts the artists when they exist', () => {
            expect(comp.getArtists()).toEqual(['a', 'b', 'c']);
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
