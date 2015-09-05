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
import ProgressBarController from './ProgressBarController';

export default class ProgressBarDirectiveFactory extends DirectiveFactory {
    constructor() {
        super();
        this.directive.injectID = 'emProgressBar';
    }
    
    directive() {
        return {
            restrict: 'E',
            scope: {},
            templateUrl: '/components/progress-bar/progress-bar.html',
            link: ProgressBarDirectiveFactory.link,
            controller: ProgressBarController.injectID,
            controllerAs: 'ctrl'
        };
    }
    
    static link(scope, element, attrs) {
        scope.barElem = element;
        scope.gutterElem = angular.element(element[0].querySelector('.em-progress-gutter'));
        scope.meterElem = angular.element(element[0].querySelector('.em-progress-meter'));

        scope.gutterElem.on('click', function(event) {
            event.stopPropagation();
            scope.progressMeterClicked(event.offsetX, scope.gutterElem.width());
        });

        scope.meterElem.on('click', function(event) {
            event.stopPropagation();
            scope.progressMeterClicked(event.offsetX, scope.gutterElem.width());
        });

        scope.updateMeterWidth();
    }
}
