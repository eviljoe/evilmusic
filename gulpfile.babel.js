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

import concat from 'gulp-concat';
import babelify from 'babelify';
import browserify from 'browserify';
import del from 'del';
import fs from 'fs';
import gulp from 'gulp';
import jscs from 'gulp-jscs';
import jshint from 'gulp-jshint';
import jshintStylish from 'jshint-stylish';
import karma from 'gulp-karma';
import runSeq from 'run-sequence';
import source from 'vinyl-source-stream';
import sourcemaps from 'gulp-sourcemaps';
import templateCache from 'gulp-angular-templatecache';
import _ from 'lodash';

/* ********* */
/* Variables */
/* ********* */

let webSrcDir = './src/main/webapp';
let webTestSrcDir = './src/test/webapp';
let destDir = './src/main/webapp/dist';
let cssDestDir = destDir + '/css';
let fontsDestDir = destDir + '/fonts';

let jsFilesToTest = [
    webSrcDir + '/**/*.js',
    webSrcDir + '/**/*.js',
    webTestSrcDir + '/**/*.js',
    '.package.json',
    '!' + webSrcDir + '/assets/**',
    '!' + webSrcDir + '/dist/**'
];

let thirdPartyJSFiles = [
    // JQuery (Needs to be before Angular)
    'node_modules/jquery/dist/jquery.js',
    
    // Angular
    'node_modules/angular/angular.js',
    
    // ngResource (Needs to be after Angular)
    'node_modules/angular-resource/angular-resource.js',
    
    // UI Bootstrap (Needs to be after Angular)
    'node_modules/angular-bootstrap/ui-bootstrap.js',
    'node_modules/angular-bootstrap/ui-bootstrap-tpls.js',
    
    // Lodash
    'node_modules/lodash/index.js',
    
    // Aurora
    webSrcDir + '/assets/libs/aurora.js',
    
    // FLAC (Needs to be after Aurora)
    webSrcDir + '/assets/libs/flac.js'
];

let thirdPartyCSSFiles = [
    'node_modules/bootstrap/dist/css/bootstrap.css',
    'node_modules/bootstrap/dist/css/bootstrap-theme.css',
    'node_modules/font-awesome/css/font-awesome.css'
];

let thirdPartyCSSMapFiles = [
    'node_modules/bootstrap/dist/css/bootstrap.css.map',
    'node_modules/bootstrap/dist/css/bootstrap-theme.css.map',
    'node_modules/font-awesome/css/font-awesome.css.map'
];

/* ******* */
/* Linting */
/* ******* */

gulp.task('lint-js-jshint', function() {
    return gulp.src(jsFilesToTest)
    .pipe(jshint())
    .pipe(jshint.reporter(jshintStylish));
});

gulp.task('lint-js-jscs', function() {
    return gulp.src(jsFilesToTest)
    .pipe(jscs());
});

gulp.task('lint', ['lint-js-jshint', 'lint-js-jscs']);

gulp.task('clean', function(cb) {
    del([destDir + '/**'], cb);
});

/* ********** */
/* JavaScript */
/* ********** */

gulp.task('concat-em-js', function(cb) {
    browserify({
        paths: [webSrcDir],
        entries: webSrcDir + '/index.js',
        debug: true
    })
    .transform(babelify)
    .bundle()
    .pipe(source('evilmusic.js'))
    .pipe(gulp.dest(destDir));
    cb();
});

gulp.task('concat-third-party-js', function() {
    verifyFilesExist(thirdPartyJSFiles);
    
    return gulp.src(thirdPartyJSFiles)
    .pipe(sourcemaps.init())
    .pipe(concat('evilmusic-third-party.js'))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(destDir));
});

/* *** */
/* CSS */
/* *** */

gulp.task('concat-em-css', function() {
    return gulp.src([
        webSrcDir + '/assets/css/**/*.css'
    ])
    .pipe(concat('evilmusic.css'))
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('concat-third-party-css', function() {
    verifyFilesExist(thirdPartyCSSFiles);
    
    return gulp.src(thirdPartyCSSFiles)
    .pipe(concat('evilmusic-third-party.css'))
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('copy-third-party-css-source-maps', function() {
    verifyFilesExist(thirdPartyCSSMapFiles);
    
    return gulp.src(thirdPartyCSSMapFiles)
    .pipe(gulp.dest(cssDestDir));
});

/* **** */
/* HTML */
/* **** */

gulp.task('create-template-cache', function() {
    return gulp.src(webSrcDir + '/**/*.html')
    .pipe(templateCache('evilmusic-templates.js', {module: 'EvilMusicApp'}))
    .pipe(gulp.dest(destDir));
});

/* ***** */
/* Fonts */
/* ***** */

gulp.task('copy-fonts', function() {
    return gulp.src([
        'node_modules/font-awesome/fonts/*'
    ])
    .pipe(gulp.dest(fontsDestDir));
});

/* ****** */
/* Builds */
/* ****** */

gulp.task('build-first-party', [
    'concat-em-js',
    'concat-em-css',
    'create-template-cache'
]);

gulp.task('build-third-party', [
    'concat-third-party-js',
    'concat-third-party-css',
    'copy-third-party-css-source-maps',
    'copy-fonts'
]);

gulp.task('build', function() {
    return runSeq(
        'clean',
        ['build-first-party', 'build-third-party']);
});

/* ******* */
/* Watches */
/* ******* */

gulp.task('watch', ['build'], function() {
    return gulp.watch([
            webSrcDir + '/assets/**/*',
            webSrcDir + '/components/**/*',
            webSrcDir + '/index.html',
            webSrcDir + '/index.js'
        ],
        {},
        ['build-first-party']);
});

/* ***** */
/* Tests */
/* ***** */

gulp.task('test', function() {
    return gulp.src([])
    .pipe(karma({
        configFile: 'karma.conf.js',
        action: 'run'
    }))
    .on('error', function(err) {
        throw err;
    });
});

/* ******* */
/* Default */
/* ******* */

gulp.task('default', ['build'], function() {});

/* ********* */
/* Functions */
/* ********* */

function verifyFilesExist(files) {
    _.forEach(files, function(file) {
        fs.stat(file, function(err, stat) {
            if(err) {
                throw err;
            }
        });
    });
}
