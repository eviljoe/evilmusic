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
            artists: new Set(),
            albumsForArtists: new Map(),
            songsForAlbum: new Map()
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
        this.library = this.Library.get();
        this.library.$promise.then((library) => {
            this.rebuildCache(library);
        }).catch((data) => {
            this.alerts.error('Could not get library.', data);
        });
    }
    
    rebuildCache(lib) {
        this.cache.artists.clear();
        this.cache.albumsForArtists.clear();
        this.cache.songsForAlbum.clear();
        
        _.forEach(lib.songs, (song) => {
            this.cacheArtist(song);
            this.cacheAlbum(song);
            this.cacheSong(song);
        });
    }
    
    cacheArtist(song) {
        this.cache.artists.add(song.artist);
    }
    
    cacheAlbum(song) {
        let albums = this.cache.albumsForArtists.get(song.artist) || new Set();
        
        albums.add(song.album);
        this.cache.albumsForArtists.set(song.artist, albums);
    }
    
    cacheSong(song) {
        let key = this.getSongKey(song.artist, song.album);
        let songs = this.cache.songsForAlbum.get(key) || new Set();
        
        songs.add(song);
        this.cache.songsForAlbum.set(key, songs);
    }
    
    /**
     * Returns an array containing each unique artist in the library.  The array is not sorted.
     *
     * HACK: The artists should be a Set instead of an Array, but AngularJS's ngRepeat doesn't seem to work when given a
     * Set.  The internet seems to think this should work, so I think Babel might be breaking the functionality.  When
     * EvilMusic is no longer using Babel, try using a Set again.
     *
     * @return {Array<string>} An array containing the unique artists in the library.
     */
    getArtists() {
        let artists = [];
        
        this.cache.artists.forEach((artist) => artists.push(artist));
        
        return artists;
    }
    
    /**
     * Returns an array containing each unique album for the given artist in the library.  The array is not sorted.
     *
     * HACK: The albums should be a Set instead of an Array, but AngularJS's ngRepeat doesn't seem to work when given a
     * Set.  The internet seems to think this should work, so I think Babel might be breaking the functionality.  When
     * EvilMusic is no longer using Babel, try using a Set again.
     *
     * @return {Array<string>} An array containing the unique albums for the artist.  If the artist cannot be found, or
     * has no albums, an empty array will be returned.
     */
    getAlbumsForArtist(artist) {
        let set = this.cache.albumsForArtists.get(artist);
        let albums = [];
        
        if(set) {
            set.forEach((album) => albums.push(album));
        }
        
        return albums;
    }
    
    /**
     * Returns an array containing each song for the given artist & album in the library.  The array is not sorted.
     *
     * HACK: The songs should be a Set instead of an Array, but AngularJS's ngRepeat doesn't seem to work when given a
     * Set.  The internet seems to think this should work, so I think Babel might be breaking the functionality.  When
     * EvilMusic is no longer using Babel, try using a Set again.
     *
     * @return {Array<string>} An array containing the unique songs for the artist & album.  If the artist & album
     * combination cannot be found, or has no songs, an empty array will be returned.
     */
    getSongsForAlbum(artist, album) {
        let set = this.cache.songsForAlbum.get(this.getSongKey(artist, album));
        let songs = [];
        
        if(set) {
            set.forEach((song) => songs.push(song));
        }
        
        return songs;
    }
    
    getSongKey(artist, album) {
        let artistHash = artist ? artist.split('').reduce(this.reduceSongKey, 0) : 1;
        let albumHash = album ? album.split('').reduce(this.reduceSongKey, 0) : 1;
        let hash = 0;
        
        hash = 37 * hash + artistHash;
        hash = 37 * hash + albumHash;
        
        return hash;
    }
    
    reduceSongKey(prevVal, currVal) {
        let hash = ((prevVal << 5) - prevVal) + currVal.charCodeAt(0);
        
        return hash & hash;
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
