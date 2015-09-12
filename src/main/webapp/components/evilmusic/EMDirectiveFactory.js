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

import EMController from './EMController';
import DirectiveFactory from 'components/utils/DirectiveFactory';

export default class EMDirectiveFactory extends DirectiveFactory {
    constructor() {
        super();
        this.directive.injectID = 'evilMusic';
    }
    
    directive() {
        return {
            restrict: 'E',
            scope: {},
            controller: EMController.injectID,
            controllerAs: 'ctrl',
            templateUrl: 'components/evilmusic/evilmusic.html'
        };
    }
}
