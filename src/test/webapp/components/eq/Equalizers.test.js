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

import Equalizers from 'components/eq/Equalizers';

describe(Equalizers.name, () => {
    let equalizers = null;
    let _Equalizer = null;
    let $q = null;
    let $rootScope = null;
    
    beforeEach(angular.mock.inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
        
        _Equalizer = {
            get: () => {
                return {$promise: $q.defer().promise};
            }
        };
        
        equalizers = new Equalizers(_Equalizer);
    }));
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(Equalizers.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(Equalizers.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('getInstance', () => {
        it('returns an the instance', function() {
            let inst = {a: 'A'};
            
            Equalizers.instance = inst;
            expect(Equalizers.getInstance()).toBe(inst);
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(equalizers, 'load').and.stub();
        });
        
        it('loads a new equalizer', () => {
            equalizers.init();
            expect(equalizers.load).toHaveBeenCalledWith(true);
        });
    });
    
    describe('createEQNodes', () => {
        beforeEach(() => {
            equalizers.eq = {nodes: []};
            spyOn(Equalizers, 'getInstance').and.returnValue(equalizers);
            spyOn(equalizers, 'createEQNode').and.stub();
        });
        
        afterEach(() => {
            Equalizers.getInstance.and.callThrough();
        });
        
        it('loads the singleton instance of the class', () => {
            equalizers.createEQNodes({});
            expect(Equalizers.getInstance).toHaveBeenCalled();
        });
        
        it('creates an web audio node for each EQ node', () => {
            equalizers.eq.nodes = [{}, {}, {}];
            equalizers.createEQNodes();
            expect(equalizers.createEQNode.calls.count()).toEqual(3);
        });
        
        it('sets a map of the web audio nodes on the singleton instance of the class', () => {
            let eqNode = {a: 'A'};
            
            equalizers.eq.nodes = [{id: 7}, {id: 11}];
            equalizers.createEQNode.and.returnValue(eqNode);
            equalizers.createEQNodes();
            
            expect(equalizers.webAudioNodes).toEqual({
                7: eqNode,
                11: eqNode
                
            });
        });
        
        it('returns an array of the created web audio nodes', () => {
            let eqNode = {a: 'A'};
            
            equalizers.eq.nodes = [{}, {}];
            equalizers.createEQNode.and.returnValue(eqNode);
            
            expect(equalizers.createEQNodes()).toEqual([eqNode, eqNode]);
        });
    });
    
    describe('createEQNode', () => {
        let _context = null;
        
        beforeEach(() => {
            _context = jasmine.createSpyObj('context', ['createBiquadFilter']);
            _context.createBiquadFilter.and.returnValue({
                frequency: {},
                Q: {},
                gain: {}
            });
        });
        
        it('returns null when no context is given', function() {
            expect(equalizers.createEQNode(null, {})).toBeNull();
        });
        
        it('returns null when no node is given', () => {
            expect(equalizers.createEQNode(_context, null)).toBeNull();
        });
        
        it('creates a biquad filter node using the given context', () => {
            equalizers.createEQNode(_context, {});
            expect(_context.createBiquadFilter).toHaveBeenCalled();
        });
        
        it('sets the biquad filter node\'s type to peaking', () => {
            expect(equalizers.createEQNode(_context, {}).type).toEqual('peaking');
        });
        
        it('sets the biquad filter node\'s frequency, Q, and gain from the given node', () => {
            let emNode = {frequency: 7, q: 11, gain: 13};
            let node = equalizers.createEQNode(_context, emNode);
            
            expect(node.frequency.value).toEqual(7);
            expect(node.Q.value).toEqual(11);
            expect(node.gain.value).toEqual(13);
        });
    });
    
    describe('updateGainNode', () => {
        let webAudioNodeID = null;
        let webAudioNode = null;
        
        beforeEach(() => {
            webAudioNodeID = 7;
            webAudioNode = {gain: {value: null}};
            
            equalizers.webAudioNodes = {};
            equalizers.webAudioNodes[webAudioNodeID] = webAudioNode;
        });
        
        it('does not update anything and returns false when no node is given', () => {
            expect(equalizers.updateNodeGain()).toEqual(false);
        });
        
        it('does not update anything and returns false corresponding web audio node is found', () => {
            expect(equalizers.updateNodeGain({id: webAudioNodeID + 1})).toEqual(false);
        });
        
        it('converts the given node\'s gain to a number if it is not one already', () => {
            let emNode = {gain: '123'};
            
            equalizers.updateNodeGain(emNode);
            expect(emNode.gain).toEqual(123);
        });
        
        it('updates the corresponding web audio node\'s gain to the given node\'s gain', () => {
            let emNode = {id: webAudioNodeID, gain: 123};
            
            equalizers.updateNodeGain(emNode);
            expect(webAudioNode.gain.value).toEqual(123);
        });
        
        it('returns true when a corresponding web audio node can be found', () => {
            expect(equalizers.updateNodeGain({id: webAudioNodeID})).toEqual(true);
        });
    });
});
