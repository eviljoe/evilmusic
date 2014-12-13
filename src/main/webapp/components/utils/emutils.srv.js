'use strict';

angular.module('EvilMusicApp')
.factory('emUtils', function() {
    var that = this;

    that.isNumber = function(val) {
        return val !== null && val !== undefined && val !== '' && !isNaN(val);
    };

    return that;
});