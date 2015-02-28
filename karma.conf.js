module.exports = function(config) {
    'use strict';

    config.set({
        basePath : '',
        files : [
            // Third party libraries
            'src/main/webapp/assets/libs/aurora.js',
            'src/main/webapp/assets/libs/flac.js',
            'src/main/webapp/assets/libs/jquery.js',
            'src/main/webapp/assets/libs/angular.js',
            'src/main/webapp/assets/libs/angular-resource.js',
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
