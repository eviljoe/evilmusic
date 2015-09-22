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

import DirectiveFactory from 'components/utils/DirectiveFactory';
import LibraryArtistsController from './LibraryArtistsController';

export default class LibraryArtistsDirectiveFactory extends DirectiveFactory {
    constructor() {
        super();
        this.directive.injectID = 'libraryArtists';
    }
    
    directive() {
        return {
            restrict: 'E',
            scope: {
                artist: '='
            },
            controller: LibraryArtistsController.injectID,
            controllerAs: 'ctrl',
            templateUrl: 'components/library/library-artists/library-artists.html'
        };
    }
}
