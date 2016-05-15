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

import {Component} from '@angular/core';
import {NG2Examples} from './NG2ExampleService';

export class NG2ExampleComponent {
    constructor(ng2Examples) {
        this.text = ng2Examples.getText();
    }
    
    static get annotations() {
        return [new Component({
            selector: 'ng2-example',
            templateUrl: 'components/ng2-example/ng2-example.html',
            viewProviders: [NG2Examples]
        })];
    }
    
    static get parameters() {
        return [[NG2Examples]];
    }
    
}

export default NG2ExampleComponent;
