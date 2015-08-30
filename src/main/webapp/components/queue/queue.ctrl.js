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

let injections = ['queues', 'player'];
function Controller(queues, player) {
    'use strict';
    
    let that = this;
    
    that.getQueue = getQueue;
    that.clear = clear;
    that.play = play;
    that.remove = remove;
    
    function getQueue() {
        return queues.q;
    }
    
    function clear() {
        queues.clear();
    }
    
    function play(index) {
        player.play(index);
    }
    
    function remove(index) {
        queues.remove(index);
    }
}

Controller.$inject = injections;
export default {
    id: 'EMQueueController',
    Controller: Controller
};
