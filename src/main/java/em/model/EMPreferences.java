package em.model;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferences {
    
    private String[] musicDirectories;
    private String metaFLACCommand;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EMPreferences() {
        super();
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public String[] getMusicDirectories() {
        return musicDirectories;
    }
    
    public void setMusicDirectories(String[] musicDirectories) {
        this.musicDirectories = musicDirectories;
    }
    
    public String getMetaFLACCommand() {
        return metaFLACCommand;
    }
    
    public void setMetaFLACCommand(String metaflacCommand) {
        this.metaFLACCommand = metaflacCommand;
    }
}
