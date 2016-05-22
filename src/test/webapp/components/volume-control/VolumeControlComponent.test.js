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

import {VolumeControlComponent} from 'components/volume-control/VolumeControlComponent';

describe(VolumeControlComponent.name, () => {
    let ctrl = null;
    let _players = null;
    
    beforeEach(() => {
        _players = {
            setVolume() {}
        };
        
        ctrl = new VolumeControlComponent(_players);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(VolumeControlComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(VolumeControlComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('volumeChanged', () => {
        beforeEach(() => {
            spyOn(_players, 'setVolume').and.stub();
        });
        
        it("sets to volume to the player's volume", () => {
            _players.volume = 7;
            ctrl.volumeChanged();
            expect(_players.setVolume).toHaveBeenCalledWith(7);
        });
    });
});
