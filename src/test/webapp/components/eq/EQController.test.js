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

import EQController from 'components/eq/EQController';

describe(EQController.name, function() {
    let ctrl = null;
    let _equalizers = null;
    
    beforeEach(() => {
        _equalizers = {
            updateNodeGain: function() {}
        };
        
        ctrl = new EQController(_equalizers);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(EQController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(EQController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('getEQ', () => {
        it('returns the EQ', () => {
            _equalizers.eq = {};
            expect(ctrl.getEQ()).toBe(_equalizers.eq);
        });
    });
    
    describe('nodeChanged', () => {
        beforeEach(() => {
            spyOn(_equalizers, 'updateNodeGain').and.stub();
        });
        
        it('updates the node\'s gain', () => {
            let node = {n: 'N'};
            ctrl.nodeChanged(node);
            expect(_equalizers.updateNodeGain).toHaveBeenCalledWith(node);
        });
    });
});
