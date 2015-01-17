package em.model;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferences implements Cloneable {
    
    private static final Logger LOG = Logger.getLogger(EMPreferences.class.getName());
    
    private String[] musicDirectories;
    private String metaFLACCommand;
    private String databaseHome;
    private Boolean databaseRollback;
    private Integer serverPort;
    
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
    
    public String getDatabaseHome() {
        return databaseHome;
    }
    
    public void setDatabaseHome(String databaseHome) {
        this.databaseHome = databaseHome;
    }
    
    public Boolean getDatabaseRollback() {
        return databaseRollback;
    }
    
    public void setDatabaseRollback(Boolean databaseRollback) {
        this.databaseRollback = databaseRollback;
    }
    
    public Integer getServerPort() {
        return serverPort;
    }
    
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
    
    @Override
    public EMPreferences clone() {
        EMPreferences clone = null;
        
        try {
            clone = (EMPreferences)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + EMPreferences.class.getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.musicDirectories =
                    musicDirectories == null ? null : Arrays.copyOf(musicDirectories, musicDirectories.length);
            clone.metaFLACCommand = metaFLACCommand;
            clone.databaseHome = databaseHome;
            clone.databaseRollback = databaseRollback;
            clone.serverPort = serverPort;
        }
        
        return clone;
    }
}
