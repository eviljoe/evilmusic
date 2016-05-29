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

import {EQDialogComponent, EQ_DIALOG_ELEMENT_ID} from 'components/eq/eq-dialog/eq-dialog.component';

describe(EQDialogComponent.name, () => {
    let comp = null;
    let _equalizers = null;
    let _modals = null;
    
    beforeEach(() => {
        _equalizers = {
            reset() {},

            save() {}
        };
        
        _modals = {
            hide() {}
        };
        
        comp = new EQDialogComponent(_equalizers, _modals);
    });
    
    describe('annotations', () => {
        it('returns an array', () => {
            expect(EQDialogComponent.annotations).toEqual(jasmine.any(Array));
        });
    });
    
    describe('parameters', () => {
        it('returns an array', () => {
            expect(EQDialogComponent.parameters).toEqual(jasmine.any(Array));
        });
    });
    
    describe('save', () => {
        // TODO
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
    
    describe('close', () => {
        beforeEach(() => {
            spyOn(_modals, 'hide').and.stub();
        });
        
        it('hides the dialog', () => {
            comp.close();
            expect(_modals.hide).toHaveBeenCalledWith(EQ_DIALOG_ELEMENT_ID);
        });
    });
});
