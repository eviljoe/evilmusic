'use strict';

angular.module('EvilMusicApp')
.directive('emLibrary', function() {
    return {
        scope : {},
        controller : 'EMLibraryController',
        controllerAs : 'ctrl',
        templateUrl: '/components/library/library.html'
    };
})
.controller('EMLibraryController', ['library', 'queue', 'emUtils', function(library, queue, emUtils) {
    var that = this;
    that.library = library;
    that.queue = queue;
    that.emUtils = emUtils;
}]);
