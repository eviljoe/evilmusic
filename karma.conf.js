'use strict';

module.exports = function(config) {
    config.set({
        basePath : '',
        files : [
            // Third party libraries
            'src/main/webapp/assets/libs/aurora.js',
            'src/main/webapp/assets/libs/flac.js',
            'src/main/webapp/assets/libs/jquery.js',
            'src/main/webapp/assets/libs/angular.js',
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