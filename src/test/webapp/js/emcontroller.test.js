'use strict';

describe('EMLibraryController', function() {

    var $controller;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;

    }));

    describe('hertzToString', function() {
        var scope;

        beforeEach(inject(function(_$http_) {
            scope = {};
            $controller('EMLibraryController', {
                $scope : scope,
                $http : _$http_,
            });
        }));

        it('converts a value less than 1000 correctly', function() {
            expect(scope.hertzToString(123)).toEqual('123 Hz');
        });

        it('converts a value greater than 1000 correctly', function() {
            expect(scope.hertzToString(1800)).toEqual('1.8 kHz');
        });

        it('returns null when given a null value', function() {
            expect(scope.hertzToString(null)).toEqual(null);
        });

        it('returns null when given an undefined value', function() {
            expect(scope.hertzToString(undefined)).toEqual(null);
        });

        it('returns null when given a value that is not a number', function() {
            expect(scope.hertzToString('hello, world')).toEqual(null);
            expect(scope.hertzToString('123 Hz')).toEqual(null);
        });
    });
});