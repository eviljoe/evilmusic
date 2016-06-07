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

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class SongInfo implements Identifiable, Cloneable {
    
    @Id
    private Integer id;
    
    private String artist;
    private String album;
    private String title;
    private String genre;
    
    /** "year" is a keyword in Derby, so specify a different column name. */
    @Column(name = "albumYear")
    private int year;
    private int millis;
    private int trackNumber;
    private int sampleRate;
    private int sampleCount;
    
    private File file;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public SongInfo() {
        this(null);
    }
    
    public SongInfo(Integer id) {
        super();
        setID(id);
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
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getMillis() {
        return millis;
    }
    
    public void setMillis(int seconds) {
        this.millis = seconds;
    }
    
    public int getSampleRate() {
        return sampleRate;
    }
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public int getSampleCount() {
        return sampleCount;
    }
    
    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }
    
    public int getTrackNumber() {
        return trackNumber;
    }
    
    public void setTrackNumber(int track) {
        this.trackNumber = track;
    }
    
    @JsonIgnore
    public File getFile() {
        return file;
    }
    
    @JsonIgnore
    public void setFile(File file) {
        this.file = file;
    }
    
    @Override
    public SongInfo clone() {
        SongInfo clone;
        
        try {
            clone = (SongInfo)super.clone();
        } catch(CloneNotSupportedException e) {
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.artist = artist;
            clone.album = album;
            clone.year = year;
            clone.title = title;
            clone.millis = millis;
            clone.file = file;
        }
        
        return clone;
    }
}
