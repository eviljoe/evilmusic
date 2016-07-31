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

import {PlaylistsMenuButtonComponent} from 'components/playlists/playlists-menu-button/playlists-menu-button.component';

describe(PlaylistsMenuButtonComponent.name, () => {
    let comp;
    
    beforeEach(() => {
        comp = new PlaylistsMenuButtonComponent();
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(PlaylistsMenuButtonComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(PlaylistsMenuButtonComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('createClicked', () => {
        beforeEach(() => {
            spyOn(comp.onCreate, 'emit').and.stub();
        });
        
        it('emits an event', () => {
            comp.createClicked();
            expect(comp.onCreate.emit).toHaveBeenCalled();
        });
    });
});
