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

import LibraryController from 'components/library/LibraryController';

describe(LibraryController.name, () => {
    let ctrl = null;
    let _libraries = null;
    let _queues = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _libraries = {};
        _queues = {addLast: () => {}};
        _emUtils = {};
        
        ctrl = new LibraryController(_libraries, _queues, _emUtils);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(LibraryController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(LibraryController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('addLast', () => {
        it('adds the song with the given ID to the end of the queue', () => {
            spyOn(_queues, 'addLast');
            ctrl.addLast(17);
            expect(_queues.addLast).toHaveBeenCalledWith(17);
        });
    });
});
