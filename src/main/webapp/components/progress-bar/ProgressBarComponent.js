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

import {Component, ElementRef} from '@angular/core';

import {EMUtils} from 'services/EMUtils';
import {Players} from 'services/Players';

const MIN_PROGRESS = 0;
const MAX_PROGRESS = 100;

export class ProgressBarComponent {
    constructor(elem, emUtils, players) {
        this.elem = elem.nativeElement;
        this.emUtils = emUtils;
        this.players = players;
        this.setTimeout = window.setTimeout;
    }
    
    static get annotations() {
        return [new Component({
            selector: 'em-progress-bar',
            templateUrl: 'components/progress-bar/progress-bar.html'
        })];
    }
    
    static get parameters() {
        return [[ElementRef], [EMUtils], [Players]];
    }

    ngOnInit() {
        this.players.playerProgressChanges.subscribe(() => this.draw());
        this.canvasContainer = this.elem.querySelector('.em-progress-bar-container');
        this.canvas = this.elem.querySelector('.em-progress-bar-canvas');
        this.setTimeout(() => this.draw(), 0);
    }
    
    barClicked(event) {
        event.stopPropagation();
        this.players.seekToPercent(event.offsetX / this.getContainerWidth());
    }
    
    getPlayerProgress() {
        let p = this.players.playerProgress;

        if(this.emUtils.isNumber(p)) {
            p = Math.max(MIN_PROGRESS, p);
            p = Math.min(MAX_PROGRESS, p);
        } else {
            p = 0;
        }
        
        return p;
    }
    
    getContainerWidth() {
        return this.canvasContainer ? this.canvasContainer.clientWidth : 0;
    }
    
    draw() {
        let canvas = this.canvas;
        let ctx = canvas.getContext('2d');
        let barWidth = Math.ceil(
            this.getPlayerProgress() / 100.0 * canvas.width); // eslint-disable-line no-magic-numbers

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

export default ProgressBarComponent;
