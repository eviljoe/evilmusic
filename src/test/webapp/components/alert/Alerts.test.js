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

import AlertButton from 'components/alert/AlertButton';
import Alerts from 'components/alert/Alerts';

xdescribe(Alerts.name, () => {
    let alerts = null;
    let _modal = null;
    
    beforeEach(() => {
        _modal = {
            open() {}
        };
        
        alerts = new Alerts(_modal);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(Alerts.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(Alerts.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('open', () => {
        beforeEach(() => {
            spyOn(_modal, 'open').and.stub();
        });
        
        it('opens a modal', () => {
            alerts.open();
            expect(_modal.open).toHaveBeenCalled();
        });
        
        it('passes the buttons to the modal', () => {
            let btns = ['a', 'b'];
            alerts.open(null, null, null, null, btns);
            expect(_modal.open.calls.mostRecent().args[0].resolve.buttons()).toEqual(btns);
        });
        
        it('passes the debug information to the modal', () => {
            let debugInfo = {a: 'A'};
            alerts.open(null, null, debugInfo, null, null);
            expect(_modal.open.calls.mostRecent().args[0].resolve.debugInfo()).toEqual(debugInfo);
        });
        
        it('passes the message to the modal', () => {
            let msg = 'abc';
            alerts.open(msg, null, null, null, null);
            expect(_modal.open.calls.mostRecent().args[0].resolve.message()).toEqual(msg);
        });
        
        it('passes the title to the modal', () => {
            let title = 'ABC';
            alerts.open(null, title, null, null, null);
            expect(_modal.open.calls.mostRecent().args[0].resolve.title()).toEqual(title);
        });
    });
    
    describe('error', () => {
        beforeEach(() => {
            spyOn(alerts, 'okButton').and.stub();
            spyOn(alerts, 'open').and.stub();
        });
        
        it('opens a modal dialog', () => {
            alerts.error();
            expect(alerts.open).toHaveBeenCalled();
        });
        
        it('uses an OK button', () => {
            alerts.error();
            expect(alerts.okButton).toHaveBeenCalled();
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
