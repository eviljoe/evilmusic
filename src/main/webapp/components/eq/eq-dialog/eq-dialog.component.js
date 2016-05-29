/*
 * Copyright (C) 2015 Joe Falascino
 * EvilMusic - Web-Based Music Player
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

import {Component} from '@angular/core';
import {EQComponent} from 'components/eq/eq.component';
import {Equalizers} from 'services/equalizers';
import {Modals} from 'services/modals';

export const EQ_DIALOG_ELEMENT_ID = 'em-eq-dialog';

export class EQDialogComponent {
    constructor(equalizers, modals) {
        this.equalizers = equalizers;
        this.modals = modals;
        this.elemID = EQ_DIALOG_ELEMENT_ID;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-eq-dialog',
            templateUrl: 'components/eq/eq-dialog/eq-dialog.html',
            directives: [EQComponent]
        })];
    }
    
    static get parameters() {
        return [[Equalizers], [Modals]];
    }
    
    // TODO this doesn't actually work
    save() {
        this.equalizers.save().then(() => {
            this.$modalInstance.close('save');
        });
    }
    
    reset() {
        this.equalizers.reset();
    }
    
    close() {
        this.modals.hide(EQ_DIALOG_ELEMENT_ID);
    }
}

export default EQDialogComponent;
