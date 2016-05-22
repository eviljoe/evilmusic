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

describe(QueueComponent.name, () => {
    let comp = null;
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
        
        comp = new QueueComponent(_players, _queues);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(QueueComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(QueueComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('clear', () => {
        beforeEach(() => {
            spyOn(_queues, 'clear').and.stub();
        });
        
        it('clears the queue', () => {
            comp.clear();
            expect(_queues.clear).toHaveBeenCalled();
        });
    });
    
    describe('play', () => {
        beforeEach(() => {
            spyOn(_players, 'play').and.stub();
        });
        
        it('plays the song at the given index', () => {
            comp.play(7);
            expect(_players.play).toHaveBeenCalledWith(7);
        });
    });
    
    describe('remove', () => {
        beforeEach(() => {
            spyOn(_queues, 'remove').and.stub();
        });
        
        it('removes the song at the given index in the queue', () => {
            comp.remove(7);
            expect(_queues.remove).toHaveBeenCalledWith(7);
        });
    });
    
    describe('getQueueElements', () => {
        it('returns null if the queue service has no queue', () => {
            _queues.q = null;
            expect(comp.getQueueElements()).toEqual(null);
        });
        
        it("returns the queue's elements if the queue service has a queue", () => {
            _queues.q = {
                elements: ['foo', 'bar']
            };
            expect(comp.getQueueElements()).toEqual(['foo', 'bar']);
        });
    });
    
    describe('isElementPlaying', () => {
        it('returns false if the given element is falsy', () => {
            expect(comp.isElementPlaying()).toEqual(false);
        });
        
        it("returns false if the given element's play index is not zero", () => {
            expect(comp.isElementPlaying({playIndex: 1})).toEqual(false);
        });
        
        it("returns true if the given element's play index is zero", () => {
            expect(comp.isElementPlaying({playIndex: 0})).toEqual(true);
        });
    });
});
