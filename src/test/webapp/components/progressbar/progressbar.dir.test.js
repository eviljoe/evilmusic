describe('emProgressBar', function() {
    'use strict';
    
    var $controller;

    beforeEach(module('EvilMusicApp'));

    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('EMProgressBarController', function() {

        var ctrl;
        var $rootScope;
        var player;
        var scope;

        beforeEach(inject(function(_$rootScope_, _player_, _emUtils_) {
            $rootScope = _$rootScope_;
            player = _player_;
            scope = {};

            spyOn($rootScope, '$on').and.callThrough();

            ctrl = $controller('EMProgressBarController', {
                $scope : scope,
                $rootScope : $rootScope,
                player : player,
                emUtils : _emUtils_
            });
        }));

        describe('construction', function() {
            it('adds a listener on player progress', function() {
                expect($rootScope.$on).toHaveBeenCalledWith(
                    player.playerProgressChangedEventName, scope.updateMeterWidth);
            });
        });

        describe('updateMeterWidth', function() {

            beforeEach(function() {
                scope.meterElem = { width : function(w) {} };
                spyOn(scope.meterElem, 'width').and.callThrough();
            });

            it('updates the meter width when the player progress is between 0 and 100', function() {
                player.playerProgress = 17.5;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('17.5%');
            });

            it('updates the meter width to 0% when the player progress is 0', function() {
                player.playerProgress = 0;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('0%');
            });

            it('updates the meter width to 0% when the player progress is negative', function() {
                player.playerProgress = -13.5;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('0%');
            });

            it('updates the meter width to 100% when the player progress is 100', function() {
                player.playerProgress = 100;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('100%');
            });

            it('updates the meter width to 100% when the player progress is greater than 100', function() {
                player.playerProgress = 117;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('100%');
            });

            it('updates the meter width to 0% when the player progress is null', function() {
                player.playerProgress = null;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('0%');
            });

            it('updates the meter width to 0% when the player progress is undefined', function() {
                player.playerProgress = undefined;
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('0%');
            });

            it('updates the meter width to 0% when the player progress is NaN', function() {
                player.playerProgress = 'hello world';
                scope.updateMeterWidth();
                expect(scope.meterElem.width).toHaveBeenCalledWith('0%');
            });
        });

        describe('progressMeterClicked', function() {

            beforeEach(function() {
                scope.onseek = function(p) {};
                spyOn(scope, 'onseek').and.callThrough();
            });

            it('calls the seek callback with x-position / width', function() {
                scope.progressMeterClicked(17, 100);
                expect(scope.onseek).toHaveBeenCalledWith(0.17);
            });

            it('does nothing when given an x-position that is null', function() {
                scope.progressMeterClicked(null, 100);
                expect(scope.onseek).not.toHaveBeenCalled();
            });

            it('does nothing when given an x-position that is undefined', function() {
                scope.progressMeterClicked(undefined, 100);
                expect(scope.onseek).not.toHaveBeenCalled();
            });

            it('does nothing when given an x-position that is NaN', function() {
                scope.progressMeterClicked('hello world', 100);
                expect(scope.onseek).not.toHaveBeenCalled();
            });

            it('does nothing when given a width that is null', function() {
                scope.progressMeterClicked(17, null);
                expect(scope.onseek).not.toHaveBeenCalled();
            });

            it('does nothing when given a width that is undefined', function() {
                scope.progressMeterClicked(17, undefined);
                expect(scope.onseek).not.toHaveBeenCalled();
            });

            it('does nothing when given a width that is NaN', function() {
                scope.progressMeterClicked(17, 'hello world');
                expect(scope.onseek).not.toHaveBeenCalled();
            });
        });
    });
});
