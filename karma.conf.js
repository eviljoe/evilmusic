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

/* eslint-disable no-undef, strict, no-var, prefer-template */

module.exports = function(config) {
    'use strict';

    var WEB_SRC_DIR = 'src/main/webapp';
    var WEB_TEST_DIR = 'src/test/webapp';

    config.set({
        basePath: '',
        files: [
            // Necessary for Babel to be able to convert some things (like the new ES6 data structures)
            'node_modules/babel-polyfill/dist/polyfill.js',
            
            // WEB_SRC_DIR + '/index.js',
            // WEB_SRC_DIR + '/components/**/*.js',
            `${WEB_TEST_DIR}/**/*.js`
        ],
        browsers: ['PhantomJS'],
        frameworks: ['jasmine', 'browserify'],
        preprocessors: {
            // 'src/main/webapp/components/**/*.js': ['browserify'],
            // 'src/main/webapp/index.js': ['browserify'],
            'src/test/webapp/**/*.js': ['browserify']
        },
        browserify: {
            debug: true,
            paths: [WEB_SRC_DIR],
            transform: ['babelify']
        },
        autoWatch: true,
        usePolling: true
    });
};
