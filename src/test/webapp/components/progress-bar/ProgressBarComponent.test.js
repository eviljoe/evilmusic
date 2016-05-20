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

import {ProgressBarComponent} from 'components/progress-bar/ProgressBarComponent';

xdescribe(ProgressBarComponent.name, () => {
    let ctrl = null;
    let _rootScope = null;
    let _scope = null;
    let _timeout = null;
    let _players = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _rootScope = {
            $on() {}
        };
        _scope = {};
        _timeout = jasmine.createSpy('$timeout');
        _players = {
            seekToPercent() {}
        };
        _emUtils = {
            isNumber() {}
        };
        
        ctrl = new ProgressBarController(_rootScope, _scope, _timeout, _players, _emUtils);
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
        beforeEach(() => {
            spyOn(ctrl, 'draw').and.stub();
        });
        
        it('adds a listener for player progress change events on the root scope', () => {
            spyOn(_rootScope, '$on').and.stub();
            ctrl.players.playerProgressChangedEventName = 'asdf';
            ctrl.init();
            expect(_rootScope.$on).toHaveBeenCalledWith('asdf', jasmine.any(Function));
        });
        
        it('draws after a timeout', () => {
            ctrl.init();
            _timeout.calls.mostRecent().args[0]();
            expect(ctrl.draw).toHaveBeenCalled();
        });
        
        it('draws after a timeout of zero', () => {
            _timeout.calls.reset();
            ctrl.init();
            expect(_timeout).toHaveBeenCalledWith(jasmine.any(Function), 0);
        });
    });
    
    describe('barClicked', () => {
        let _event = null;
        
        beforeEach(() => {
            _event = jasmine.createSpyObj('event', ['stopPropagation']);
            spyOn(ctrl, 'getCanvasWidth').and.returnValue(100);
            spyOn(_players, 'seekToPercent').and.stub();
        });
        
        it('stops the event propogation', () => {
            ctrl.barClicked(_event);
            expect(_event.stopPropagation).toHaveBeenCalled();
        });
        
        it('seeks to the requested player progress based off of the x-offset of the click event', () => {
            _event.offsetX = 17;
            ctrl.barClicked(_event);
            expect(_players.seekToPercent).toHaveBeenCalledWith(0.17);
        });
    });
    
    describe('getPlayerProgress', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.callFake((val) => typeof val === 'number');
        });
        
        it('returns the player progress when it is between 0 and 100', () => {
            ctrl.players.playerProgress = 17.5;
            ctrl.getPlayerProgress();
            expect(ctrl.getPlayerProgress()).toEqual(17.5);
        });
        
        it('returns 0 when the player progress is 0', () => {
            ctrl.players.playerProgress = 0;
            expect(ctrl.getPlayerProgress()).toEqual(0);
        });
        
        it('returns 0 when the player progress is negative', () => {
            ctrl.players.playerProgress = -13.5;
            ctrl.getPlayerProgress();
            expect(ctrl.getPlayerProgress()).toEqual(0);
        });
        
        it('returns 100 when the player progress is 100', () => {
            ctrl.players.playerProgress = 100;
            ctrl.getPlayerProgress();
            expect(ctrl.getPlayerProgress()).toEqual(100);
        });
        
        it('returns 100 when the player progress is greater than 100', () => {
            ctrl.players.playerProgress = 117;
            ctrl.getPlayerProgress();
            expect(ctrl.getPlayerProgress()).toEqual(100);
        });
        
        it('returns 0 when the player progress is not a number', () => {
            ctrl.players.playerProgress = 'asdf';
            ctrl.getPlayerProgress();
            expect(ctrl.getPlayerProgress()).toEqual(0);
        });
    });
    
    describe('getCanvas', () => {
        let _canvas;
        
        beforeEach(() => {
            _canvas = {a: 'A'};
            _scope.canvas = [_canvas];
        });
        
        it('returns the canvas from the scope', () => {
            expect(ctrl.getCanvas()).toBe(_canvas);
        });
    });
    
    describe('getCanvasWidth', () => {
        let _canvasContainer;
        
        beforeEach(() => {
            _canvasContainer = jasmine.createSpyObj('canvasContainer', ['width']);
            _scope.canvasContainer = _canvasContainer;
        });
        
        it('returns 0 when the scope does not have a canvas container', () => {
            _scope.canvasContainer = null;
            expect(ctrl.getCanvasWidth()).toEqual(0);
        });
        
        it("returns the canvas container's width the canvas container exists", () => {
            _canvasContainer.width.and.returnValue(17);
            expect(ctrl.getCanvasWidth()).toEqual(17);
        });
    });
    
    describe('draw', () => {
        let _canvas;
        let _ctx;
        
        beforeEach(() => {
            _canvas = {
                width: 100,
                height: 10,
                getContext() {}
            };
            _ctx = jasmine.createSpyObj('context', ['clearRect']);
            
            spyOn(ctrl, 'getCanvas').and.returnValue(_canvas);
            spyOn(_canvas, 'getContext').and.returnValue(_ctx);
            spyOn(ctrl, 'drawMeter').and.stub();
            spyOn(ctrl, 'drawGutter').and.stub();
            spyOn(ctrl, 'getPlayerProgress').and.stub();
        });
        
        it('clears the entire canvas', () => {
            ctrl.draw();
            expect(_ctx.clearRect).toHaveBeenCalledWith(0, 0, 100, 10);
        });
        
        it("draws a meter filling the percentage of the player's progress", () => {
            ctrl.getPlayerProgress.and.returnValue(25);
            ctrl.draw();
            expect(ctrl.drawMeter).toHaveBeenCalledWith(_ctx, 0, 0, 25, 10);
        });
        
        it('draws a gutter filling portion of the bar not filled by the meter', () => {
            ctrl.getPlayerProgress.and.returnValue(25);
            ctrl.draw();
            expect(ctrl.drawGutter).toHaveBeenCalledWith(_ctx, 25, 0, 75, 10);
        });
    });
    
    describe('drawMeter', () => {
        let _ctx;
        
        beforeEach(() => {
            _ctx = jasmine.createSpyObj('ctx', ['fillRect']);
        });
        
        it('draws the meter in blue', () => {
            ctrl.drawMeter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillStyle).toEqual('blue');
        });
        
        it('draws the meter as a rectangle filling the given bounds', () => {
            ctrl.drawMeter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillRect).toHaveBeenCalledWith(1, 2, 3, 4);
        });
    });
    
    describe('drawGutter', () => {
        let _ctx;
        
        beforeEach(() => {
            _ctx = jasmine.createSpyObj('ctx', ['fillRect']);
        });
        
        it('draws the meter in light grey', () => {
            ctrl.drawGutter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillStyle).toEqual('lightgrey');
        });
        
        it('draws the meter as a rectangle filling the given bounds', () => {
            ctrl.drawGutter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillRect).toHaveBeenCalledWith(1, 2, 3, 4);
        });
    });
});
