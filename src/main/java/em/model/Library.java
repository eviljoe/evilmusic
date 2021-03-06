/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package em.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since v0.1
 * @author eviljoe
 */
public class Library {
    
    private List<SongInfo> songs;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public Library() {
        this(null);
    }
    
    public Library(Collection<SongInfo> songs) {
        super();
        this.setSongs(songs);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public List<SongInfo> getSongs() {
        if(songs == null) {
            songs = new ArrayList<SongInfo>();
        }
        return songs;
    }
    
    public void setSongs(Collection<SongInfo> songs) {
        if(songs == null) {
            this.songs = new ArrayList<SongInfo>();
        } else {
            for(SongInfo song : songs) {
                addSong(song);
            }
        }
    }
    
    public boolean addSong(SongInfo song) {
        return song == null ? false : getSongs().add(song);
    }
}
