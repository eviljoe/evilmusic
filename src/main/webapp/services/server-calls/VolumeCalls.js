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

import {Http} from '@angular/http';
import {Injectable} from '@angular/core';

export class VolumeCalls {
    constructor(http) {
        this.http = http;
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Http]];
    }
    
    get() {
        return this.http.get('/rest/config/volume')
            .map((res) => res.json());
    }
    
    save(volume) {
        return this.http.put(`/rest/config/volume/${volume}`)
            .map((res) => res.json());
    }
}

export default VolumeCalls;
