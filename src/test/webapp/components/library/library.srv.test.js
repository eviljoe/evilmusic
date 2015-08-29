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

describe('library', function() {
    'use strict';

    var library;
    var $httpBackend;
    var defaultLibrary = { id : 7 };

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expect('GET', '/rest/library').respond(defaultLibrary);
        $httpBackend.when('GET', '/rest/queue/default').respond(defaultLibrary);
    }));

    beforeEach(inject(function(_library_) {
        library = _library_;
        $httpBackend.flush();
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe('construction', function() {
        it('loads the library', function() {
            expect(library.library.id).toEqual(defaultLibrary.id);
        });
    });

    describe('clear', function() {
        it('makes a REST call to clear the library', function() {
            $httpBackend.expect('DELETE', '/rest/library').respond({});
            $httpBackend.when('GET', '/rest/library').respond({});
            library.clear();
            $httpBackend.flush();
        });

        it('makes a REST call to load the queue', function() {
            $httpBackend.when('DELETE', '/rest/library').respond({});
            $httpBackend.expect('GET', '/rest/library').respond({});
            library.clear();
            $httpBackend.flush();
        });
    });

    describe('rebuild', function() {
        it('makes a REST call to rebuild the library', function() {
            $httpBackend.expect('POST', '/rest/library').respond({});
            $httpBackend.when('GET', '/rest/library').respond({});
            library.rebuild();
            $httpBackend.flush();
        });

        it('makes a REST call to load the queue', function() {
            $httpBackend.when('POST', '/rest/library').respond({});
            $httpBackend.expect('GET', '/rest/library').respond({});
            library.rebuild();
            $httpBackend.flush();
        });
    });
});
