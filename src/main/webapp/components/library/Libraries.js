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

import _ from 'lodash';

export default class Libraries {
    constructor(alerts, queues, Library) {
        this.alerts = alerts;
        this.library = null;
        this.queues = queues;
        this.Library = Library;
        
        this.cache = {
            artists: new Set()
        };
        
        this.init();
    }
    
    static get $inject() {
        return ['alerts', 'queues', 'Library'];
    }
    
    static get injectID() {
        return 'libraries';
    }
    
    init() {
        this.load();
    }
    
    /** Loads the contents of the library using a REST call. */
    load() {
        this.library = this.Library.get().$promise.then((library) => {
            this.updateCache(library);
        }).catch((data) => {
            this.alerts.error('Could not get library.', data);
        });
    }
    
    updateCache(lib) {
        this.cache.artists.clear();
        
        _.forEach(lib.songs, (song) => {
            this.cache.artists.add(song.artist);
        });
    }
    
    /**
     * Returns an array containing each unique artist in the library.  The array is not sorted.
     *
     * HACK: This should be a Set instead of an Array, but AngularJS's ngRepeat doesn't seem to work when given a Set.
     * The internet seems to think this should work, so I think Babel might be breaking the functionality.  When
     * EvilMusic is no longer using Babel, try using a Set again.
     *
     * @return {Array<string>} An array containing the unique artists in the library.
     */
    getArtists() { // JOE ju
        let artists = [];
        
        // JOE ? how do i test this if phantom js doesn't support sets?!?
        this.cache.artists.forEach((artist) => artists.push(artist));
        
        return artists;
    }
    
    /**
    * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
    * be reloaded.
    */
    clear() {
        this.library.$promise.then(() => this.clearNow());
    }
    
    clearNow() {
        this.library.$delete().then(() => {
            this.load();
            this.queues.load(true);
        }, (data) => {
            this.alerts.error('Clear library failed.', data);
        });
    }
    
    /**
    * Makes a REST call to rebuild the library.  This will delete all songs from the library.  Next, the server's music
    * directories (from the preferences file) will be read to repopulate the library.  After the library has been
    * rebuild, the it and the queue will be reloaded.
    */
    rebuild() {
        this.library.$promise.then(() => this.rebuildNow());
    }
    
    rebuildNow() {
        this.library.$rebuild().then(() => {
            this.load();
            this.queues.load(true);
        }, (data) => {
            this.alerts.error('Library rebuilding failed.', data);
        });
    }
}
