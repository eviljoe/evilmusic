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

module.exports = function(config) {
    'use strict';

    config.set({
        basePath : '',
        files : [
            // Third party libraries
            'src/main/webapp/assets/libs/aurora.js',
            'src/main/webapp/assets/libs/flac.js',
            'node_modules/jquery/dist/jquery.js', // Needs to be before angular
            'node_modules/angular/angular.js',
            'node_modules/angular-resource/angular-resource.js', // Needs to be after angular
            'node_modules/angular-bootstrap/dist/ui-bootstrap.js', // Needs to be after angular
            'node_modules/angular-mocks/angular-mocks.js',

            // Root level HTML & JavaScript files
            'src/main/webapp/index.js',
            'src/main/webapp/index.html',

            // EvilMusic AngularJS files
            'src/main/webapp/components/**/*',

            // Unit Tests
            'src/test/webapp/**/*',
        ],
        frameworks : ['jasmine'],
        browsers : ['PhantomJS'],
        autoWatch : true,
        usePolling : true
    });
};
