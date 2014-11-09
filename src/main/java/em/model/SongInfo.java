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
public class SongInfo implements Identifiable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String artist;
    private String album;
    private String name;
    
    /** "year" is a keyword in Derby, so specify a different column name. */
    @Column(name = "albumYear")
    private int year;
    private int seconds;
    
    private File file;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public SongInfo() {
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    
    public File getFile() {
        return file;
    }
    
    public void setFile(File file) {
        this.file = file;
    }
}
