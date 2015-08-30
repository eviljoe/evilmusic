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

let injections = ['$scope', '$rootScope', 'player', 'emUtils'];

function Controller($scope, $rootScope, player, emUtils) {
    'use strict';

    $scope.progressMeterClicked = function(xPos, width) {
        if(emUtils.isNumber(xPos) && emUtils.isNumber(width)) {
            $scope.onseek(xPos / width);
        }
    };

    $scope.updateMeterWidth = function() {
        let p = player.playerProgress;

        if(emUtils.isNumber(p)) {
            p = Math.max(0, p);
            p = Math.min(100, p);
        } else {
            p = 0;
        }

        $scope.meterElem.width(p + '%');
    };

    $rootScope.$on(player.playerProgressChangedEventName, $scope.updateMeterWidth);
}

Controller.$inject = injections;
export default {
    id: 'EMProgressBarController',
    Controller: Controller
};
