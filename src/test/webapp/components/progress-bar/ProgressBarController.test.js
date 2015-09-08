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

import ProgressBarController from 'components/progress-bar/ProgressBarController';

describe(ProgressBarController.name, () => {
    let ctrl = null;
    let _scope = null;
    let _rootScope = null;
    let _players = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _scope = {
            meterElem: {
                width() {}
            }
        };
        _rootScope = {
            $on() {}
        };
        _players = {
            seekToPercent() {}
        };
        _emUtils = {
            isNumber() {}
        };
        
        ctrl = new ProgressBarController(_scope, _rootScope, _players, _emUtils);
        ProgressBarController.instance = ctrl;
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(ProgressBarController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(ProgressBarController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('init', () => {
        it('puts the progress meter click callback on the scope', () => {
            ctrl.init();
            expect(_scope.progressMeterClicked).toEqual(jasmine.any(Function));
        });
        
        it('puts the update meter width function on the scope', () => {
            expect(_scope.updateMeterWidth).toEqual(jasmine.any(Function));
        });
        
        it('adds a listener for player progress change events on the root scope', () => {
            spyOn(_rootScope, '$on').and.stub();
            ctrl.players.playerProgressChangedEventName = 'asdf';
            ctrl.init();
            expect(_rootScope.$on).toHaveBeenCalledWith('asdf', jasmine.any(Function));
        });
    });
    
    describe('progressMeterClicked', () => {
        beforeEach(function() {
            spyOn(_players, 'seekToPercent').and.stub();
            spyOn(_emUtils, 'isNumber').and.callFake((val) => {
                return typeof val === 'number';
            });
        });
        
        it('calls the seek callback with x-position / width', () => {
            ctrl.progressMeterClicked(17, 100);
            expect(_players.seekToPercent).toHaveBeenCalledWith(0.17);
        });
        
        it('does nothing when given an x-position that is not a number', () => {
            ctrl.progressMeterClicked('asdf', 100);
            expect(_players.seekToPercent).not.toHaveBeenCalled();
        });
        
        it('does nothing when given a width that is not a number', () => {
            ctrl.progressMeterClicked(17, 'asdf');
            expect(_players.seekToPercent).not.toHaveBeenCalled();
        });
    });
    
    describe('updateMeterWidth', () => {
        beforeEach(() => {
            spyOn(_scope.meterElem, 'width').and.stub();
            spyOn(_emUtils, 'isNumber').and.callFake((val) => {
                return typeof val === 'number';
            });
        });
        
        it('updates the meter width when the player progress is between 0 and 100', () => {
            ctrl.players.playerProgress = 17.5;
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('17.5%');
        });
        
        it('updates the meter width to 0% when the player progress is 0', () => {
            ctrl.players.playerProgress = 0;
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('0%');
        });
        
        it('updates the meter width to 0% when the player progress is negative', () => {
            ctrl.players.playerProgress = -13.5;
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('0%');
        });
        
        it('updates the meter width to 100% when the player progress is 100', () => {
            ctrl.players.playerProgress = 100;
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('100%');
        });
        
        it('updates the meter width to 100% when the player progress is greater than 100', () => {
            ctrl.players.playerProgress = 117;
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('100%');
        });
        
        it('updates the meter width to 0% when the player progress is not a number', () => {
            ctrl.players.playerProgress = 'asdf';
            ctrl.updateMeterWidth();
            expect(_scope.meterElem.width).toHaveBeenCalledWith('0%');
        });
    });
});
