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

// jshint -W132

module.exports = function(config) {
    'use strict';

    var webSrcDir = 'src/main/webapp';
    var webTestDir = 'src/test/webapp';

    config.set({
        basePath: '',
        files: [
            // Third party libraries
            webSrcDir + '/assets/libs/aurora.js',
            webSrcDir + '/assets/libs/flac.js',
            'node_modules/jquery/dist/jquery.js', // Needs to be before Angular
            'node_modules/angular/angular.js',
            'node_modules/angular-resource/angular-resource.js', // Needs to be after Angular
            'node_modules/angular-bootstrap/dist/ui-bootstrap.js', // Needs to be after Angular
            'node_modules/angular-bootstrap/dist/ui-bootstrap-tpls.js', // Needs to be after Angular
            'node_modules/angular-mocks/angular-mocks.js',
            'node_modules/lodash/index.js',

            // Root level HTML & JavaScript files
            webSrcDir + '/index.js',
            webSrcDir + '/index.html',

            // EvilMusic JavaScript files
            webSrcDir + '/components/**/*',

            // Unit Tests
            webTestDir + '/**/*'
        ],
        browsers: ['PhantomJS'],
        frameworks: ['jasmine', 'browserify'],
        preprocessors: {
            'src/main/webapp/components/**/*.js': ['browserify'],
            'src/main/webapp/index.js': ['browserify'],
            'src/test/webapp/**/*.js': ['browserify']
        },
        browserify: {
            debug: true,
            paths: [webSrcDir],
            transform: ['babelify'],
            bundleDelay: 1000
        },
        autoWatch: true,
        usePolling: true
    });
};
