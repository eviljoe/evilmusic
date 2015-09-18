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

import QueueResourceFactory from 'components/resources/QueueResourceFactory';

describe(QueueResourceFactory.name, () => {
    let factory = null;
    
    beforeEach(() => {
        factory = new QueueResourceFactory();
    });
    
    describe('resource', () => {
        let _resource = null;
        
        beforeEach(() => {
            _resource = jasmine.createSpy('$resource');
        });
        
        it('defines injections', () => {
            expect(factory.resource.$inject).toEqual(jasmine.any(Array));
        });
        
        it('defines an injection ID', () => {
            expect(factory.resource.injectID).toEqual(jasmine.any(String));
        });
        
        it('creates a resource', function() {
            factory.resource(_resource);
            expect(_resource).toHaveBeenCalledWith(jasmine.any(String), jasmine.any(Object), jasmine.any(Object));
        });
    });
    
    describe('emptyRequest', () => {
        it('returns undefined', () => {
            expect(QueueResourceFactory.emptyRequest()).toEqual(undefined);
        });
    });
});
