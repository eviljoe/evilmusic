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

import _ from 'lodash';
import babelify from 'babelify';
import browserify from 'browserify';
import concat from 'gulp-concat';
import del from 'del';
import eslint from 'gulp-eslint';
import fs from 'fs';
import gulp from 'gulp';
import jscs from 'gulp-jscs';
import karma from 'gulp-karma';
import runSeq from 'run-sequence';
import source from 'vinyl-source-stream';

/* ********* */
/* Variables */
/* ********* */

const WEB_SRC_DIR = './src/main/webapp';
const WEB_TEST_SRC_DIR = './src/test/webapp';
const DEST_DIR = './src/main/webapp/dist';
const CSS_DEST_DIR = `${DEST_DIR}/css`;
const FONTS_DEST_DIR = `${DEST_DIR}/fonts`;

const JS_FILES_TO_LINT = [
    `${WEB_SRC_DIR}/**/*.js`,
    `${WEB_TEST_SRC_DIR}/**/*.js`,
    '.package.json',
    'gulpfile*.js',
    `!${WEB_SRC_DIR}/assets/**`,
    `!${WEB_SRC_DIR}/dist/**`
];

const THIRD_PARTY_LIBS = [
    '@angular/common',
    '@angular/compiler',
    '@angular/core',
    '@angular/http',
    '@angular/platform-browser-dynamic',
    '@angular/platform-browser',
    '@angular/upgrade',
    'babel-core',
    'babel-polyfill',
    'babel-preset-es2015',
    'babelify',
    'bootstrap',
    'es6-shim',
    'jquery',
    'lodash',
    'ng2-bootstrap',
    'reflect-metadata',
    'rxjs',
    'zone.js'
];

const THIRD_PARTY_JS_FILES = [
    `${WEB_SRC_DIR}/assets/libs/aurora.js`, // Aurora
    `${WEB_SRC_DIR}/assets/libs/flac.js` // FLAC (Needs to be after Aurora)
];

const FIRST_PARTY_CSS_FILES = [
    `${WEB_SRC_DIR}/assets/css/**/*.css`
];

const THIRD_PARTY_CSS_FILES = [
    'node_modules/bootstrap/dist/css/bootstrap.css',
    'node_modules/bootstrap/dist/css/bootstrap-theme.css',
    'node_modules/font-awesome/css/font-awesome.css'
];

const THIRD_PARTY_CSS_MAP_FILES = [
    'node_modules/bootstrap/dist/css/bootstrap.css.map',
    'node_modules/bootstrap/dist/css/bootstrap-theme.css.map',
    'node_modules/font-awesome/css/font-awesome.css.map'
];

const THIRD_PARTY_FONT_FILES = [
    'node_modules/font-awesome/fonts/*'
];

const WATCH_DIRS = [
    `${WEB_SRC_DIR}/assets/**/*`,
    `${WEB_SRC_DIR}/components/**/*`,
    `${WEB_SRC_DIR}/pipes/**/*`,
    `${WEB_SRC_DIR}/services/**/*`,
    `${WEB_SRC_DIR}/index.html`,
    `${WEB_SRC_DIR}/index.js`
];

/* ******** */
/* Cleaning */
/* ******** */

gulp.task('clean', (cb) => {
    return del([`${DEST_DIR}/**`]);
});

/* ******* */
/* Linting */
/* ******* */

gulp.task('lint-js-eslint', () => {
    return gulp.src(JS_FILES_TO_LINT)
    .pipe(eslint())
    .pipe(eslint.format())
    .pipe(eslint.failAfterError());
});

gulp.task('lint-js-jscs', () => {
    return gulp.src(JS_FILES_TO_LINT)
    .pipe(jscs());
});

gulp.task('lint', ['lint-js-eslint', 'lint-js-jscs']);

/* ********** */
/* JavaScript */
/* ********** */

gulp.task('build-em-js', (cb) => {
    const b = browserify({
        paths: [WEB_SRC_DIR],
        entries: `${WEB_SRC_DIR}/index.js`,
        debug: true
    });
    
    THIRD_PARTY_LIBS.forEach((lib) => b.external(lib));
    
    return b.transform(babelify)
    .bundle()
    .pipe(source('evilmusic.js'))
    .pipe(gulp.dest(DEST_DIR));
});

gulp.task('build-third-party-js', (cb) => {
    const b = browserify({
        entries: THIRD_PARTY_JS_FILES,
        debug: true
    });
    
    THIRD_PARTY_LIBS.forEach((lib) => b.require(lib));
    
    return b.bundle()
    .pipe(source('evilmusic-third-party.js'))
    .pipe(gulp.dest(DEST_DIR));
});

/* *** */
/* CSS */
/* *** */

gulp.task('concat-em-css', () => {
    return gulp.src(FIRST_PARTY_CSS_FILES)
    .pipe(concat('evilmusic.css'))
    .pipe(gulp.dest(CSS_DEST_DIR));
});

gulp.task('concat-third-party-css', () => {
    verifyFilesExist(THIRD_PARTY_CSS_FILES);
    
    return gulp.src(THIRD_PARTY_CSS_FILES)
    .pipe(concat('evilmusic-third-party.css'))
    .pipe(gulp.dest(CSS_DEST_DIR));
});

gulp.task('copy-third-party-css-source-maps', () => {
    verifyFilesExist(THIRD_PARTY_CSS_MAP_FILES);
    
    return gulp.src(THIRD_PARTY_CSS_MAP_FILES)
    .pipe(gulp.dest(CSS_DEST_DIR));
});

/* **** */
/* HTML */
/* **** */

gulp.task('create-template-cache', () => {
    // return gulp.src(FIRST_PARTY_HTML_FILES)
    //     .pipe(templateCache('evilmusic-templates.js', {module: 'EvilMusicApp'}))
    //     .pipe(gulp.dest(DEST_DIR));
});

/* ***** */
/* Fonts */
/* ***** */

gulp.task('copy-fonts', () => {
    return gulp.src(THIRD_PARTY_FONT_FILES)
    .pipe(gulp.dest(FONTS_DEST_DIR));
});

/* ****** */
/* Builds */
/* ****** */

gulp.task('build-first-party', [
    'build-em-js',
    'concat-em-css',
    'create-template-cache'
]);

gulp.task('build-third-party', [
    'build-third-party-js',
    'concat-third-party-css',
    'copy-third-party-css-source-maps',
    'copy-fonts'
]);

gulp.task('build', (cb) => {
    return runSeq('clean', ['build-first-party', 'build-third-party'], cb);
});

/* ******* */
/* Watches */
/* ******* */

gulp.task('watch', ['build'], () => {
    return gulp.watch(WATCH_DIRS, {}, ['build-first-party']);
});

/* ***** */
/* Tests */
/* ***** */

gulp.task('test', () => {
    return gulp.src([])
    .pipe(karma({
        configFile: 'karma.conf.js',
        action: 'run'
    }))
    .on('error', (err) => {
        throw err;
    });
});

/* ******* */
/* Default */
/* ******* */

gulp.task('default', ['build']);

/* ********* */
/* Functions */
/* ********* */

function verifyFilesExist(files) {
    _.forEach(files, (file) => {
        fs.stat(file, (err, stat) => {
            if(err) {
                throw err;
            }
        });
    });
}
