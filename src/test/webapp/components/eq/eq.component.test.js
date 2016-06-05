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

import {EQComponent} from 'components/eq/eq.component';

describe(EQComponent.name, () => {
    let comp = null;
    let _equalizers = null;
    
    beforeEach(() => {
        _equalizers = {
            updateNodeGain() {}
        };
        
        comp = new EQComponent(_equalizers);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(EQComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(EQComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('getEQNodes', () => {
        let node1;
        let node2;
        let node3;
        
        beforeEach(() => {
            node1 = {frequency: 1};
            node2 = {frequency: 2};
            node3 = {frequency: 3};
            _equalizers.eq = {
                nodes: [node3, node2, node1]
            };
        });
        
        it('returns null when the EQ services does not have an EQ', () => {
            _equalizers.eq = null;
            expect(comp.getEQNodes()).toBeNull();
        });
        
        it('returns the EQ nodes when they exist', () => {
            expect(comp.getEQNodes()).toContain(node3, node2, node1);
        });
        
        it('sorts the eq nodes by frequency', () => {
            expect(comp.getEQNodes()).toEqual([node1, node2, node3]);
        });
    });
    
    describe('nodeChanged', () => {
        beforeEach(() => {
            spyOn(_equalizers, 'updateNodeGain').and.stub();
        });
        
        it("updates the node's gain", () => {
            let node = {n: 'N'};
            comp.nodeChanged(node);
            expect(_equalizers.updateNodeGain).toHaveBeenCalledWith(node);
        });
    });
});
