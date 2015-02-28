describe('library', function() {
    'use strict';

    var library;
    var $httpBackend;
    var defaultLibrary = { id : 7 };

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expect('GET', '/rest/library').respond(defaultLibrary);
        $httpBackend.when('GET', '/rest/queue/current').respond(defaultLibrary);
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
