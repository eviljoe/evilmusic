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
        let _gutterElem = null;
        let _meterElem = null;
        let _scope = null;
        let _element = null;
        
        beforeEach(() => {
            _gutterElem = {
                on: () => {},
                width: () => {}
            };
            
            _meterElem = {on: () => {}};
            
            _scope = {
                progressMeterClicked: () => {},
                updateMeterWidth: () => {}
            };
            
            _element = {};
            
            spyOn(dir, 'getElement').and.callFake((elem, selector) => {
                let e = null;
                
                if(selector === '.em-progress-gutter') {
                    e = _gutterElem;
                } else if(selector === '.em-progress-meter') {
                    e = _meterElem;
                }
                
                return e;
            });
        });
        
        it('puts the bar element on the scope', () => {
            _scope.barElem = null;
            dir._link(_scope, _element, {});
            expect(_scope.barElem).toBe(_element);
        });
        
        it('puts the gutter element on the scope', () => {
            _scope.gutterElem = null;
            dir._link(_scope, _element, {});
            expect(_scope.gutterElem).toBe(_gutterElem);
        });
        
        it('puts the meter element on the scope', () => {
            _scope.gutterElem = null;
            dir._link(_scope, _element, {});
            expect(_scope.meterElem).toBe(_meterElem);
        });
        
        it('adds a listener for click events on the gutter element', () => {
            spyOn(_gutterElem, 'on').and.stub();
            dir._link(_scope, _element, {});
            expect(_gutterElem.on).toHaveBeenCalledWith('click', jasmine.any(Function));
        });
        
        it('adds a listener for click events on the meter element', () => {
            spyOn(_meterElem, 'on').and.stub();
            dir._link(_scope, _element, {});
            expect(_meterElem.on).toHaveBeenCalledWith('click', jasmine.any(Function));
        });
        
        it('updates the meter width', () => {
            spyOn(_scope, 'updateMeterWidth').and.stub();
            dir._link(_scope, _element, {});
            expect(_scope.updateMeterWidth).toHaveBeenCalled();
        });
    });
    
    describe('barClicked', () => {
        let _scope = null;
        let _event = null;
        
        beforeEach(() => {
            _scope = {
                gutterElem: {width: () => {}},
                progressMeterClicked: () => {}
            };
            _event = {stopPropagation: () => {}};
        });
        
        it('stops the event propogation', () => {
            spyOn(_event, 'stopPropagation').and.stub();
            dir.barClicked(_scope, _event);
            expect(_event.stopPropagation).toHaveBeenCalled();
        });
        
        it('notifies the scope that the progress bar was clicked', () => {
            spyOn(_scope, 'progressMeterClicked').and.stub();
            spyOn(_scope.gutterElem, 'width').and.returnValue(13);
            _event.offsetX = 7;

            dir.barClicked(_scope, _event);
            expect(_scope.progressMeterClicked).toHaveBeenCalledWith(7, 13);
        });
    });
});
