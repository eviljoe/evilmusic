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

import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

export class PlaylistCalls {
    constructor(http) {
        this.http = http;
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Http]];
    }
    
    create(name) {
        return this.http.post(`/rest/playlists?name=${name}`)
            .map((res) => res.json())
            .share();
    }
    
    getAll() {
        return this.http.get('/rest/playlists')
            .map((res) => res.json())
            .share();
    }
    
    get(id) {
        return this.http.get(`/rest/playlists/${id}`)
            .map((res) => res.json())
            .share();
    }
    
    setName(id, name) {
        return this.http.put(`/rest/playlists/${id}/name/${name}`)
            .map((res) => res.json())
            .share();
    }
    
    addLast(id, songIDs) {
        let queryParams = [];
        let queryParamsStr = '';
        
        if(songIDs) {
            for(let songID of songIDs) {
                queryParams.push(`songID=${songID}`);
            }
            
            queryParamsStr = queryParams.join('&');
        }
        
        return this.http.put(`/rest/playlists/{id}/last?${queryParamsStr}`)
            .map((res) => res.json())
            .share();
    }
    
    clear(id) {
        return this.http.delete(`/rest/playlists/{id}/elements`)
            .map((res) => res.json())
            .share();
    }
    
    removeElement(playlistID, playlistElemID) {
        return this.http.delete(`/rest/playlists/${playlistID}/elements/${playlistElemID}`)
            .map((res) => res.json())
            .share();
    }
    
    delete(id) {
        return this.http.delete(`/rest/playlists/${id}`)
            .share();
    }
    
    setName(id, name) {
        return this.http.put(`/rest/playlists/${id}/name/${name}`)
            .map((res) => res.json())
            .share();
    }
}
