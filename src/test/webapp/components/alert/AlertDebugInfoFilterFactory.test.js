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

import AlertDebugInfoFilterFactory from 'components/alert/AlertDebugInfoFilterFactory';

xdescribe(AlertDebugInfoFilterFactory.name, () => {
    let factory = null;
    let _window = null;
    
    beforeEach(() => {
        _window = {
            JSON: {
                stringify() {}
            }
        };
        factory = new AlertDebugInfoFilterFactory();
    });
    
    describe('filter', () => {
        it('defines an injection ID', () => {
            expect(factory.filter.injectID).toEqual(jasmine.any(String));
        });
        
        it('defines injections', () => {
            expect(factory.filter.$inject).toEqual(jasmine.any(Array));
        });
        
        it('creates a filter function', () => {
            factory.filter();
            expect(factory.filter()).toEqual(jasmine.any(Function));
        });
    });
    
    describe('jsonToString', () => {
        beforeEach(() => {
            spyOn(_window.JSON, 'stringify').and.stub();
        });
        
        it('stringifies the JSON', () => {
            AlertDebugInfoFilterFactory.jsonToString(_window, {});
            expect(_window.JSON.stringify).toHaveBeenCalled();
        });
            
        it('returns the converted value', () => {
            _window.JSON.stringify.and.returnValue('string');
            expect(AlertDebugInfoFilterFactory.jsonToString(_window, {})).toEqual('string');
        });
    });
});
