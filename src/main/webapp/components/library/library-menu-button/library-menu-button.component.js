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

import {Component} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {DROPDOWN_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';

import {Libraries} from 'services/libraries';

export class LibraryMenuButtonComponent {
    constructor(libraries) {
        this.libraries = libraries;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-library-menu-button',
            templateUrl: 'components/library/library-menu-button/library-menu-button.html',
            directives: [DROPDOWN_DIRECTIVES, CORE_DIRECTIVES]
        })];
    }
    
    static get parameters() {
        return [[Libraries]];
    }
}
