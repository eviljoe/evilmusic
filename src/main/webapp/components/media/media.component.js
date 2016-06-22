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
import {TAB_DIRECTIVES} from 'ng2-bootstrap';

import {LibraryComponent} from 'components/library/library.component';
import {PlaylistsComponent} from 'components/playlists/playlists.component';

export class MediaComponent {
    static get annotations() { // JOE ju
        return [new Component({
            selector: 'em-media',
            templateUrl: 'components/media/media.html',
            directives: [TAB_DIRECTIVES, LibraryComponent, PlaylistsComponent]
        })];
    }
    
    static get parameters() { // JOE ju
        return [];
    }
}
