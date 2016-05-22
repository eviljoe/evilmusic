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

import {LibraryComponent} from 'components/library/LibraryComponent';

describe(LibraryComponent.name, () => {
    let comp = null;
    let _emUtils = null;
    let _libraries = null;
    let _queues = null;
    
    beforeEach(() => {
        _libraries = {
            getSongsForAlbum() {}
        };
        _queues = {addLast: () => {}};
        _emUtils = {};
        
        comp = new LibraryComponent(_emUtils, _libraries, _queues);
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
        
        it('sorts the songs when they exist', () => {
            expect(comp.getSongs()).toEqual([song1, song2, song3]);
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
