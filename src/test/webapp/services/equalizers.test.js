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

import {Observable} from 'rxjs';
import {Equalizers} from 'services/equalizers';

describe(Equalizers.name, () => {
    let equalizers = null;
    let _alerts = null;
    let _equalizerCalls = null;
    
    beforeEach(() => {
        _alerts = {
            error() {}
        };
        _equalizerCalls = {
            get() {
                return Observable.create((observer) => {
                    observer.next({});
                    observer.complete();
                });
            },

            save() {}
        };
        
        equalizers = new Equalizers(_alerts, _equalizerCalls);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Equalizers.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Equalizers.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('init', () => {
        beforeEach(() => {
            spyOn(equalizers, 'load').and.stub();
        });
        
        it('sets the equalizers instance', () => {
            Equalizers.instance = null;
            equalizers.init();
            expect(Equalizers.instance).toEqual(equalizers);
        });
        
        it('loads a new equalizer', () => {
            equalizers.init();
            expect(equalizers.load).toHaveBeenCalledWith(true);
        });
    });
    
    describe('load', () => {
        let getObserver;
        
        beforeEach(() => {
            spyOn(equalizers, '_loaded').and.stub();
            spyOn(_equalizerCalls, 'get').and.returnValue(Observable.create((observer) => getObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            equalizers.loading = null;
            equalizers.load();
            expect(equalizers.loading).toEqual(true);
        });
        
        it('loads the default equalizer when given true', () => {
            equalizers.load(true);
            expect(_equalizerCalls.get).toHaveBeenCalledWith('default');
        });
        
        it('reloads the current equalizer given false', () => {
            equalizers.eq.id = 7;
            equalizers.load(false);
            expect(_equalizerCalls.get).toHaveBeenCalledWith(7);
        });
        
        it('reloads the current equalizer given nothing', () => {
            equalizers.eq.id = 13;
            equalizers.load();
            expect(_equalizerCalls.get).toHaveBeenCalledWith(13);
        });
        
        it('reacts if it loads successfully', () => {
            equalizers.load(true);
            
            getObserver.next({a: 'A'});
            getObserver.complete();
            
            expect(equalizers._loaded).toHaveBeenCalledWith({a: 'A'});
        });
        
        it('displays an error message if the equalizer fails to loads', () => {
            equalizers.load();
            
            getObserver.error();
            
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_loaded', () => {
        it('sets the equalizer', () => {
            equalizers._loaded({foo: 'bar'});
            expect(equalizers.eq).toEqual({foo: 'bar'});
        });
        
        it('sets the loading flag to false', () => {
            equalizers.loading = null;
            equalizers._loaded();
            expect(equalizers.loading).toEqual(false);
        });
    });
    
    describe('createEQNodes', () => {
        beforeEach(() => {
            Equalizers.instance = equalizers;
            equalizers.eq = {nodes: []};
            spyOn(equalizers, 'createEQNode').and.stub();
        });
        
        afterEach(() => {
            Equalizers.instance = null;
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
        
        it('returns null when no context is given', () => {
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
    
    describe('updateNodeGain', () => {
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
    
    describe('save', () => {
        let saveObserver;
        
        beforeEach(() => {
            spyOn(equalizers, '_saved').and.stub();
            spyOn(_equalizerCalls, 'save').and.returnValue(Observable.create((observer) => saveObserver = observer));
            spyOn(_alerts, 'error').and.stub();
        });
        
        it('sets the loading flag to true', () => {
            equalizers.loading = null;
            equalizers.save();
            expect(equalizers.loading).toEqual(true);
        });
        
        it('saves the equalizer', () => {
            equalizers.eq = {id: 17};
            equalizers.save();
            expect(_equalizerCalls.save).toHaveBeenCalledWith(17, equalizers.eq);
        });
        
        it('reacts if the EQ is loaded successful', () => {
            equalizers.save();
            
            saveObserver.next({foo: 'bar'});
            saveObserver.complete();
            
            expect(equalizers._saved).toHaveBeenCalledWith({foo: 'bar'});
        });
        
        it('displays an error message if not successful', () => {
            equalizers.save();
            saveObserver.error();
            expect(_alerts.error).toHaveBeenCalled();
        });
    });
    
    describe('_saved', () => {
        it('sets the equalizer', () => {
            equalizers._saved({foo: 'bar'});
            expect(equalizers.eq).toEqual({foo: 'bar'});
        });
        
        it('sets the loading flag to false', () => {
            equalizers.loading = null;
            equalizers._saved();
            expect(equalizers.loading).toEqual(false);
        });
    });
    
    describe('reset', () => {
        beforeEach(() => {
            spyOn(equalizers, 'updateNodeGain').and.stub();
            equalizers.eq = {
                nodes: [{gain: 7}, {gain: 11}, {gain: 13}]
            };
        });
        
        it('sets each of the nodes in the equalizer to have a gain of 0', () => {
            equalizers.reset();
            equalizers.eq.nodes.forEach((node) => {
                expect(node.gain).toEqual(0);
            });
        });
        
        it('updates the gain of the web audio node for each of the equalizer nodes', () => {
            equalizers.reset();
            equalizers.eq.nodes.forEach((node) => {
                expect(equalizers.updateNodeGain).toHaveBeenCalledWith(node);
            });
        });
    });
    
    describe('get loading', () => {
        it('returns the loading flag', () => {
            equalizers._loading = 'foo';
            expect(equalizers.loading).toEqual('foo');
        });
    });
    
    describe('set loading', () => {
        beforeEach(() => {
            equalizers.loadingChanges = jasmine.createSpyObj('loadingChanges', ['emit']);
        });
        
        it('sets the loading flag', () => {
            equalizers._loading = null;
            equalizers.loading = true;
            expect(equalizers._loading).toEqual(true);
        });
        
        it('emits a loading change', () => {
            equalizers.loading = true;
            expect(equalizers.loadingChanges.emit).toHaveBeenCalled();
        });
    });
});
