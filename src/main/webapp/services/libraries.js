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

import {EventEmitter, Injectable} from '@angular/core';
import _ from 'lodash';

import {LibraryCalls} from './server-calls/library-calls';
import {Alerts} from './alerts';
import {Queues} from './queues';

const SONG_KEY_HASH_PRIME = 37;

export class Libraries {
    constructor(alerts, libraryCalls, queues) {
        this.alerts = alerts;
        this.libraryCalls = libraryCalls;
        this.library = null;
        this.queues = queues;
        
        this.libraryChanges = new EventEmitter();
        this.loadingChanges = new EventEmitter();
        
        this.cache = {
            artists: new Set(),
            albumsForArtists: new Map(),
            songsForAlbum: new Map()
        };
        
        this.init();
    }
    
    static get annotations() {
        return [new Injectable()];
    }
    
    static get parameters() {
        return [[Alerts], [LibraryCalls], [Queues]];
    }
    
    init() {
        this.load();
    }
    
    /** Loads the contents of the library using a REST call. */
    load() {
        this.loading = true;
        
        this.libraryCalls.get().subscribe(
            (library) => this._loaded(library),
            (err) => this.alerts.error('Could not get library.', err)
        );
    }
    
    _loaded(library) {
        this.library = library;
        this.rebuildCache(library);
        this.loading = false;
        this.libraryChanges.emit();
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
     * Returns a set containing each unique artist in the library.
     *
     * @return {Set<string>} A set containing the unique artists in the library.
     */
    getArtists() {
        return new Set(this.cache.artists);
    }
    
    /**
     * Returns a set containing each unique album for the given artist in the library.
     *
     * @return {Set<string>} A set containing the unique albums for the artist.  If the artist cannot be found, or
     * has no albums, an empty set will be returned.
     */
    getAlbumsForArtist(artist) {
        return new Set(this.cache.albumsForArtists.get(artist));
    }
    
    /**
     * Returns a set containing each song for the given artist & album in the library.
     *
     * @return {Set<string>} A set containing the unique songs for the artist & album.  If the artist & album
     * combination cannot be found, or has no songs, an empty set will be returned.
     */
    getSongsForAlbum(artist, album) {
        return new Set(this.cache.songsForAlbum.get(this.getSongKey(artist, album)));
    }
    
    getSongKey(artist, album) {
        let artistHash = artist ? artist.split('').reduce(this.reduceSongKey, 0) : 1;
        let albumHash = album ? album.split('').reduce(this.reduceSongKey, 0) : 1;
        let hash = 0;
        
        hash = SONG_KEY_HASH_PRIME * hash + artistHash;
        hash = SONG_KEY_HASH_PRIME * hash + albumHash;
        
        return hash;
    }
    
    reduceSongKey(prevVal, currVal) {
        let hash = (prevVal << 5 - prevVal) + currVal.charCodeAt(0); // eslint-disable-line no-magic-numbers
        
        return hash & hash;
    }
    
    /**
    * Makes a REST call to delete all of the library's contents.  After the library is cleared, it and the queue will
    * be reloaded.
    */
    clear() {
        this.loading = true;
        
        this.libraryCalls.clear().subscribe(
            (library) => this._cleared(library),
            (err) => this.alerts.error('Clear library failed.', err)
        );
    }
    
    _cleared(library) {
        this.library = library;
        this.queues.load(true);
        this.rebuildCache(this.library);
        this.loading = false;
        this.libraryChanges.emit();
    }
    
    /**
    * Makes a REST call to rebuild the library.  This will delete all songs from the library.  Next, the server's music
    * directories (from the preferences file) will be read to repopulate the library.  After the library has been
    * rebuild, the it and the queue will be reloaded.
    */
    rebuild() {
        this.loading = true;
        
        this.libraryCalls.rebuild().subscribe(
            (library) => this._rebuilt(library),
            (err) => this.alerts.error('Library rebuilding failed.', err)
        );
    }
    
    _rebuilt(library) {
        this.library = library;
        this.queues.load(true);
        this.rebuildCache(this.library);
        this.loading = false;
        this.libraryChanges.emit();
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

export default Libraries;
