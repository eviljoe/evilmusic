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

import {AbstractClass} from 'utils/AbstractClass';

describe(AbstractClass.name, () => {
    let abstractClass = null;
    
    beforeEach(() => {
        abstractClass = new AbstractClass();
    });
    
    describe('_initAbstractClass', () => {
        beforeEach(() => {
            spyOn(abstractClass, '_checkForAbstractFunctions').and.stub();
            spyOn(abstractClass, '_checkForAbstractFunction').and.stub();
        });
    
        it('checks for each of the given function names when given an array', () => {
            let names = ['a', 'b'];
            abstractClass._initAbstractClass(names);
            expect(abstractClass._checkForAbstractFunctions).toHaveBeenCalledWith(names);
        });
    
        it('checks for each of the given name when given a string', () => {
            let name = 'a';
            abstractClass._initAbstractClass(name);
            expect(abstractClass._checkForAbstractFunction).toHaveBeenCalledWith(name);
        });
    
        it('does not check for any functions when given undefined', () => {
            abstractClass._initAbstractClass();
            expect(abstractClass._checkForAbstractFunction).not.toHaveBeenCalled();
            expect(abstractClass._checkForAbstractFunctions).not.toHaveBeenCalled();
        });
    
        it('does not check for any functions when given null', () => {
            abstractClass._initAbstractClass(null);
            expect(abstractClass._checkForAbstractFunction).not.toHaveBeenCalled();
            expect(abstractClass._checkForAbstractFunctions).not.toHaveBeenCalled();
        });
    });
    
    describe('_checkForAbstractFunction', () => {
        it('throws an error if the abstract class does not have a function with the given name', () => {
            expect(() => abstractClass._checkForAbstractFunction('doesNotExist')).toThrow();
        });
        
        it('throws an error if the abstract class has a field with the given name that is not a function', () => {
            abstractClass.variable = 'var';
            expect(() => abstractClass._checkForAbstractFunction('variable')).toThrow();
        });
        
        it('does not throw an error if the  abstract class has a function with the given name', () => {
            abstractClass.fn = () => {};
            
            expect(() => abstractClass._checkForAbstractFunction('fn')).not.toThrow();
        });
    });
    
    describe('_checkForAbstractFunctions', () => {
        beforeEach(() => {
            spyOn(abstractClass, '_checkForAbstractFunction').and.stub();
        });
        
        it('does not check for any functions when given undefined', () => {
            abstractClass._checkForAbstractFunctions();
            expect(abstractClass._checkForAbstractFunction).not.toHaveBeenCalled();
        });
        
        it('does not check for any functions when given null', () => {
            abstractClass._checkForAbstractFunctions(null);
            expect(abstractClass._checkForAbstractFunction).not.toHaveBeenCalled();
        });
        
        it('does not check for any functions when given an empty array', () => {
            abstractClass._checkForAbstractFunctions([]);
            expect(abstractClass._checkForAbstractFunction).not.toHaveBeenCalled();
        });
        
        it('checks for each of the given function names', () => {
            abstractClass._checkForAbstractFunctions(['a', 'b']);
            expect(abstractClass._checkForAbstractFunction.calls.argsFor(0)).toEqual(['a']);
            expect(abstractClass._checkForAbstractFunction.calls.argsFor(1)).toEqual(['b']);
        });
    });
});
