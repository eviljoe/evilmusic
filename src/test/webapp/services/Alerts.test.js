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

import {Alerts, AlertButton} from 'services/Alerts';

describe(Alerts.name, () => {
    let alerts = null;
    let _modals = null;
    
    beforeEach(() => {
        _modals = {
            show() {}
        };
        
        alerts = new Alerts(_modals);
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
    
    describe('error', () => {
        beforeEach(() => {
            spyOn(alerts, 'okButton').and.stub();
            spyOn(_modals, 'show').and.stub();
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
        
        it('opens a modal dialog', () => {
            alerts.error();
            expect(_modals.show).toHaveBeenCalled();
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
