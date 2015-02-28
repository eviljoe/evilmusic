angular.module('EvilMusicApp')
.factory('emUtils', function() {
    'use strict';
    
    var that = this;

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
     * @return {Boolean} Returns true if the value is a number.  Returns false otherwise.
     *
     * @see isNaN(...)
     */
    that.isNumber = function(val) {
        return val !== null && val !== undefined && val !== '' && !isNaN(val);
    };

    /**
     * Converts the given Hertz magnitude to a string with units.  The following table describes the string that will be
     * returned:
     *
     *    HERTZ | OUTPUT                 | EXAMPLE
     * ---------+------------------------+--------
     *  < 1,000 | <magnitude> Hz         | 128 Hz
     * >= 1,000 | <magnitude / 1000> kHz | 1.8 kHz
     *
     * @param {Number} hertz The Hertz magnitude.  If NaN, null will be returned.
     *
     * @return {String} Returns the string representation of the Hertz magnitude.  If the magnitude is null, undefined,
     *         or NaN, null will be returned.\
     *
     * @see isNumber(...)
     */
    that.hertzToString = function(hertz) {
        var str = null;

        if(that.isNumber(hertz)) {
            str = hertz < 0 ? '-' : '';

            hertz = Math.abs(hertz);

            if(hertz < 1000) {
                str += hertz + ' Hz';
            } else {
                str += hertz / 1000 + ' kHz';
            }
        }

        return str;
    };

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
     * @param {Number} seconds The seconds magnitude.  If NaN, null will be returned.
     *
     * @return {String} Returns the string representation of the seconds magnitude.  If the magnitude is null,
     *         undefined, or NaN, null will be returned.
     *
     * @see isNumber(...)
     */
    that.millisecondsToString = function(millis) {
        var str = null;

         if(that.isNumber(millis)) {
            str = millis < 0 ? '-' : '';

            millis = Math.abs(millis);

            if(millis === 0) {
                str = '0:00';
            } else if(millis <= 1000) {
                str += '0:01';
            } else if(millis < 60000) {
                str += millis < 10000 ? '0:0' : '0:';
                str += Math.ceil(millis / 1000);
            } else {
                var minutes = Math.floor(millis / 60000);

                millis = millis % 60000;
                str += minutes;
                str += millis < 10000 ? ':0' : ':';
                str += Math.ceil(millis / 1000);
            }
        }

        return str;
    };

    return that;
});
