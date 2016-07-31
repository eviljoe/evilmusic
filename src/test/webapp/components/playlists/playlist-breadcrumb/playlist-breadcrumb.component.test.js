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

import {PlaylistBreadcrumbComponent} from 'components/playlists/playlist-breadcrumb/playlist-breadcrumb.component';

describe(PlaylistBreadcrumbComponent.name, () => {
    let comp;
    
    beforeEach(() => {
        comp = new PlaylistBreadcrumbComponent();
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(PlaylistBreadcrumbComponent.annotations).toEqual(jasmine.any(Array));
        });
    });

    describe('parameters', () => {
        it('returns an array', () => {
            expect(PlaylistBreadcrumbComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('hasPlaylist', () => {
        it('returns true if there is a playlist', () => {
            comp.playlist = {};
            expect(comp.hasPlaylist()).toEqual(true);
        });
        
        it('returns false if the playlist is falsy', () => {
            comp.playlist = null;
            expect(comp.hasPlaylist()).toEqual(false);
        });
    });
    
    describe('isSingleActive', () => {
        beforeEach(() => {
            spyOn(comp, 'hasPlaylist').and.stub();
        });
        
        it('returns true if there is a playlist', () => {
            comp.hasPlaylist.and.returnValue(true);
            expect(comp.isSingleActive()).toEqual(true);
        });
        
        it('returns false if there is no playlist', () => {
            comp.hasPlaylist.and.returnValue(false);
            expect(comp.isSingleActive()).toEqual(false);
        });
    });
    
    describe('isMultiActive', () => {
        beforeEach(() => {
            spyOn(comp, 'hasPlaylist').and.stub();
        });
        
        it('returns true if there is no playlist', () => {
            comp.hasPlaylist.and.returnValue(false);
            expect(comp.isMultiActive()).toEqual(true);
        });
        
        it('returns false if there is a playlist', () => {
            comp.hasPlaylist.and.returnValue(true);
            expect(comp.isMultiActive()).toEqual(false);
        });
    });
    
    describe('multiClicked', () => {
        beforeEach(() => {
            spyOn(comp.playlistCleared, 'emit').and.stub();
        });
        
        it('emits a playlist clear event', () => {
            comp.multiClicked();
            expect(comp.playlistCleared.emit).toHaveBeenCalled();
        });
    });
    
    describe('getPlaylistName', () => {
        it('returns null when the playlist is null', () => {
            comp.playlist = null;
            expect(comp.getPlaylistName()).toEqual(null);
        });
        
        it("returns the playlist's name when there is a playlist", () => {
            comp.playlist = {name: 'foo'};
            expect(comp.getPlaylistName()).toEqual('foo');
        });
    });
    
    describe('menuOnCreate', () => {
        beforeEach(() => {
            spyOn(comp.onCreate, 'emit').and.stub();
        });
        
        it('emits an event', () => {
            comp.menuOnCreate();
            expect(comp.onCreate.emit).toHaveBeenCalled();
        });
    });
});
