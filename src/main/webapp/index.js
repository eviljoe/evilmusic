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

// HACK This is to make sure that bootstrap has access to jQuery.  This is only necessary until ng2-bootstrap adds
// support for modals.  I can use ng2-bootstrap's modals, remove this.\
window.jQuery = require('jquery'); // eslint-disable-line
require('bootstrap/dist/js/bootstrap'); // eslint-disable-line

import 'zone.js';
import 'reflect-metadata';
import 'rxjs';
import {bootstrap} from '@angular/platform-browser-dynamic';
import {EvilMusicComponent} from 'components/evilmusic/evilmusic.component';

bootstrap(EvilMusicComponent);
