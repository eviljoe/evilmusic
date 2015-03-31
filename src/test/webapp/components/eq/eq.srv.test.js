/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

describe('eq', function() {
    'use strict';
    
    var $httpBackend;
    var eq;
    var defaultEQ = {};
    var mockContext = null;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expect('GET', '/rest/eq/current').respond(defaultEQ);

        mockContext = {
            createBiquadFilter : function() {}
        };

        spyOn(mockContext, 'createBiquadFilter').and.callFake(function() {
            return {
                frequency : {},
                Q : {},
                gain : {}
            };
        });
    }));

    beforeEach(inject(function(_eq_) {
        eq = _eq_;
        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe('construction', function() {
        it('loads the EQ', function() {
            expect(eq.eq).toEqual(defaultEQ);
        });
    });

    describe('createEQNodes', function() {
        var defaultEQNodes = [
            { id : 3, frequency : 5, q : 7, gain : 11 },
            { id : 13, frequency : 17, q : 19, gain : 23 },
            { id : 29, frequency : 31, q : 37, gain : 41 }
        ];

        it('creates a web audio node for each EQ node in the EQ', function() {
            var webAudioNodes;

            eq.eq.nodes = defaultEQNodes;
            webAudioNodes = eq.createEQNodes(mockContext);

            for(var x = 0; x < defaultEQNodes.length; x++) {
                var webAudioNode = webAudioNodes[x];
                var defaultEQNode = defaultEQNodes[x];

                expect(webAudioNode.type).toEqual('peaking');
                expect(webAudioNode.frequency.value).toEqual(defaultEQNode.frequency);
                expect(webAudioNode.Q.value).toEqual(defaultEQNode.q);
                expect(webAudioNode.gain.value).toEqual(defaultEQNode.gain);
            }
        });

        it('creates a map of web audio nodes in the service for each EQ node in the EQ ', function() {
            var webAudioNodes;

            eq.eq.nodes = defaultEQNodes;
            webAudioNodes = eq.createEQNodes(mockContext);

            for(var x = 0; x < defaultEQNodes.length; x++) {
                var defaultEQNode = defaultEQNodes[x];
                var webAudioNode = eq.webAudioNodes[defaultEQNode.id];

                expect(webAudioNode.type).toEqual('peaking');
                expect(webAudioNode.frequency.value).toEqual(defaultEQNode.frequency);
                expect(webAudioNode.Q.value).toEqual(defaultEQNode.q);
                expect(webAudioNode.gain.value).toEqual(defaultEQNode.gain);
            }
        });
    });

    describe('createEQNode', function() {

        var defaultEQNode = { id : 5, frequency : 7, q : 7, gain : 11 };

        it('creates a web audio node for the given EQ node', function() {
            var webAudioNode = eq.createEQNode(mockContext, defaultEQNode);

            expect(webAudioNode.type).toEqual('peaking');
            expect(webAudioNode.frequency.value).toEqual(defaultEQNode.frequency);
            expect(webAudioNode.Q.value).toEqual(defaultEQNode.q);
            expect(webAudioNode.gain.value).toEqual(defaultEQNode.gain);
        });

        it('returns null when given a null context', function() {
            expect(eq.createEQNode(null, defaultEQNode)).toBeNull();
        });

        it('returns null when given an undefined context', function() {
            expect(eq.createEQNode(undefined, defaultEQNode)).toBeNull();
        });

        it('returns null when given a null EQ node', function() {
            expect(eq.createEQNode(mockContext, undefined)).toBeNull();
        });

        it('returns null when given an undefined EQ node', function() {
            expect(eq.createEQNode(mockContext, undefined)).toBeNull();
        });
    });

    describe('updateNodeGain', function() {

        var defaultWebAudioNodes = null;

        beforeEach(function() {
            defaultWebAudioNodes = {};
            defaultWebAudioNodes[5] = { gain : { value : 1 } };
            defaultWebAudioNodes[7] = { gain : { value : 1 } };
            eq.webAudioNodes = defaultWebAudioNodes;
        });

        it('updates the associated web audio node\'s gain given an EQ node', function() {
            var eqNode = { id : 5, gain : 11 };

            expect(eq.updateNodeGain(eqNode)).toBe(true);
            expect(defaultWebAudioNodes[5].gain.value).toBe(11);
            expect(defaultWebAudioNodes[7].gain.value).not.toBe(11);
        });

        it('returns false and does not update anything when given an EQ node whose ID is not in use', function() {
            var eqNode = { id : 13, gain : 11 };

            expect(eq.updateNodeGain(eqNode)).toBe(false);
            expect(defaultWebAudioNodes[5].gain.value).toBe(1);
            expect(defaultWebAudioNodes[7].gain.value).toBe(1);
        });

        it('returns false and does not update anything when given a null EQ node', function() {
            expect(eq.updateNodeGain(null)).toBe(false);
            expect(defaultWebAudioNodes[5].gain.value).toBe(1);
            expect(defaultWebAudioNodes[7].gain.value).toBe(1);
        });

        it('returns false and does not update anything when given an undefined EQ node', function() {
            expect(eq.updateNodeGain(undefined)).toBe(false);
            expect(defaultWebAudioNodes[5].gain.value).toBe(1);
            expect(defaultWebAudioNodes[7].gain.value).toBe(1);
        });
    });
});
