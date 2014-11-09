package em.model;

import java.util.List;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferences {
    
    private List<String> musicDirectories;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EMPreferences() {
        super();
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public List<String> getMusicDirectories() {
        return musicDirectories;
    }
    
    public void setMusicDirectories(List<String> musicDirectories) {
        this.musicDirectories = musicDirectories;
    }
}
