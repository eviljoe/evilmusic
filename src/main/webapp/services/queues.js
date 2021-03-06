/*
 * EvilMusic - Web-Based Music Player
 * Copyright (C) 2016 Joe Falascino
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

import {EventEmitter, Injectable} from '@angular/core';
import _ from 'lodash';

import {Alerts} from './alerts';
import {EMUtils} from './emutils';
import {QueueCalls} from './server-calls/queue-calls';

export class Queues {
    constructor(alerts, emUtils, queueCalls) {
        this.alerts = alerts;
        this.emUtils = emUtils;
        this.queueCalls = queueCalls;
        this.q = null;
        
        this.playIndexChanges = new EventEmitter();
        this.loadingChanges = new EventEmitter();
        this.queueChanges = new EventEmitter();
        
        this.init();
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Alerts], [EMUtils], [QueueCalls]];
    }
    
    init() {
        this.load(true);
    }
    
    /**
     * Loads the contents of the queue using a REST call.  A new queue can be loaded, or the same queue can be
     * re-loaded.
     *
     * @param {boolean} loadNew Whether or not to load a new queue or reload the current queue.
     */
    load(loadNew) {
        let id = loadNew ? 'default' : this.q.id;
        
        this.loading = true;
        this.queueCalls.get(id).subscribe(
            (queue) => this._loaded(queue),
            (err) => this.alerts.error('Could not get queue.', err)
        );
    }
    
    _loaded(queue) {
        this.q = queue;
        this.queueChanges.emit(this.q);
        this.loading = false;
    }

    /**
     * Makes a REST call to add the song with the given ID to the end of the queue.  After the song has been enqueued,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be enqueued because there
     * is no way to know which queue should be altered.
     *
     * @param {...number} One or more IDs of the song(s) to be enqueued.
     */
    addLast(...songIDs) {
        this.loading = true;
        
        this.queueCalls.addLast(this.q.id, songIDs).subscribe(
            (queue) => this._addedLast(queue),
            (err) => this.alerts.error('Failed to enqueue last.', err)
        );
    }
    
    _addedLast(queue) {
        this.q = queue;
        this.queueChanges.emit(this.q);
        this.loading = false;
    }
    
    /**
     * Makes a REST call to remove the song at the given queue index from the queue.  After the queue has been updated,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be removed because there
     * is no way to know which queue should be altered.
     *
     * @param {number} queueIndex The index within the queue that should be removed.
     */
    remove(queueIndex) {
        this.loading = true;
        
        this.queueCalls.remove(this.q.id, queueIndex).subscribe(
            (queue) => this._removed(queue),
            (err) => this.alerts.error(`Failed to remove from queue (${queueIndex})`, err)
        );
    }
    
    _removed(queue) {
        this.q = queue;
        this.queueChanges.emit(this.q);
        this.loading = false;
    }

    /**
     * Makes a REST call to empty out the queue.  After the queue has been emptied on the server, the queue will be
     * reloaded.
     */
    clear() {
        this.loading = true;
        
        this.queueCalls.clear(this.q.id).subscribe(
            (queue) => this._cleared(queue),
            (err) => this.alerts.error('Clear queue failed.', err)
        );
    }
    
    _cleared(queue) {
        this.q = queue;
        this.queueChanges.emit(this.q);
        this.loading = false;
    }
    
    /**
     * Returns a song from the queue.
     *
     * @param  {number} queueIndex The queue index of the song to be found.  If null, undefined, or NaN, null will be
     *         returned.
     *
     * @return {Song} If a song can be found at the given queue index, it will be returned.  If a song cannot be found,
     *         null will be returned.
     */
    getSong(queueIndex) {
        let song;

        if(this.emUtils.isNumber(queueIndex) && this.q) {
            song = _.result(_.find(this.q.elements, {queueIndex}), 'song');
        }

        return song || null;
    }
    
    /**
     * Returns queue index for the next song in the queue.  If there is no next song, -1 is returned.
     *
     * @return {int} Returns the index for the next song or -1 if there is no next song.
     */
    getNextSongQueueIndex() {
        let nextIndex = this.q.playIndex + 1;
        
        return nextIndex > this.q.elements.length - 1 ? -1 : nextIndex;
    }
    
    /**
    * Returns queue index for the previous song in the queue.  If there is no previous song, -1 is returned.
    *
    * @return {int} Returns the index for the previous song or -1 if there is no previous song.
     */
    getPreviousSongQueueIndex() {
        let prevIndex = this.q.playIndex + -1;
        
        return prevIndex < 0 ? -1 : prevIndex;
    }
    
    setPlayIndex(playIndex) {
        this.queueCalls.setPlayIndex(this.q.id, playIndex).subscribe(
            (queue) => this._playIndexChanged(playIndex, queue),
            (err) => this.alerts.error('Failed to set the play index.', err)
        );
    }
    
    _playIndexChanged(playIndex, queue) {
        this.q = queue;
        this.queueChanges.emit(this.q);
        this.playIndexChanges.emit(playIndex);
    }
    
    get loading() {
        return this._loading;
    }
    
    set loading(loading) {
        let oldLoading = this._loading;
        
        this._loading = loading;
        this.loadingChanges.emit({
            old: oldLoading,
            new: this._loading
        });
    }
}

export default Queues;
