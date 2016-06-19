/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class Playlist implements Cloneable, Identifiable {
    
    private static final Logger LOG = Logger.getLogger(Playlist.class.getName());
    public static final int NAME_MAX_LENGTH = 200;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(length = NAME_MAX_LENGTH)
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "playlist")
    private List<PlaylistElement> elements;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public Playlist() {
        super();
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<PlaylistElement> getElements() {
        return elements;
    }
    
    public void setElements(Collection<PlaylistElement> elements) {
        this.elements = EMUtils.toList(elements);
    }
    
    @Transient
    public PlaylistElement getElement(int index) throws IndexOutOfBoundsException {
        return elements == null ? null : elements.get(index);
    }
    
    @Transient
    public SongInfo getSong(int index) throws IndexOutOfBoundsException {
        return elements == null ? null : elements.get(index).getSong();
    }
    
    @Transient
    public boolean addSongLast(SongInfo song) {
        boolean added = false;
        
        if(song != null) {
            added = addElementLast(new PlaylistElement(song));
        }
        
        return added;
    }
    
    @Transient
    public boolean addSongsLast(Collection<SongInfo> songs) {
        boolean altered = false;
        
        if(songs != null) {
            for(SongInfo song : songs) {
                altered = addSongLast(song) || altered;
            }
        }
        
        return altered;
    }
    
    @Transient
    boolean addElementLast(PlaylistElement element) {
        boolean added = false;
        
        if(element != null) {
            if(elements == null) {
                elements = new ArrayList<>();
            }
            
            added = elements.add(element);
            
            if(added) {
                element.setPlaylist(this);
            }
        }
        
        return added;
    }
    
    @Transient
    public void clearElements() {
        if(EMUtils.hasValues(elements)) {
            elements.clear();
        }
    }
    
    public PlaylistElement removeElement(int index) throws IndexOutOfBoundsException {
        return elements == null ? null : elements.remove(index);
    }
    
    public PlaylistElement removeElementByID(int id) {
        final Iterator<PlaylistElement> it = elements.iterator();
        PlaylistElement removed = null;
        
        while(removed == null && it.hasNext()) {
            final PlaylistElement elem = it.next();
            
            if(elem.getID().intValue() == id) {
                it.remove();
                removed = elem;
            }
        }
        
        return removed;
    }
    
    @Transient
    public int size() {
        return elements == null ? 0 : elements.size();
    }
    
    @Override
    public Playlist clone() {
        Playlist clone = null;
        
        try {
            clone = (Playlist)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + Playlist.class.getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.name = name;
            
            if(elements != null) {
                clone.elements = new ArrayList<>();
                for(PlaylistElement element : elements) {
                    clone.elements.add(element.clone());
                }
            }
        }
        
        return clone;
    }
}
