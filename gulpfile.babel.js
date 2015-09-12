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
import gulp from 'gulp';
import jscs from 'gulp-jscs';
import jshint from 'gulp-jshint';
import jshintStylish from 'jshint-stylish';
import karma from 'gulp-karma';
import source from 'vinyl-source-stream';
import sourcemaps from 'gulp-sourcemaps';
import templateCache from 'gulp-angular-templatecache';

let webSrcDir = './src/main/webapp';
let webTestSrcDir = './src/test/webapp';
let destDir = './src/main/webapp/dist';
let cssDestDir = './src/main/webapp/dist/css';
let fontsDestDir = './src/main/webapp/dist/fonts';

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
    'node_modules/angular-bootstrap/dist/ui-bootstrap.js',
    'node_modules/angular-bootstrap/dist/ui-bootstrap-tpls.js',
    
    // Lodash
    'node_modules/lodash/index.js',
    
    // Aurora
    webSrcDir + '/assets/libs/aurora.js',
    
    // FLAC (Needs to be after Aurora)
    webSrcDir + '/assets/libs/flac.js'
];
 
// JOE TODO task convert src/main/webapp/assets/less to CSS (and get rid of src/main/webapp/assets/css/evilmusic.css)

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

gulp.task('clean', function() {
    del([destDir], function (err, deletedFiles) {});
});

gulp.task('concat-em-js', ['clean'], function() {
    browserify({
        paths: [webSrcDir],
        entries: webSrcDir + '/index.js',
        debug: true
    })
    .transform(babelify)
    .bundle()
    .pipe(source('evilmusic.js'))
    .pipe(gulp.dest(destDir));
});

gulp.task('concat-third-party-js', ['clean'], function() {
    return gulp.src(thirdPartyJSFiles)
    .pipe(sourcemaps.init())
    .pipe(concat('evilmusic-third-party.js'))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(destDir));
});

gulp.task('concat-em-css', ['clean'], function() {
    return gulp.src([
        webSrcDir + '/assets/css/**/*.css'
    ])
    .pipe(concat('evilmusic.css'))
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('concat-third-party-css', ['clean'], function() {
    return gulp.src([
        'node_modules/bootstrap/dist/css/bootstrap.css',
        'node_modules/bootstrap/dist/css/bootstrap-theme.css'
    ])
    .pipe(concat('evilmusic-third-party.css'))
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('copy-third-party-css-source-maps', ['clean'], function() {
    return gulp.src([
        'node_modules/bootstrap/dist/css/bootstrap.css.map',
        'node_modules/bootstrap/dist/css/bootstrap-theme.css.map'
    ])
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('create-template-cache', ['clean'], function() {
    return gulp.src(webSrcDir + '/**/*.html')
    .pipe(templateCache('evilmusic-templates.js', {module: 'EvilMusicApp'}))
    .pipe(gulp.dest(destDir));
});

gulp.task('copy-fonts', ['clean'], function() {
    return gulp.src([
        'node_modules/bootstrap/dist/fonts/*'
    ])
    .pipe(gulp.dest(fontsDestDir));
});

gulp.task('build', [
    'clean',
    'concat-em-js',
    'concat-third-party-js',
    'concat-em-css',
    'concat-third-party-css',
    'copy-third-party-css-source-maps',
    'create-template-cache',
    'copy-fonts'
]);

gulp.task('watch', ['build'], function() {
    gulp.watch(webSrcDir + '/assets/**/*', ['build']);
    gulp.watch(webSrcDir + '/components/**/*', ['build']);
    gulp.watch(webSrcDir + '/index.html', ['build']);
    gulp.watch(webSrcDir + '/index.js', ['build']);
});

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

gulp.task('default', ['build'], function() {});
