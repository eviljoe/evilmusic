'use strict';

describe('EMLibraryController', function() {

    var ctrlName = 'EMLibraryController';
    var $controller;
    var $httpBackend;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;

    }));

    beforeEach(inject(function(_$httpBackend_){
        $httpBackend = _$httpBackend_;
        $httpBackend.when('GET', '/rest/queue/current').respond({});
        $httpBackend.when('GET', '/rest/eq/current').respond({});
    }));
});