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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class QueueElement implements Identifiable, Cloneable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int queueIndex;
    private int playIndex;
    
    @OneToOne(fetch = FetchType.EAGER)
    private SongInfo song;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public QueueElement() {
        this(null, null);
    }
    
    public QueueElement(Integer id) {
        this(id, null);
    }
    
    public QueueElement(SongInfo song) {
        this(null, song);
    }
    
    public QueueElement(Integer id, SongInfo song) {
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
    
    public int getQueueIndex() {
        return queueIndex;
    }
    
    public void setQueueIndex(int queueIndex) {
        this.queueIndex = queueIndex;
    }
    
    public int getPlayIndex() {
        return playIndex;
    }
    
    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }
    
    public SongInfo getSong() {
        return song;
    }
    
    public void setSong(SongInfo song) {
        this.song = song;
    }
    
    @Override
    public QueueElement clone() {
        QueueElement clone;
        
        try {
            clone = (QueueElement)super.clone();
        } catch(CloneNotSupportedException e) {
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.queueIndex = queueIndex;
            clone.playIndex = playIndex;
            clone.song = song == null ? null : song.clone();
        }
        
        return clone;
    }
}
