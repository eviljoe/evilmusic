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

import LibraryController from './LibraryController';
import LibraryDirectiveFactory from './LibraryDirectiveFactory';
import LibraryAlbumsController from './library-albums/LibraryAlbumsController';
import LibraryAlbumsDirectiveFactory from './library-albums/LibraryAlbumsDirectiveFactory';
import LibraryArtistsController from './library-artists/LibraryArtistsController';
import LibraryArtistsDirectiveFactory from './library-artists/LibraryArtistsDirectiveFactory';
import Libraries from './Libraries';

export default function(emApp) {
    'use strict';
    
    return emApp
        .directive(LibraryDirectiveFactory)
        .controller(LibraryController)
        .directive(LibraryAlbumsDirectiveFactory)
        .controller(LibraryAlbumsController)
        .directive(LibraryArtistsDirectiveFactory)
        .controller(LibraryArtistsController)
        .service(Libraries);
}
