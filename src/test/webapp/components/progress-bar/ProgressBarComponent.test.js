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

import {Observable} from 'rxjs';
import {ProgressBarComponent} from 'components/progress-bar/ProgressBarComponent';

describe(ProgressBarComponent.name, () => {
    let comp = null;
    let _elem = null;
    let _players = null;
    let _emUtils = null;
    
    beforeEach(() => {
        _elem = {
            querySelector() {}
        };
        
        _emUtils = {
            isNumber() {}
        };
        
        _players = {
            seekToPercent() {}
        };
        
        comp = new ProgressBarComponent({nativeElement: _elem}, _emUtils, _players);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(ProgressBarComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(ProgressBarComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('ngOnInit', () => {
        let playerProgressObserver;
        let queriedForElem;
        
        beforeEach(() => {
            _players.playerProgressChanges = Observable.create((observer) => {
                playerProgressObserver = observer;
            });
            queriedForElem = {foo: 'bar'};
            
            spyOn(comp, 'draw').and.stub();
            spyOn(comp, 'setTimeout').and.stub();
            spyOn(_elem, 'querySelector').and.returnValue(queriedForElem);
        });
        
        it('draws when player progress changes are fired', () => {
            comp.ngOnInit();
            playerProgressObserver.next();
            expect(comp.draw).toHaveBeenCalled();
        });
        
        it('finds & sets the canvas container element', () => {
            comp.ngOnInit();
            expect(comp.canvasContainer).toEqual(queriedForElem);
        });
        
        it('finds & sets canvas element', () => {
            comp.ngOnInit();
            expect(comp.canvas).toEqual(queriedForElem);
        });
        
        it('draws after a timeout of zero', () => {
            comp.ngOnInit();
            expect(comp.setTimeout).toHaveBeenCalledWith(jasmine.any(Function), 0);
        });
        
        it('draws after a timeout', () => {
            comp.ngOnInit();
            comp.setTimeout.calls.mostRecent().args[0]();
            expect(comp.draw).toHaveBeenCalled();
        });
    });
    
    describe('barClicked', () => {
        let _event = null;
        
        beforeEach(() => {
            _event = jasmine.createSpyObj('event', ['stopPropagation']);
            spyOn(comp, 'getContainerWidth').and.returnValue(100);
            spyOn(_players, 'seekToPercent').and.stub();
        });
        
        it('stops the event propogation', () => {
            comp.barClicked(_event);
            expect(_event.stopPropagation).toHaveBeenCalled();
        });
        
        it('seeks to the requested player progress based off of the x-offset of the click event', () => {
            _event.offsetX = 17;
            comp.barClicked(_event);
            expect(_players.seekToPercent).toHaveBeenCalledWith(0.17);
        });
    });
    
    describe('getPlayerProgress', () => {
        beforeEach(() => {
            spyOn(_emUtils, 'isNumber').and.callFake((val) => typeof val === 'number');
        });
        
        it('returns the player progress when it is between 0 and 100', () => {
            comp.players.playerProgress = 17.5;
            comp.getPlayerProgress();
            expect(comp.getPlayerProgress()).toEqual(17.5);
        });
        
        it('returns 0 when the player progress is 0', () => {
            comp.players.playerProgress = 0;
            expect(comp.getPlayerProgress()).toEqual(0);
        });
        
        it('returns 0 when the player progress is negative', () => {
            comp.players.playerProgress = -13.5;
            comp.getPlayerProgress();
            expect(comp.getPlayerProgress()).toEqual(0);
        });
        
        it('returns 100 when the player progress is 100', () => {
            comp.players.playerProgress = 100;
            comp.getPlayerProgress();
            expect(comp.getPlayerProgress()).toEqual(100);
        });
        
        it('returns 100 when the player progress is greater than 100', () => {
            comp.players.playerProgress = 117;
            comp.getPlayerProgress();
            expect(comp.getPlayerProgress()).toEqual(100);
        });
        
        it('returns 0 when the player progress is not a number', () => {
            comp.players.playerProgress = 'asdf';
            comp.getPlayerProgress();
            expect(comp.getPlayerProgress()).toEqual(0);
        });
    });
    
    describe('getContainerWidth', () => {
        let _canvasContainer;
        
        beforeEach(() => {
            _canvasContainer = {};
            comp.canvasContainer = _canvasContainer;
        });
        
        it('returns 0 when the scope does not have a canvas container', () => {
            comp.canvasContainer = null;
            expect(comp.getContainerWidth()).toEqual(0);
        });
        
        it("returns the canvas container's width the canvas container exists", () => {
            _canvasContainer.clientWidth = 17;
            expect(comp.getContainerWidth()).toEqual(17);
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
            comp.canvas = _canvas;
            
            spyOn(_canvas, 'getContext').and.returnValue(_ctx);
            spyOn(comp, 'drawMeter').and.stub();
            spyOn(comp, 'drawGutter').and.stub();
            spyOn(comp, 'getPlayerProgress').and.stub();
        });
        
        it('clears the entire canvas', () => {
            comp.draw();
            expect(_ctx.clearRect).toHaveBeenCalledWith(0, 0, 100, 10);
        });
        
        it("draws a meter filling the percentage of the player's progress", () => {
            comp.getPlayerProgress.and.returnValue(25);
            comp.draw();
            expect(comp.drawMeter).toHaveBeenCalledWith(_ctx, 0, 0, 25, 10);
        });
        
        it('draws a gutter filling portion of the bar not filled by the meter', () => {
            comp.getPlayerProgress.and.returnValue(25);
            comp.draw();
            expect(comp.drawGutter).toHaveBeenCalledWith(_ctx, 25, 0, 75, 10);
        });
    });
    
    describe('drawMeter', () => {
        let _ctx;
        
        beforeEach(() => {
            _ctx = jasmine.createSpyObj('ctx', ['fillRect']);
        });
        
        it('draws the meter in blue', () => {
            comp.drawMeter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillStyle).toEqual('blue');
        });
        
        it('draws the meter as a rectangle filling the given bounds', () => {
            comp.drawMeter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillRect).toHaveBeenCalledWith(1, 2, 3, 4);
        });
    });
    
    describe('drawGutter', () => {
        let _ctx;
        
        beforeEach(() => {
            _ctx = jasmine.createSpyObj('ctx', ['fillRect']);
        });
        
        it('draws the meter in light grey', () => {
            comp.drawGutter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillStyle).toEqual('lightgrey');
        });
        
        it('draws the meter as a rectangle filling the given bounds', () => {
            comp.drawGutter(_ctx, 1, 2, 3, 4);
            expect(_ctx.fillRect).toHaveBeenCalledWith(1, 2, 3, 4);
        });
    });
});
