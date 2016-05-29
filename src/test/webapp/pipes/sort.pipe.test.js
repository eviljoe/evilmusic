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

import {SortPipe} from 'pipes/sort.pipe';

describe(SortPipe.name, () => {
    let pipe = null;
    
    beforeEach(() => {
        pipe = new SortPipe();
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(SortPipe.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(SortPipe.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('transform', () => {
        it('returns an empty array when given undefined', () => {
            expect(pipe.transform()).toEqual([]);
        });
        
        it('returns an empty array when given null', () => {
            expect(pipe.transform(null)).toEqual([]);
        });
        
        it('returns empty array when given an empty array', () => {
            expect(pipe.transform([])).toEqual([]);
        });
        
        it('can sort strings', () => {
            expect(pipe.transform(['c', 'b', 'a'])).toEqual(['a', 'b', 'c']);
        });
        
        it('can sort numbers', () => {
            expect(pipe.transform([3, 2, 1])).toEqual([1, 2, 3]);
        });
        
        it('can sort complex objects given a field name', () => {
            let a = {name: 'a'};
            let b = {name: 'b'};
            let c = {name: 'c'};
            
            expect(pipe.transform([c, b, a], 'name')).toEqual([a, b, c]);
        });
        
        it('can sort complex objects given a function that returns the key', () => {
            let a = {name: 'a'};
            let B = {name: 'B'};
            let c = {name: 'c'};
            
            expect(pipe.transform([c, B, a], (x) => x.name.toLowerCase())).toEqual([a, B, c]);
        });
        
        it('can sort a set', () => {
            expect(pipe.transform(new Set(['a', 'b', 'c']))).toEqual(['a', 'b', 'c']);
        });
    });
});
