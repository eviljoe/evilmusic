'use strict';

module.exports = function(config) {
    config.set({
        basePath : '',
        files : [
            // Third party libraries
            'src/main/webapp/assets/libs/aurora.js',
            'src/main/webapp/assets/libs/flac.js',
            'src/main/webapp/assets/libs/query.js',
            'src/main/webapp/assets/libs/angular.js',
            'node_modules/angular-mocks/angular-mocks.js',

            // Root level HTML & JavaScript files
            'src/main/webapp/index.html',
            'src/main/webapp/emcontroller.js',

            // EvilMusic non-AngularJS JavaScript Files
            'src/main/webapp/assets/js/**/*.js',

            // AngularJS Directives
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