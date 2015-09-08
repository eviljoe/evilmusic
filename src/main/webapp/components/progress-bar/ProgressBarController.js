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

export default class ProgressBarController {
    constructor($scope, $rootScope, players, emUtils) {
        ProgressBarController.instance = this;
        
        this.$scope = $scope;
        this.players = players;
        this.emUtils = emUtils;
        
        $rootScope.$on(players.playerProgressChangedEventName, () => $scope.updateMeterWidth());
        
        // JOE TODO this whole nightmare needs to be refactored.  I can hear it crying.
        $scope.progressMeterClicked = this.progressMeterClicked;
        $scope.updateMeterWidth = this.updateMeterWidth;
    }

    static get $inject() {
        return ['$scope', '$rootScope', 'players', 'emUtils'];
    }

    static get injectID() {
        return 'ProgressBarController';
    }
    
    static getInstance() {
        return ProgressBarController.instance;
    }
    
    progressMeterClicked(xPos, width) {
        let instance = ProgressBarController.getInstance();
        
        if(instance.emUtils.isNumber(xPos) && instance.emUtils.isNumber(width)) {
            instance.players.seekToPercent(xPos / width);
        }
    }
    
    updateMeterWidth() {
        let instance = ProgressBarController.getInstance();
        let p = instance.players.playerProgress;

        if(instance.emUtils.isNumber(p)) {
            p = Math.max(0, p);
            p = Math.min(100, p);
        } else {
            p = 0;
        }

        instance.$scope.meterElem.width(p + '%');
    }
}
