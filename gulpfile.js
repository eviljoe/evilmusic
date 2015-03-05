var gulp = require('gulp');
var concat = require('gulp-concat');
var sourcemaps = require('gulp-sourcemaps');
var del = require('del');
var templateCache = require('gulp-angular-templatecache');

var webSrcDir = 'src/main/webapp';
var destDir = 'src/main/webapp/dist';
var cssDestDir = 'src/main/webapp/dist/css';
var fontsDestDir = 'src/main/webapp/dist/fonts';
 
// JOE TODO task convert src/main/webapp/assets/less to CSS (and get rid of src/main/webapp/assets/css/evilmusic.css)

gulp.task('clean', function() {
    del([destDir], function (err, deletedFiles) {});
});

gulp.task('concat-em-js', ['clean'], function() {
    return gulp.src([
            webSrcDir + '/index.js',
            webSrcDir + '/components/**/*.js'
    ])
    .pipe(sourcemaps.init())
    .pipe(concat('evilmusic.js'))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(destDir));
});

gulp.task('concat-third-party-js', ['clean'], function() {
    return gulp.src([
        'node_modules/jquery/dist/jquery.js', // Needs to be before angular
        'node_modules/angular/angular.js',
        'node_modules/angular-resource/angular-resource.js', // Needs to be after angular
        'node_modules/angular-bootstrap/dist/ui-bootstrap.js', // Needs to be after angular
        webSrcDir + '/assets/libs/aurora.js',
        webSrcDir + '/assets/libs/flac.js', // Needs to be after aurora.js
    ])
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

gulp.task('concat-third-party-css-source-maps', ['clean'], function() {
    return gulp.src([
        'node_modules/bootstrap/dist/css/bootstrap.css.map',
        'node_modules/bootstrap/dist/css/bootstrap-theme.css.map',
    ])
    .pipe(gulp.dest(cssDestDir));
});

gulp.task('create-template-cache', ['clean'], function() {
    return gulp.src(webSrcDir + '/components/**/*.html')
        .pipe(templateCache('evilmusic-templates.js', { module : 'EvilMusicApp' }))
        .pipe(gulp.dest(destDir));
});

gulp.task('copy-fonts', ['clean'], function() {
    return gulp.src([
        'node_modules/bootstrap/dist/fonts/*'
    ])
    .pipe(gulp.dest(fontsDestDir));
});

gulp.task('watch', ['build'], function() {
    gulp.watch(webSrcDir + '/assets/**/*', ['build']);
    gulp.watch(webSrcDir + '/components/**/*', ['build']);
    gulp.watch(webSrcDir + '/index.html', ['build']);
    gulp.watch(webSrcDir + '/index.js', ['build']);
});

gulp.task(
    'build',
    [
        'clean',
        'concat-em-js',
        'concat-third-party-js',
        'concat-em-css',
        'concat-third-party-css',
        'concat-third-party-css-source-maps',
        'create-template-cache',
        'copy-fonts'
    ],
    function() {}
);

gulp.task('default', ['build'], function() {});