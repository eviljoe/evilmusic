'use strict';

describe('EMLibraryController', function() {

    var ctrlName = 'EMLibraryController';
    var $controller;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;

    }));

    describe('hertzToString', function() {
        var scope;

        beforeEach(inject(function($httpBackend, $http) {
            scope = {};

            $httpBackend.when('GET', '/rest/config/volume').respond(1.0);
            $httpBackend.when('GET', '/rest/eq/current').respond({});
            $httpBackend.when('GET', '/rest/queue/current').respond({});
            $httpBackend.when('GET', '/rest/library').respond({});

            $controller(ctrlName, {
                $scope : scope,
                $http : $http,
            });
        }));

        it('converts a value equal to zero correctly', function() {
            expect(scope.hertzToString(0)).toEqual('0 Hz');
        });

        it('converts a value less than 1000 correctly', function() {
            expect(scope.hertzToString(123)).toEqual('123 Hz');
        });

        it('converts a value equal to 1000 correctly', function() {
            expect(scope.hertzToString(1000)).toEqual('1 kHz');
        });

        it('converts a value greater than 1000 correctly', function() {
            expect(scope.hertzToString(1800)).toEqual('1.8 kHz');
        });

        it('returns null when given a null value', function() {
            expect(scope.hertzToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', function() {
            expect(scope.hertzToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', function() {
            expect(scope.hertzToString('hello, world')).toBeNull();
            expect(scope.hertzToString('123 Hz')).toBeNull();
        });
    });

    describe('millisecondsToString', function() {
        var scope;

        beforeEach(inject(function($httpBackend, $http) {
            scope = {};

            $httpBackend.when('GET', '/rest/config/volume').respond(1.0);
            $httpBackend.when('GET', '/rest/eq/current').respond({});
            $httpBackend.when('GET', '/rest/queue/current').respond({});
            $httpBackend.when('GET', '/rest/library').respond({});

            $controller(ctrlName, {
                $scope : scope,
                $http : $http,
            });
        }));

        it('converts a value equal to zero seconds', function() {
            expect(scope.millisecondsToString(0)).toEqual('0:00');
        });

        it('converts a value less than one second correctly', function() {
            expect(scope.millisecondsToString(867)).toEqual('0:01');
            expect(scope.millisecondsToString('867')).toEqual('0:01');
        });

        it('converts a value equal to one second', function() {
            expect(scope.millisecondsToString(1000)).toEqual('0:01');
        });

        it('converts a value less than ten seconds correctly', function() {
            expect(scope.millisecondsToString(3867)).toEqual('0:04');
        });

        it('converts a value equal to ten seconds correctly', function() {
            expect(scope.millisecondsToString(10000)).toEqual('0:10');
        });

        it('converts a value less than one minute correctly', function() {
            expect(scope.millisecondsToString(20867)).toEqual('0:21');
        });

        it('converts a value equal to one minute correctly', function() {
            expect(scope.millisecondsToString(60000)).toEqual('1:00');
        });

        it('converts a value greater than one minute correctly', function() {
            expect(scope.millisecondsToString(91000)).toEqual('1:31');
        });

        it('returns null when given a null value', function() {
            expect(scope.millisecondsToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', function() {
            expect(scope.millisecondsToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', function() {
            expect(scope.millisecondsToString('foo bar')).toBeNull();
        });
    });
});