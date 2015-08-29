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

describe('queue', function() {
    'use strict';

    var $httpBackend;
    var $http;
    var queue;
    var defaultQueue = {
        id : 10,
        elements : [
            { playIndex : -1, queueIndex : 0, song : { id : 100, title : 'test song #1' }},
            { playIndex : 0, queueIndex : 1, song : { id : 101, title : 'test song #2' }},
            { playIndex : 1, queueIndex : 2, song : { id : 102, title : 'test song #3' }}
        ]
    };

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expect('GET', '/rest/queue/default').respond(defaultQueue);
    }));

    beforeEach(inject(function(_$http_, _queue_) {
        $http = _$http_;
        queue = _queue_;

        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe('construction', function() {
        it('loads the queue', function() {
            expect(queue.q.id).toEqual(defaultQueue.id);
        });
    });

    describe('addLast', function() {
        it('makes a REST API call to add a song to the queue', function() {
            var songID = 17;

            $httpBackend.expect('PUT', '/rest/queue/' + defaultQueue.id + '/last?songIDs=' + songID).respond(undefined);
            $httpBackend.when('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.addLast(songID);
            $httpBackend.flush();
        });

        it('reloads the queue after a song is added', function() {
            var songID = 19;
            
            $httpBackend.when('PUT', '/rest/queue/' + defaultQueue.id + '/last?songIDs=' + songID).respond(undefined);
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.addLast(songID);
            $httpBackend.flush();
        });
    });

    describe('remove', function() {
        it('makes a REST API call to remove a song from the queue', function() {
            var queueIndex = 1;

            $httpBackend.expect(
                'DELETE', '/rest/queue/' + defaultQueue.id + '/queueindex/' + queueIndex).respond(undefined);
            $httpBackend.when('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.remove(queueIndex);
            $httpBackend.flush();
        });

        it('reloads the queue after a song is removed', function() {
            var queueIndex = 1;

            $httpBackend.when(
                'DELETE', '/rest/queue/' + defaultQueue.id + '/queueindex/' + queueIndex).respond(undefined);
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.remove(queueIndex);
            $httpBackend.flush();
        });
    });

    describe('clear', function() {
        it('makes a REST API call to clear the queue', function() {
            $httpBackend.expect('DELETE', '/rest/queue/' + defaultQueue.id + '/elements').respond(undefined);
            $httpBackend.when('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.clear();
            $httpBackend.flush();
        });

        it('reloads the queue after it is cleared', function() {
            $httpBackend.when('DELETE', '/rest/queue/' + defaultQueue.id + '/elements').respond(undefined);
            $httpBackend.expect('GET', '/rest/queue/' + defaultQueue.id).respond(defaultQueue);

            queue.clear();
            $httpBackend.flush();
        });
    });

    describe('getSong', function() {
        it('will return a song when given a queue index that is in use', function() {
            expect(queue.getSong(1)).toEqual(defaultQueue.elements[1].song);
        });

        it('will return null when given zero (and zero is a queue index that is in use)', function() {
            expect(queue.getSong(0)).toEqual(defaultQueue.elements[0].song);
        });

        it('will return null when given a queue index that is not in use', function() {
            expect(queue.getSong(17)).toBeNull();
        });

        it('will return null when given null', function() {
            expect(queue.getSong(null)).toBeNull();
        });

        it('will return null when given undefined', function() {
            expect(queue.getSong(undefined)).toBeNull();
        });

        it('will return null when given a value that is not a number', function() {
            expect(queue.getSong('hello world')).toBeNull();
        });
    });
});
