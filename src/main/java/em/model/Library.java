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
        super();
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
