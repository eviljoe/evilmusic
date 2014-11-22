package em.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class SongInfo implements Identifiable, Cloneable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String artist;
    private String album;
    private String title;
    private String genre;
    
    /** "year" is a keyword in Derby, so specify a different column name. */
    @Column(name = "albumYear")
    private int year;
    private int seconds;
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
    
    public int getSeconds() {
        return seconds;
    }
    
    public void setSeconds(int seconds) {
        this.seconds = seconds;
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
    
    public File getFile() {
        return file;
    }
    
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
            update(clone);
        }
        
        return clone;
    }
    
    public void update(SongInfo other) {
        other.id = id;
        other.artist = artist;
        other.album = album;
        other.year = year;
        other.title = title;
        other.seconds = seconds;
        other.file = file;
    }
}
