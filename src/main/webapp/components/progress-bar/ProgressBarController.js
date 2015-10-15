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
    constructor($rootScope, $scope, $timeout, players, emUtils) {
        ProgressBarController.instance = this;
        
        this.$rootScope = $rootScope;
        this.$scope = $scope;
        this.$timeout = $timeout;
        this.players = players;
        this.emUtils = emUtils;
        
        this.init();
    }

    static get $inject() {
        return ['$rootScope', '$scope', '$timeout', 'players', 'emUtils'];
    }

    static get injectID() {
        return 'ProgressBarController';
    }
    
    init() {
        this.$rootScope.$on(this.players.playerProgressChangedEventName, () => this.draw());
        this.$timeout(() => this.draw(), 0);
    }
    
    barClicked(event) {
        event.stopPropagation();
        this.players.seekToPercent(event.offsetX / this.getCanvasWidth());
    }
    
    getPlayerProgress() {
        let p = this.players.playerProgress;

        if(this.emUtils.isNumber(p)) {
            p = Math.max(0, p);
            p = Math.min(100, p);
        } else {
            p = 0;
        }
        
        return p;
    }
    
    getCanvas() {
        return this.$scope.canvas[0];
    }
    
    getCanvasWidth() {
        return this.$scope.canvasContainer ? this.$scope.canvasContainer.width() : 0;
    }
    
    draw() {
        let canvas = this.getCanvas();
        let ctx = canvas.getContext('2d');
        let barWidth = this.getPlayerProgress() / 100.0 * canvas.width;

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        this.drawMeter(ctx, 0, 0, barWidth, canvas.height);
        this.drawGutter(ctx, barWidth, 0, canvas.width - barWidth, canvas.height);
    }
    
    drawMeter(ctx, x, y, width, height) {
        ctx.fillStyle = 'blue';
        ctx.fillRect(x, y, width, height);
    }
    
    drawGutter(ctx, x, y, width, height) {
        ctx.fillStyle = 'lightgrey';
        ctx.fillRect(x, y, width, height);
    }
}
