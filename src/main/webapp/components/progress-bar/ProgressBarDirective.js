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

import angular from 'angular';
import ProgressBarController from './ProgressBarController';

export default class ProgressBarDirective {
    constructor() {
        this.restrict = 'E';
        this.scope = {};
        this.templateUrl = 'components/progress-bar/progress-bar.html';
        this.controller = ProgressBarController.injectID;
        this.controllerAs = 'ctrl';
        this.angular = angular;
        this.link = (scope, element, attrs, ctrl) => this._link(scope, element, attrs, ctrl);
    }
        
    _link(scope, element, attrs, ctrl) {
        scope.canvasContainer = this.getElement(element[0], '.em-progress-bar-container');
        scope.canvas = this.getElement(element[0], '.em-progress-bar-canvas');
        scope.canvas.on('click', (event) => ctrl.barClicked(event));
        
        ctrl.draw();
    }
    
    getElement(rootElem, selector) {
        return this.angular.element(rootElem.querySelector(selector));
    }
}
