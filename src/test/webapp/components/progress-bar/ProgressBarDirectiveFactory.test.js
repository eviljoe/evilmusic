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

import ProgressBarDirectiveFactory from 'components/progress-bar/ProgressBarDirectiveFactory';

describe(ProgressBarDirectiveFactory.name, () => {
    let factory = null;
    
    beforeEach(() => {
        factory = new ProgressBarDirectiveFactory();
    });
    
    describe('directive', () => {
        it('defines an injection ID', () => {
            expect(factory.directive.injectID).toEqual(jasmine.any(String));
        });
        
        it('returns a directive configuration object', function() {
            expect(factory.directive()).toEqual(jasmine.any(Object));
        });
    });
});