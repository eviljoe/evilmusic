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

import {MinutesPipe} from 'pipes/minutes.pipe';

describe(MinutesPipe.name, () => {
    let pipe = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _emUtils = {
            millisecondsToString() {}
        };
        
        pipe = new MinutesPipe(_emUtils);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(MinutesPipe.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(MinutesPipe.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('transform', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'millisecondsToString').and.returnValue('converted');
        });
        
        it('forwards the conversion call to a service', () => {
            pipe.transform(123);
            expect(_emUtils.millisecondsToString).toHaveBeenCalledWith(123);
        });
            
        it('returns the converted value', () => {
            expect(pipe.transform(123)).toEqual('converted');
        });
    });
});
