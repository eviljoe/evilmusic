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
.factory('Queues', ['$resource', function($resource) {
    'use strict';
    
    function emptyRequest(data) {
        return undefined;
    }
    
    return $resource(
        '/rest/queue/:id',
        { id : '@id' },
        {
            addLast : {
                method : 'PUT',
                url : '/rest/queue/:id/last',
                transformRequest : emptyRequest
            },
            remove : {
                method : 'DELETE',
                url : '/rest/queue/:id/queueindex/:qIndex',
                params : { id : '@id', qIndex : '@qIndex'},
                transformRequest : emptyRequest
            },
            clear : {
                method : 'DELETE',
                url : '/rest/queue/:id/elements',
                transformRequest : emptyRequest
            }
        }
    );
}]);
