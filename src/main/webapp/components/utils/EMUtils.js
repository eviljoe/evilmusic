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

export default class EMUtils {
    
    constructor() {
        this.HERTZ_IN_KILOHERTZ = 1000;
        this.MILLISECONDS_IN_SECOND = 1000;
        this.MILLISECONDS_IN_TEN_SECONDS = 10000;
        this.MILLISECONDS_IN_MINUTE = 60000;
    }
    
    static get injectID() {
        return 'emUtils';
    }
    
    /**
     * Determines if the given value is a number.  The value is considered to be a number if it meets the following
     * criteria:
     *   * It is not null
     *   * It is not undefined
     *   * It is not an empty string
     *   * It is not NaN (according to the isNaN(...) function)
     *
     * @param {Any} val The value to be checked to see if it is a number.
     *
     * @return {boolean} Returns true if the value is a number.  Returns false otherwise.
     *
     * @see isNaN(...)
     */
    isNumber(val) {
        return val !== null && val !== undefined && val !== '' && !isNaN(val);
    }

    /**
     * Converts the given Hertz magnitude to a string with units.  The following table describes the string that will be
     * returned:
     *
     *    HERTZ | OUTPUT                 | EXAMPLE
     * ---------+------------------------+--------
     *  < 1,000 | <magnitude> Hz         | 128 Hz
     * >= 1,000 | <magnitude / 1000> kHz | 1.8 kHz
     *
     * @param {number} hertz The Hertz magnitude.  If NaN, null will be returned.
     *
     * @return {string} Returns the string representation of the Hertz magnitude.  If the magnitude is null, undefined,
     *         or NaN, null will be returned.\
     *
     * @see isNumber(...)
     */
    hertzToString(hertz) {
        let str = null;

        if(this.isNumber(hertz)) {
            str = hertz < 0 ? '-' : '';

            hertz = Math.abs(hertz);

            if(hertz < this.HERTZ_IN_KILOHERTZ) {
                str += `${hertz} Hz`;
            } else {
                str += `${hertz / this.HERTZ_IN_KILOHERTZ} kHz`;
            }
        }

        return str;
    }

    /**
     * Converts the given seconds magnitude to a string with units.  The following table describes the string that will
     * be returned:
     *
     * SECONDS | OUTPUT              | EXAMPLE
     * --------+---------------------+------
     *    < 60 | 0:<seconds>         | 0:05
     *   >= 60 | <minutes>:<seconds> | 3:59
     *
     * In the above format, <seconds> will always be at least two digits.  If it is representing a value less than ten,
     * a leading zero will be added.  For example, "07" will be used to represent seven.
     *
     * @param {number} millis The seconds magnitude.  If NaN, null will be returned.
     *
     * @return {string} Returns the string representation of the seconds magnitude.  If the magnitude is null,
     *         undefined, or NaN, null will be returned.
     *
     * @see isNumber(...)
     */
    millisecondsToString(millis) {
        let str = null;

        if(this.isNumber(millis)) {
            str = millis < 0 ? '-' : '';
            
            millis = Math.abs(millis);
            
            if(millis === 0) {
                str = '0:00';
            } else if(millis <= this.MILLISECONDS_IN_SECOND) {
                str += '0:01';
            } else if(millis < this.MILLISECONDS_IN_MINUTE) {
                str += millis < this.MILLISECONDS_IN_TEN_SECONDS ? '0:0' : '0:';
                str += Math.ceil(millis / this.MILLISECONDS_IN_SECOND);
            } else {
                let minutes = Math.floor(millis / this.MILLISECONDS_IN_MINUTE);
                
                millis = millis % this.MILLISECONDS_IN_MINUTE;
                str += minutes;
                str += millis < this.MILLISECONDS_IN_TEN_SECONDS ? ':0' : ':';
                str += Math.ceil(millis / this.MILLISECONDS_IN_SECOND);
            }
        }

        return str;
    }
}
