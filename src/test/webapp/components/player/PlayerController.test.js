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

import PlayerController from 'components/player/PlayerController';

describe(PlayerController.name, () => {
    let ctrl = null;
    let _players = null;
    
    beforeEach(() => {
        _players = {};
        ctrl = new PlayerController(_players);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(PlayerController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(PlayerController.injectID).toEqual(jasmine.any(String));
        });
    });
});
