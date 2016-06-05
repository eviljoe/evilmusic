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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
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
public class Queue implements Identifiable, Cloneable {
    
    private static final Logger LOG = Logger.getLogger(Queue.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int playIndex;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "queue")
    private List<QueueElement> elements;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public Queue() {
        super();
        setPlayIndex(-1);
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
    
    public int getPlayIndex() {
        return playIndex;
    }
    
    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
        updateIndices();
    }
    
    public List<QueueElement> getElements() {
        return elements;
    }
    
    public void setElements(Collection<QueueElement> elements) {
        this.elements = EMUtils.toList(elements);
        updateIndices();
    }
    
    @Transient
    public QueueElement getElement(int index) throws IndexOutOfBoundsException {
        return elements == null ? null : elements.get(index);
    }
    
    @Transient
    public SongInfo getSong(int index) throws IndexOutOfBoundsException {
        return elements == null ? null : elements.get(index).getSong();
    }
    
    @Transient
    public boolean addSongLast(SongInfo song) {
        return addSongLast(song, true);
    }
    
    @Transient
    public boolean addSongsLast(Collection<SongInfo> songs) {
        boolean altered = false;
        
        if(songs != null) {
            for(SongInfo song : songs) {
                altered = addSongLast(song, false) || altered;
            }
            
            if(altered) {
                updateIndices();
            }
        }
        
        return altered;
    }
    
    @Transient
    private boolean addSongLast(SongInfo song, boolean updateIndices) {
        boolean added = false;
        
        if(song != null) {
            added = addElementLast(new QueueElement(song), updateIndices);
        }
        
        return added;
    }
    
    @Transient
    private boolean addElementLast(QueueElement element, boolean updateIndices) {
        boolean added = false;
        
        if(element != null) {
            if(elements == null) {
                elements = new ArrayList<>();
            }
            
            added = elements.add(element);
            
            if(added) {
                element.setQueue(this);
                if(updateIndices) {
                    updateIndices();
                }
            }
        }
        
        return added;
    }
    
    @Transient
    public void clearElements() {
        if(EMUtils.hasValues(elements)) {
            elements.clear();
            updateIndices();
        }
    }
    
    public QueueElement removeElement(int index) {
        final QueueElement removed = elements == null ? null : elements.remove(index);
        
        if(removed != null) {
            updateIndices();
        }
        
        return removed;
    }
    
    @Transient
    public int size() {
        return elements == null ? 0 : elements.size();
    }
    
    @Transient
    private void updateIndices() {
        if(EMUtils.hasValues(elements)) {
            int qI = 0;
            int pI = Math.max(0, getPlayIndex());
            
            pI = -Math.min(pI, size() - 1);
            
            for(QueueElement element : elements) {
                element.setQueueIndex(qI++);
                element.setPlayIndex(pI++);
            }
        }
    }
    
    @Override
    @Transient
    public Queue clone() {
        Queue clone = null;
        
        try {
            clone = (Queue)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + Queue.class.getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.playIndex = playIndex;
            
            if(elements == null) {
                clone.elements = elements;
            } else {
                clone.elements = new ArrayList<>();
                for(QueueElement element : elements) {
                    clone.elements.add(element.clone());
                }
            }
        }
        
        return clone;
    }
}
