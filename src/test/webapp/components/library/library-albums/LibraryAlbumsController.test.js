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

import LibraryAlbumsController from 'components/library/library-albums/LibraryAlbumsController';

xdescribe(LibraryAlbumsController.name, () => {
    let ctrl = null;
    let _scope = null;
    let _libraries = null;
    
    beforeEach(() => {
        _scope = {};
        _libraries = {
            getAlbumsForArtist() {}
        };
        
        ctrl = new LibraryAlbumsController(_scope, _libraries);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(LibraryAlbumsController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(LibraryAlbumsController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('getAlbums', () => {
        beforeEach(() => {
            spyOn(_libraries, 'getAlbumsForArtist').and.stub();
        });
        
        it('gets the albums for the scope\'s current artist', () => {
            _scope.artist = 'artist_1';
            ctrl.getAlbums();
            expect(_libraries.getAlbumsForArtist).toHaveBeenCalledWith(_scope.artist);
        });
        
        it('returns the albums', () => {
            _libraries.getAlbumsForArtist.and.returnValue(['a', 'b']);
            expect(ctrl.getAlbums()).toEqual(['a', 'b']);
        });
    });
    
    describe('albumChanged', () => {
        it('sets the given album as the scope\'s album', () => {
            _scope.album = null;
            ctrl.albumChanged('new_album');
            expect(_scope.album).toEqual('new_album');
        });
    });
});
