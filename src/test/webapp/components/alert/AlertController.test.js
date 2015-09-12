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

import AlertController from 'components/alert/AlertController';

describe(AlertController.name, () => {
    let ctrl = null;
    let _modalInstance = null;
    
    beforeEach(() => {
        _modalInstance = {
            close() {},
            dismiss() {}
        };
        ctrl = new AlertController(_modalInstance);
    });
    
    describe('$inject', () => {
        it('defines injections', () => {
            expect(AlertController.$inject).toEqual(jasmine.any(Array));
        });
    });
    
    describe('injectID', () => {
        it('defines an injection ID', () => {
            expect(AlertController.injectID).toEqual(jasmine.any(String));
        });
    });
    
    describe('buttonClicked', () => {
        beforeEach(() => {
            spyOn(_modalInstance, 'close').and.stub();
            spyOn(_modalInstance, 'dismiss').and.stub();
        });
        
        it('closes the modal with the given button\'s ID when given a button that gets resolved', () => {
            ctrl.buttonClicked({id: 'closeme', resolve: true});
            expect(_modalInstance.close).toHaveBeenCalledWith('closeme');
        });
        
        it('dismisses the modal with the given button\'s ID when given a button that does not get resolved', () => {
            ctrl.buttonClicked({id: 'dismissme', resolve: false});
            expect(_modalInstance.dismiss).toHaveBeenCalledWith('dismissme');
        });
    });
});
