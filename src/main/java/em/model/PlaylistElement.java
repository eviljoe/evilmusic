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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class PlaylistElement implements Cloneable, Identifiable {
    
    private static final Logger LOG = Logger.getLogger(PlaylistElement.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_PlaylistElement_to_Playlist"))
    private Playlist playlist;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_PlaylistElement_to_SongInfo"))
    private SongInfo song;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public PlaylistElement() {
        this(null, null);
    }
    
    public PlaylistElement(Integer id) {
        this(id, null);
    }
    
    public PlaylistElement(SongInfo song) {
        this(null, song);
    }
    
    public PlaylistElement(Integer id, SongInfo song) {
        super();
        setID(id);
        setSong(song);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    @Override
    public Integer getID() {
        return id;
    }
    
    @Override
    public void setID(Integer id) {
        this.id = id;
    }
    
    public SongInfo getSong() {
        return song;
    }
    
    public void setSong(SongInfo song) {
        this.song = song;
    }
    
    @JsonIgnore
    public Playlist getPlaylist() {
        return playlist;
    }
    
    @JsonIgnore
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
    
    @Override
    public PlaylistElement clone() {
        PlaylistElement clone;
        
        try {
            clone = (PlaylistElement)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + this.getClass().getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.song = song == null ? null : song.clone();
        }
        
        return clone;
    }
}
