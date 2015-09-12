/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2015 Joe Falascino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import EMUtils from 'components/utils/EMUtils';

describe('emUtils', () => {
    let emUtils = null;

    beforeEach(() => {
        emUtils = new EMUtils();
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(EMUtils.injectID).toEqual(jasmine.any(String));
        });
    });

    describe('isNumber', () => {
        it('returns true when given zero', () => {
            expect(emUtils.isNumber(0)).toEqual(true);
            expect(emUtils.isNumber(-0)).toEqual(true);
            expect(emUtils.isNumber(0.0)).toEqual(true);
            expect(emUtils.isNumber(-0.0)).toEqual(true);
            expect(emUtils.isNumber('0')).toEqual(true);
            expect(emUtils.isNumber('-0')).toEqual(true);
            expect(emUtils.isNumber('0.0')).toEqual(true);
            expect(emUtils.isNumber('-0.0')).toEqual(true);
        });

        it('returns true when given an integer', () => {
            expect(emUtils.isNumber(7)).toEqual(true);
            expect(emUtils.isNumber(-7)).toEqual(true);
            expect(emUtils.isNumber('7')).toEqual(true);
            expect(emUtils.isNumber('-7')).toEqual(true);
        });

        it('returns true when given a floating point value', () => {
            expect(emUtils.isNumber(11.17)).toEqual(true);
            expect(emUtils.isNumber(-11.17)).toEqual(true);
            expect(emUtils.isNumber('11.17')).toEqual(true);
            expect(emUtils.isNumber('-11.17')).toEqual(true);
        });

        it('returns false when given a string that does not contain a number', () => {
            expect(emUtils.isNumber('hello 0 world')).toEqual(false);
        });

        it('returns false when given an empty string', () => {
            expect(emUtils.isNumber('')).toEqual(false);
        });

        it('returns false when given null', () => {
            expect(emUtils.isNumber(null)).toEqual(false);
        });

        it('returns false when given undefined', () => {
            expect(emUtils.isNumber(undefined)).toEqual(false);
        });
    });

    describe('hertzToString', () => {
        it('converts a value equal to zero correctly', () => {
            expect(emUtils.hertzToString(0)).toEqual('0 Hz');
        });

        it('converts a value less than 1000 correctly', () => {
            expect(emUtils.hertzToString(123)).toEqual('123 Hz');
        });

        it('converts a value equal to 1000 correctly', () => {
            expect(emUtils.hertzToString(1000)).toEqual('1 kHz');
        });

        it('converts a value greater than 1000 correctly', () => {
            expect(emUtils.hertzToString(1800)).toEqual('1.8 kHz');
        });

        it('returns null when given a null value', () => {
            expect(emUtils.hertzToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', () => {
            expect(emUtils.hertzToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', () => {
            expect(emUtils.hertzToString('hello, world')).toBeNull();
            expect(emUtils.hertzToString('123 Hz')).toBeNull();
        });
    });

    describe('millisecondsToString', () => {
        it('converts a value equal to zero seconds', () => {
            expect(emUtils.millisecondsToString(0)).toEqual('0:00');
        });

        it('converts a value less than one second correctly', () => {
            expect(emUtils.millisecondsToString(867)).toEqual('0:01');
            expect(emUtils.millisecondsToString('867')).toEqual('0:01');
        });

        it('converts a value equal to one second', () => {
            expect(emUtils.millisecondsToString(1000)).toEqual('0:01');
        });

        it('converts a value less than ten seconds correctly', () => {
            expect(emUtils.millisecondsToString(3867)).toEqual('0:04');
        });

        it('converts a value equal to ten seconds correctly', () => {
            expect(emUtils.millisecondsToString(10000)).toEqual('0:10');
        });

        it('converts a value less than one minute correctly', () => {
            expect(emUtils.millisecondsToString(20867)).toEqual('0:21');
        });

        it('converts a value equal to one minute correctly', () => {
            expect(emUtils.millisecondsToString(60000)).toEqual('1:00');
        });

        it('converts a value greater than one minute correctly', () => {
            expect(emUtils.millisecondsToString(91000)).toEqual('1:31');
        });

        it('returns null when given a null value', () => {
            expect(emUtils.millisecondsToString(null)).toBeNull();
        });

        it('returns null when given an undefined value', () => {
            expect(emUtils.millisecondsToString(undefined)).toBeNull();
        });

        it('returns null when given a value that is not a number', () => {
            expect(emUtils.millisecondsToString('hello world')).toBeNull();
        });
    });
});
