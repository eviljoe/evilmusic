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

import {Alerts, AlertButton} from 'services/alerts';

describe(Alerts.name, () => {
    let alerts = null;
    
    beforeEach(() => {
        alerts = new Alerts();
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(Alerts.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(Alerts.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('_show', () => {
        let _modalElement;
        
        beforeEach(() => {
            _modalElement = jasmine.createSpyObj('modalElement', ['show']);
            alerts.modalElement = _modalElement;
        });
        
        it('shows the alert dialog', () => {
            alerts._show();
            expect(_modalElement.show).toHaveBeenCalled();
        });
    });
    
    describe('hide', () => {
        let _modalElement;
        
        beforeEach(() => {
            _modalElement = jasmine.createSpyObj('modalElement', ['hide']);
            alerts.modalElement = _modalElement;
        });
        
        it('hides the alert dialog', () => {
            alerts.hide();
            expect(_modalElement.hide).toHaveBeenCalled();
        });
    });
    
    describe('error', () => {
        beforeEach(() => {
            spyOn(alerts, 'okButton').and.stub();
            spyOn(alerts, '_show').and.stub();
        });
        
        it('sets the alert info', () => {
            alerts.alertInfo = null;
            alerts.error();
            expect(alerts.alertInfo).toBeTruthy();
        });
        
        it('uses an OK button', () => {
            alerts.error();
            expect(alerts.okButton).toHaveBeenCalled();
        });
        
        it('shows the error dialog', () => {
            alerts.error();
            expect(alerts._show).toHaveBeenCalled();
        });
    });
    
    describe('okButton', () => {
        it('creats an alert button that says "OK"', () => {
            let btn = alerts.okButton();
            expect(btn instanceof AlertButton).toEqual(true);
            expect(btn.text).toEqual('OK');
        });
        
        it('creates a primary button by default', () => {
            expect(alerts.okButton().primary).toEqual(true);
        });
        
        it('creates a resolve button by default', () => {
            expect(alerts.okButton().resolve).toEqual(true);
        });
        
        it('can be used to create a non-primary button', () => {
            expect(alerts.okButton(false).primary).toEqual(false);
        });
        
        it('can be used to create a reject button', () => {
            expect(alerts.okButton(undefined, false).resolve).toEqual(false);
        });
    });
});
