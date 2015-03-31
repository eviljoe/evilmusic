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

angular.module('EvilMusicApp')
.directive('emLibrary', function() {
    'use strict';

    return {
        restrict : 'E',
        scope : {},
        controller : 'EMLibraryController',
        controllerAs : 'ctrl',
        templateUrl : '/components/library/library.html'
    };
})
.controller('EMLibraryController', ['library', 'queue', 'emUtils', function(library, queue, emUtils) {
    'use strict';

    var that = this;
    that.library = library;
    that.queue = queue;
    that.emUtils = emUtils;
}]);
