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

import {HertzPipe} from 'pipes/HertzPipe';

xdescribe(HertzPipe.name, () => {
    let factory = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _emUtils = {
            hertzToString: () => {}
        };
        
        factory = new HertzFilterFactory();
    });
    
    describe('filter', () => {
        it('defines an injection ID', () => {
            expect(factory.filter.injectID).toEqual(jasmine.any(String));
        });
        
        it('defines injections', () => {
            expect(factory.filter.$inject).toEqual(jasmine.any(Array));
        });
        
        it('creates a filter function', () => {
            factory.filter(_emUtils);
            expect(factory.filter()).toEqual(jasmine.any(Function));
        });
    });
    
    describe('hertzToString', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'hertzToString').and.returnValue('converted');
        });
        
        it('forwards the conversion call to a service', () => {
            HertzFilterFactory.hertzToString(_emUtils, 123);
            expect(_emUtils.hertzToString).toHaveBeenCalledWith(123);
        });
            
        it('returns the converted value', () => {
            expect(HertzFilterFactory.hertzToString(_emUtils, 123)).toEqual('converted');
        });
    });
});
