'use strict';

module.exports = function(config) {
    config.set({
        basePath : '',
        files : [
            'src/main/webapp/js/emaurora.js',
            'src/main/webapp/js/lib/flac.js',
            'src/main/webapp/js/lib/jquery.js',
            'src/main/webapp/js/lib/angular.js',
            'node_modules/angular-mocks/angular-mocks.js',
            'src/main/webapp/js/*.js',
            'src/main/webapp/js/directives/*.js',
            'src/test/webapp/js/**/*.js',
        ],
        frameworks : ['jasmine'],
        browsers : ['PhantomJS'],
        autoWatch : true,
        usePolling : true
    });
};