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
});