'use strict';

describe('emQueue', function() {

    var $httpBackend;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $httpBackend.when('GET', '/rest/queue/current').respond({});
    }));

    describe('Directive', function() {

    });

    describe('EMQueueController', function() {

        var $controller;
        var ctrl;

        beforeEach(inject(function(_$controller_, queue) {
            $controller = _$controller_;
            ctrl = $controller('EMQueueController', {
                queue : queue
            });
        }));

        describe('construction', function() {
            it('loads the queue', function() {
                expect(ctrl.queue).toBeDefined();
            });
        });
    });
});