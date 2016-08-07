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

import {PlaylistElemMenuButtonComponent} from
    'components/playlists/playlist-elem-menu-button/playlist-elem-menu-button.component';

describe(PlaylistElemMenuButtonComponent.name, () => {
    let comp;
    let _playlists;
    let _queues;
    
    beforeEach(() => {
        _playlists = {
            delete() {}
        };
        
        _queues = {
            addLast() {}
        };
        
        comp = new PlaylistElemMenuButtonComponent(_playlists, _queues);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(PlaylistElemMenuButtonComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(PlaylistElemMenuButtonComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('isForFullPlaylist', () => {
        beforeEach(() => {
            comp.playlist = {};
            comp.element = null;
        });
        
        it('returns true when there is a playlist and no element', () => {
            expect(comp.isForFullPlaylist()).toEqual(true);
        });
        
        it('returns false when there is no playlist', () => {
            comp.playlist = null;
            expect(comp.isForFullPlaylist()).toEqual(false);
        });
        
        it('returns false when there is an element', () => {
            comp.element = {};
            expect(comp.isForFullPlaylist()).toEqual(false);
        });
    });
    
    describe('addToQueueLast', () => {
        beforeEach(() => {
            spyOn(comp, 'getSongIDs').and.stub();
            spyOn(_queues, 'addLast').and.stub();
        });
        
        it('adds the song IDs when they exist', () => {
            comp.getSongIDs.and.returnValue([1, 2, 3]);
            comp.addToQueueLast();
            expect(_queues.addLast).toHaveBeenCalledWith([1, 2, 3]);
        });
        
        it('does not add any song IDs when none exist', () => {
            comp.getSongIDs.and.returnValue([]);
            comp.addToQueueLast();
            expect(_queues.addLast).not.toHaveBeenCalled();
        });
    });
    
    describe('getSongIDs', () => {
        beforeEach(() => {
            spyOn(comp, 'getSongIDsFromPlaylist').and.returnValue(['ids_from_playlist']);
            spyOn(comp, 'getSongIDsFromElement').and.returnValue(['ids_from_element']);
        });
        
        it('returns the song IDs from the playlist when there is a playlist but no element', () => {
            comp.playlist = {};
            comp.element = null;
            expect(comp.getSongIDs()).toEqual(['ids_from_playlist']);
        });
        
        it('returns the song IDs from the element when there is an element but no playlist', () => {
            comp.playlist = null;
            comp.element = {};
            expect(comp.getSongIDs()).toEqual(['ids_from_element']);
        });
        
        it('returns an empty array when there is no playlist or element', () => {
            comp.playlist = null;
            comp.element = null;
            expect(comp.getSongIDs()).toEqual([]);
        });
    });
    
    describe('getSongIDsFromPlaylist', () => {
        beforeEach(() => {
            comp.playlist = {
                elements: [
                    {song: {id: 7}},
                    {song: {id: 5}},
                    {song: {id: 3}}
                ]
            };
        });
        
        it("returns an array containing the IDs from each playlist element's song", () => {
            expect(comp.getSongIDsFromPlaylist()).toEqual([7, 5, 3]);
        });
    });
    
    describe('getSongIDsFromElement', () => {
        beforeEach(() => {
            comp.element = {
                song: {
                    id: 7
                }
            };
        });
        
        it("returns an array containing the element's song ID", () => {
            expect(comp.getSongIDsFromElement()).toEqual([7]);
        });
    });
    
    describe('deletePlaylist', () => {
        beforeEach(() => {
            spyOn(_playlists, 'delete').and.stub();
            comp.playlist = {id: 7};
        });
        
        it('deletes the playlist', () => {
            comp.deletePlaylist();
            expect(_playlists.delete).toHaveBeenCalledWith(7);
        });
    });
});
