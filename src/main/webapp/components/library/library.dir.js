angular.module('EvilMusicApp')
.directive('emLibrary', function() {
    'use strict';

    return {
        restrict : 'E',
        scope : {},
        controller : 'EMLibraryController',
        controllerAs : 'ctrl',
        templateUrl : '/components/library/library.html'
    };
})
.controller('EMLibraryController', ['library', 'queue', 'emUtils', function(library, queue, emUtils) {
    'use strict';

    var that = this;
    that.library = library;
    that.queue = queue;
    that.emUtils = emUtils;
}]);
