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
import {NG2Examples} from '../ng2-example/NG2Examples';

export class NG2ChildExampleComponent {
    constructor(ng2Examples) {
        this.text = ng2Examples.getChildText();
        this.tfp = null;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'ng2-child-example',
            templateUrl: 'components/ng2-child-example/ng2-child-example.html',
            viewProviders: [NG2Examples],
            inputs: ['tfp']
        })];
    }
    
    static get parameters() {
        return [[NG2Examples]];
    }
    
    ngOnInit() {
        console.log('tfp', this.tfp);
    }
}

export default NG2ChildExampleComponent;
