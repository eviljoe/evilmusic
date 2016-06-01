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
import {EQButtonComponent} from 'components/eq/eq-button/eq-button.component';

describe(EQButtonComponent.name, () => {
    let comp = null;
    let _equalizers = null;
    
    beforeEach(() => {
        _equalizers = {
            reset() {},

            save() {}
        };
        
        comp = new EQButtonComponent(_equalizers);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(EQButtonComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(EQButtonComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('open', () => {
        let _eqDialog;
        
        beforeEach(() => {
            _eqDialog = jasmine.createSpyObj('eqDialog', ['show']);
            comp.eqDialog = _eqDialog;
        });
        
        it('shows the dialog', () => {
            comp.open();
            expect(comp.eqDialog.show).toHaveBeenCalled();
        });
    });
    
    describe('close', () => {
        let _eqDialog;
        
        beforeEach(() => {
            _eqDialog = jasmine.createSpyObj('eqDialog', ['hide']);
            comp.eqDialog = _eqDialog;
        });
        
        it('hides the dialog', () => {
            comp.close();
            expect(_eqDialog.hide).toHaveBeenCalled();
        });
    });
    
    describe('save', () => {
        let saveObserver;
        
        beforeEach(() => {
            spyOn(comp, 'close').and.stub();
            spyOn(_equalizers, 'save').and.returnValue(Observable.create((observer) => saveObserver = observer));
        });
        
        it('saves the equalizer', () => {
            comp.save();
            expect(_equalizers.save).toHaveBeenCalled();
        });
        
        it('closes the dialog after the equalizer has been saved', () => {
            comp.save();
            
            saveObserver.next();
            saveObserver.complete();
            
            expect(comp.close).toHaveBeenCalled();
        });
    });
    
    describe('reset', () => {
        beforeEach(() => {
            spyOn(_equalizers, 'reset').and.stub();
        });
        
        it('resets the equalizer', () => {
            comp.reset();
            expect(_equalizers.reset).toHaveBeenCalled();
        });
    });
});
