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

import {Http} from '@angular/http';
import {Injectable} from '@angular/core';

export class QueueCalls {
    constructor(http) {
        this.http = http;
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Http]];
    }
    
    // TODO ju
    get(id) {
        return this.http.get(`/rest/queue/${id}`)
            .map((res) => res.json());
    }
    
    // TODO ju
    addLast(id, ...songIDs) {
        let queryParams = [];
        let queryParamsStr = '';
        
        if(songIDs) {
            for(let songID of songIDs) {
                queryParams.push(`songID=${songID}`);
            }
            
            queryParamsStr = queryParams.join('&');
        }
        
        return this.http.put(`/rest/queue/${id}/last?${queryParamsStr}`)
            .map((res) => res.json());
    }
    
    // TODO ju
    remove(id, qIndex) {
        return this.http.delete(`/rest/queue/${id}/queueindex/${qIndex}`)
            .map((res) => res.json());
    }
    
    // TODO ju
    clear(id) {
        return this.http.delete(`/rest/queue/${id}/elements`)
            .map((res) => res.json());
    }
    
    // TODO ju
    setPlayIndex(id, playIndex) {
        return this.http.put(`/rest/queue/${id}/playindex/${playIndex}`)
            .map((res) => res.json());
    }
}

export default QueueCalls;