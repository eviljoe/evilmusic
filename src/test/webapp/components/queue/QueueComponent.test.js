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

import {QueueComponent} from 'components/queue/QueueComponent';

xdescribe(QueueComponent.name, () => {
    let ctrl = null;
    let _queues = null;
    let _players = null;
    
    beforeEach(() => {
        _queues = {
            q: {},
            clear() {},

            remove() {}
        };
        _players = {
            play() {}
        };
        ctrl = new QueueController(_queues, _players);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(QueueController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(QueueController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('getQueue', () => {
        it('returns the queue', () => {
            expect(ctrl.getQueue()).toBe(_queues.q);
        });
    });
    
    describe('clear', () => {
        beforeEach(() => {
            spyOn(_queues, 'clear').and.stub();
        });
        
        it('clears the queue', () => {
            ctrl.clear();
            expect(_queues.clear).toHaveBeenCalled();
        });
    });
    
    describe('play', () => {
        beforeEach(() => {
            spyOn(_players, 'play').and.stub();
        });
        
        it('plays the song at the given index', () => {
            ctrl.play(7);
            expect(_players.play).toHaveBeenCalledWith(7);
        });
    });
    
    describe('remove', () => {
        beforeEach(() => {
            spyOn(_queues, 'remove').and.stub();
        });
        
        it('removes the song at the given index in the queue', () => {
            ctrl.remove(7);
            expect(_queues.remove).toHaveBeenCalledWith(7);
        });
    });
});
