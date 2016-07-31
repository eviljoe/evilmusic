/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2016 Joe Falascino
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

import {Component, ViewChild} from '@angular/core';
import {MODAL_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

import {EQComponent} from 'components/eq/eq.component';
import {LoadingOverlayComponent} from 'components/loading-overlay/loading-overlay.component';

import {Equalizers} from 'services/equalizers';

export class EQButtonComponent {
    constructor(equalizers) {
        this.equalizers = equalizers;
        
        this.init();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-eq-button',
            templateUrl: 'components/eq/eq-button/eq-button.html',
            directives: [MODAL_DIRECTIVES, EQComponent, LoadingOverlayComponent],
            queries: {
                eqDialog: new ViewChild('eqDialog')
            }
        })];
    }
    
    static get parameters() {
        return [[Equalizers]];
    }
    
    init() {
        this._equalizerLoadingChanged();
        this.equalizers.loadingChanges.subscribe(() => this._equalizerLoadingChanged());
    }
    
    _equalizerLoadingChanged() {
        this.loading = this.equalizers.loading;
    }
    
    open() {
        this.eqDialog.show();
    }
    
    close() {
        this.eqDialog.hide();
    }
    
    save() {
        this.equalizers.save().subscribe(() => this.close());
    }
    
    reset() {
        this.equalizers.reset();
    }
}

export default EQButtonComponent;
