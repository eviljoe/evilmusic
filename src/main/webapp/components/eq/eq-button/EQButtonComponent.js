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

import $ from 'jquery';
import {Component} from '@angular/core';

export class EQButtonComponent {
    constructor($modal) {
        this.$modal = $modal;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-eq-button',
            templateUrl: 'components/eq/eq-button/eq-button.html'
        })];
    }
    
    static get parameters() {
        return [];
    }
    
    openEQ() {
        console.log('$', $); // JOE o
        console.log('$("#myModal")', $('#myModal')); // JOE o
        console.log('$("#myModal").modal', $('#myModal').modal); // JOE o
        // $('#myModal').modal();
    }
}

export default EQButtonComponent;
