'use strict';

describe('emUtils', function() {

    var emUtils;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_emUtils_) {
        emUtils = _emUtils_;
    }));

    describe('isNumber', function() {
        it('returns true when given zero', function() {
            expect(emUtils.isNumber(0)).toEqual(true);
            expect(emUtils.isNumber(-0)).toEqual(true);
            expect(emUtils.isNumber(0.0)).toEqual(true);
            expect(emUtils.isNumber(-0.0)).toEqual(true);
            expect(emUtils.isNumber('0')).toEqual(true);
            expect(emUtils.isNumber('-0')).toEqual(true);
            expect(emUtils.isNumber('0.0')).toEqual(true);
            expect(emUtils.isNumber('-0.0')).toEqual(true);
        });

        it('returns true when given an integer', function() {
            expect(emUtils.isNumber(7)).toEqual(true);
            expect(emUtils.isNumber(-7)).toEqual(true);
            expect(emUtils.isNumber('7')).toEqual(true);
            expect(emUtils.isNumber('-7')).toEqual(true);
        });

        it('returns true when given a floating point value', function() {
            expect(emUtils.isNumber(11.17)).toEqual(true);
            expect(emUtils.isNumber(-11.17)).toEqual(true);
            expect(emUtils.isNumber('11.17')).toEqual(true);
            expect(emUtils.isNumber('-11.17')).toEqual(true);
        });

        it('returns false when given a string that does not contain a number', function() {
            expect(emUtils.isNumber('hello 0 world')).toEqual(false);
        });

        it('returns false when given an empty string', function() {
            expect(emUtils.isNumber('')).toEqual(false);
        });

        it('returns false when given null', function() {
            expect(emUtils.isNumber(null)).toEqual(false);
        });

        it('returns false when given undefined', function() {
            expect(emUtils.isNumber(undefined)).toEqual(false);
        });
    });

    describe('hertzToString', function() {
        it('converts a value equal to zero correctly', function() {
            expect(emUtils.hertzToString(0)).toEqual('0 Hz');
        });

        it('converts a value less than 1000 correctly', function() {
            expect(emUtils.hertzToString(123)).toEqual('123 Hz');
        });

        it('converts a value equal to 1000 correctly', function() {
            expect(emUtils.hertzToString(1000)).toEqual('1 kHz');
        });

        it('converts a value greater than 1000 correctly', function() {
            expect(emUtils.hertzToString(1800)).toEqual('1.8 kHz');
        });

        it('returns null when given a null value', function() {
            expect(emUtils.hertzToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', function() {
            expect(emUtils.hertzToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', function() {
            expect(emUtils.hertzToString('hello, world')).toBeNull();
            expect(emUtils.hertzToString('123 Hz')).toBeNull();
        });
    });

    describe('millisecondsToString', function() {
        it('converts a value equal to zero seconds', function() {
            expect(emUtils.millisecondsToString(0)).toEqual('0:00');
        });

        it('converts a value less than one second correctly', function() {
            expect(emUtils.millisecondsToString(867)).toEqual('0:01');
            expect(emUtils.millisecondsToString('867')).toEqual('0:01');
        });

        it('converts a value equal to one second', function() {
            expect(emUtils.millisecondsToString(1000)).toEqual('0:01');
        });

        it('converts a value less than ten seconds correctly', function() {
            expect(emUtils.millisecondsToString(3867)).toEqual('0:04');
        });

        it('converts a value equal to ten seconds correctly', function() {
            expect(emUtils.millisecondsToString(10000)).toEqual('0:10');
        });

        it('converts a value less than one minute correctly', function() {
            expect(emUtils.millisecondsToString(20867)).toEqual('0:21');
        });

        it('converts a value equal to one minute correctly', function() {
            expect(emUtils.millisecondsToString(60000)).toEqual('1:00');
        });

        it('converts a value greater than one minute correctly', function() {
            expect(emUtils.millisecondsToString(91000)).toEqual('1:31');
        });

        it('returns null when given a null value', function() {
            expect(emUtils.millisecondsToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', function() {
            expect(emUtils.millisecondsToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', function() {
            expect(emUtils.millisecondsToString('hello world')).toBeNull();
        });
    });
});