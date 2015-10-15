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

import ProgressBarDirective from 'components/progress-bar/ProgressBarDirective';

describe(ProgressBarDirective.name, () => {
    let dir = null;
    
    beforeEach(() => {
        dir = new ProgressBarDirective();
    });
    
    describe('_link', () => {
        let _canvasContainer = null;
        let _canvas = null;
        let _scope = null;
        let _controller = null;
        
        beforeEach(() => {
            _canvasContainer = {};
            _canvas = {
                on() {}
            };
            _scope = {};
            _controller = {
                draw() {}
            };
            
            spyOn(dir, 'getElement').and.callFake((root, selector) => {
                let elem = null;
                
                if(selector === '.em-progress-bar-container') {
                    elem = _canvasContainer;
                } else if(selector === '.em-progress-bar-canvas') {
                    elem = _canvas;
                }
                
                return elem;
            });
        });
        
        it('puts the canvas container element on the scope', () => {
            _scope.canvasContainer = null;
            dir._link(_scope, {}, {}, _controller);
            expect(_scope.canvasContainer).toBe(_canvasContainer);
        });
        
        it('puts the canvas element on the scope', () => {
            _scope.canvas = null;
            dir._link(_scope, {}, {}, _controller);
            expect(_scope.canvas).toBe(_canvas);
        });
        
        it('adds a listener for click events on the canvas element', () => {
            spyOn(_canvas, 'on').and.stub();
            dir._link(_scope, {}, {}, _controller);
            expect(_canvas.on).toHaveBeenCalledWith('click', jasmine.any(Function));
        });
        
        it('draws the progress bar', () => {
            spyOn(_controller, 'draw').and.stub();
            dir._link(_scope, {}, {}, _controller);
            expect(_controller.draw).toHaveBeenCalled();
        });
    });
});
