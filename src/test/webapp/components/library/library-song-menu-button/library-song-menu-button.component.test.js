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

import {LibrarySongMenuButtonComponent} from
    'components/library/library-song-menu-button/library-song-menu-button.component';

describe(LibrarySongMenuButtonComponent.name, () => {
    let comp;
    let _changeDetector;
    let _playlists;
    let _queues;
    
    beforeEach(() => {
        _changeDetector = {
            detectChanges() {}
        };
        
        _playlists = {
            playlistsChanges: Observable.create(() => {})
        };
        
        _queues = {
            addLast() {}
        };
        
        comp = new LibrarySongMenuButtonComponent(_changeDetector, _playlists, _queues);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(LibrarySongMenuButtonComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(LibrarySongMenuButtonComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        let playlistsObserver;
        
        beforeEach(() => {
            _playlists.playlistsChanges = Observable.create((observer) => playlistsObserver = observer);
            spyOn(comp, '_playlistsChanged').and.stub();
        });
        
        it('reacts to playlist changes', () => {
            comp.init();
            
            playlistsObserver.next();
            playlistsObserver.complete();
            
            expect(comp._playlistsChanged).toHaveBeenCalled();
        });
    });
    
    describe('_playlistsChanged', () => {
        beforeEach(() => {
            spyOn(_changeDetector, 'detectChanges').and.stub();
        });
        
        it('runs the change detection', () => {
            comp._playlistsChanged();
            expect(_changeDetector.detectChanges).toHaveBeenCalled();
        });
    });
    
    describe('getPlaylists', () => {
        it('returns the playlists', () => {
            _playlists.playlists = [{id: 1}, {id: 2}, {id: 3}];
            expect(comp.getPlaylists()).toEqual([{id: 1}, {id: 2}, {id: 3}]);
        });
    });
    
    describe('addToQueueLast', () => {
        beforeEach(() => {
            comp.songs = new Set([
                {id: 13, trackNumber: 3},
                {id: 12, trackNumber: 2},
                {id: 11, trackNumber: 1}
            ]);
            spyOn(_queues, 'addLast').and.stub();
        });
        
        it('adds the ID for each song sorted by track number', () => {
            comp.addToQueueLast();
            expect(_queues.addLast).toHaveBeenCalledWith([11, 12, 13]);
        });
    });
    
    describe('addToPlaylistLast', () => {
        // JOE todo
    });
});
