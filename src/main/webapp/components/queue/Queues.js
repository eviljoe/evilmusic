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

export default class Queues {
    constructor(emUtils, Queue) {
        this.q = null;
        this.emUtils = emUtils;
        this.Queue = Queue;
        
        this.load(true);
    }
    
    static get $inject() {
        return ['emUtils', 'Queue'];
    }
    
    static get injectID() {
        return 'queues';
    }
    
    /**
     * Loads the contents of the queue using a REST call.  A new queue can be loaded, or the same queue can be
     * re-loaded.
     *
     * @param {boolean} loadNew Whether or not to load a new queue or reload the current queue.
     */
    load(loadNew) {
        let id = loadNew ? 'default' : this.q.id;

        this.q = this.Queue.get({id: id});
        this.q.$promise.catch((data) => {
            alert('Could not get queue.\n\n' + JSON.stringify(data));
        });
    }

    /**
     * Makes a REST call to add the song with the given ID to the end of the queue.  After the song has been enqueued,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be enqueued because there
     * is no way to know which queue should be altered.
     *
     * @param {number} songID The ID of the song to be enqueued.
     */
    addLast(songID) {
        this.q.$promise.then(() => {
            this.q.$addLast({id: this.q.id, songIDs: songID}).then(() => {
                this.load(false);
            },
            (data) => {
                alert('Failed to enqueue last.\n\n' + JSON.stringify(data));
            });
        });
    }

    /**
     * Makes a REST call to remove the song at the given queue index from the queue.  After the queue has been updated,
     * the queue will be reloaded.  If a queue has not already been loaded, the song will not be removed because there
     * is no way to know which queue should be altered.
     *
     * @param {number} queueIndex The index within the queue that should be removed.
     */
    remove(queueIndex) {
        this.q.$promise.then(() => {
            this.q.$remove({id: this.q.id, qIndex: this.queueIndex}).then(() => {
                this.load(false);
            },
            (data) => {
                alert('Failed to remove from queue (' + this.queueIndex + ')\n\n' + JSON.stringify(data));
            });
        });
    }

    /**
     * Makes a REST call to empty out the queue.  After the queue has been emptied on the server, the queue will be
     * reloaded.
     */
    clear() {
        this.q.$promise.then(() => {
            this.q.$clear().then(() => {
                this.load(false);
            },
            (data) => {
                alert('Clear queue failed.\n\n' + JSON.stringify(data));
            });
        });
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
        let song = null;

        if(this.emUtils.isNumber(queueIndex) && this.q && this.q.elements) {
            for(let x = 0; x < this.q.elements.length; x++) {
                let elem = this.q.elements[x];

                if(elem && elem.queueIndex === queueIndex) {
                    song = elem.song;
                }
            }
        }

        return song;
    }
}
