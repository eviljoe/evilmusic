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

xdescribe(LibraryArtistsComponent.name, () => {
    let ctrl = null;
    let _scope = null;
    let _libraries = null;
    
    beforeEach(() => {
        _scope = {};
        _libraries = {
            getArtists() {}
        };
        
        ctrl = new LibraryArtistsController(_scope, _libraries);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(LibraryArtistsController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(LibraryArtistsController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('getArtists', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getArtists').and.stub();
        });
        
        it('gets the artists', () => {
            _scope.artist = 'artist_1';
            ctrl.getArtists();
            expect(_libraries.getArtists).toHaveBeenCalled();
        });
        
        it('returns the artists', () => {
            _libraries.getArtists.and.returnValue(['a', 'b']);
            expect(ctrl.getArtists()).toEqual(['a', 'b']);
        });
    });
    
    describe('artistChanged', () => {
        it('sets the given artist as the scope\'s artist', () => {
            _scope.artist = null;
            ctrl.artistChanged('new_artist');
            expect(_scope.artist).toEqual('new_artist');
        });
    });
});
