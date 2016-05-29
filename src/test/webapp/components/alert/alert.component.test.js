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

import {AlertComponent, ALERT_DIALOG_ELEMENT_ID} from 'components/alert/alert.component';

describe(AlertComponent.name, () => {
    let comp;
    let _alerts;
    let _modals;
    
    beforeEach(() => {
        _alerts = {
            getAlertInfo() {}
        };
        
        _modals = {
            hide() {}
        };
        
        comp = new AlertComponent(_alerts, _modals);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(AlertComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(AlertComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('title', () => {
        beforeEach(() => {
            spyOn(_alerts, 'getAlertInfo').and.returnValue({title: 'foo'});
        });
        
        it('returns null when there is no alert info', () => {
            _alerts.getAlertInfo.and.returnValue(null);
            expect(comp.title).toEqual(null);
        });
        
        it("returns the alert info's title", () => {
            expect(comp.title).toEqual('foo');
        });
    });
    
    describe('message', () => {
        beforeEach(() => {
            spyOn(_alerts, 'getAlertInfo').and.returnValue({message: 'bar'});
        });
        
        it('returns null when there is no alert info', () => {
            _alerts.getAlertInfo.and.returnValue(null);
            expect(comp.message).toEqual(null);
        });
        
        it("returns the alert info's message", () => {
            expect(comp.message).toEqual('bar');
        });
    });
    
    describe('debugInfo', () => {
        beforeEach(() => {
            spyOn(_alerts, 'getAlertInfo').and.returnValue({debugInfo: 'abc'});
        });
        
        it('returns null when there is no alert info', () => {
            _alerts.getAlertInfo.and.returnValue(null);
            expect(comp.debugInfo).toEqual(null);
        });
        
        it("returns the alert info's debug info", () => {
            expect(comp.debugInfo).toEqual('abc');
        });
    });
    
    describe('buttons', () => {
        beforeEach(() => {
            spyOn(_alerts, 'getAlertInfo').and.returnValue({buttons: 'xyz'});
        });
        
        it('returns null when there is no alert info', () => {
            _alerts.getAlertInfo.and.returnValue(null);
            expect(comp.buttons).toEqual(null);
        });
        
        it("returns the alert info's buttons", () => {
            expect(comp.buttons).toEqual('xyz');
        });
    });
    
    describe('close', () => {
        beforeEach(() => {
            spyOn(_modals, 'hide').and.stub();
        });
        
        it('hides the dialog', () => {
            comp.close();
            expect(_modals.hide).toHaveBeenCalledWith(ALERT_DIALOG_ELEMENT_ID);
        });
    });
});
