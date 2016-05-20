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

// import angular from 'angular';
import {EQDialogComponent} from 'components/eq/eq-dialog/EQDialogComponent';

xdescribe(EQDialogComponent.name, () => {
    let ctrl = null;
    let _modalInstance = null;
    let _equalizers = null;
    let $q = null;
    let $rootScope = null;
    
    beforeEach(angular.mock.inject((_$q_, _$rootScope_) => {
        $q = _$q_;
        $rootScope = _$rootScope_;
        
        _modalInstance = {
            close() {},

            dismiss() {}
        };
        _equalizers = {
            reset() {},

            save() {}
        };
        
        ctrl = new EQDialogController(_modalInstance, _equalizers);
    }));
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(EQDialogController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(EQDialogController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('save', () => {
        let saveDefer = null;
        
        beforeEach(() => {
            saveDefer = $q.defer();
            spyOn(_equalizers, 'save').and.returnValue(saveDefer.promise);
            spyOn(_modalInstance, 'close').and.stub();
        });
        
        it('saves the equalizer', () => {
            ctrl.save();
            expect(_equalizers.save).toHaveBeenCalled();
        });
        
        it('closes the dialog if the save finishes successfully', () => {
            ctrl.save();
            
            saveDefer.resolve();
            $rootScope.$apply();
            
            expect(_modalInstance.close).toHaveBeenCalled();
        });
        
        it('does not close the dialog if the save fails', () => {
            ctrl.save();
            
            saveDefer.reject();
            $rootScope.$apply();
            
            expect(_modalInstance.close).not.toHaveBeenCalled();
        });
    });
    
    describe('reset', () => {
        beforeEach(() => {
            spyOn(_equalizers, 'reset').and.stub();
        });
        
        it('resets the equalizer', () => {
            ctrl.reset();
            expect(_equalizers.reset).toHaveBeenCalled();
        });
    });
    
    describe('close', () => {
        beforeEach(() => {
            spyOn(_modalInstance, 'dismiss').and.stub();
        });
        
        it('dismisses the dialog', () => {
            ctrl.close();
            expect(_modalInstance.dismiss).toHaveBeenCalledWith('close');
        });
    });
});
