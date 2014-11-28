package em.prefs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import em.model.EMPreferences;
import em.utils.LibraryUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferencesManager {
    
    private static final Logger LOG = Logger.getLogger(LibraryUtils.class.getName());
    
    private static final String DEFAULT_PREFS_FILE_NAME = "em.properties";
    static final String DELIMITER = ";";
    
    static EMPreferencesManager instance;
    
    private volatile EMPreferences preferences;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private EMPreferencesManager() {
        super();
    }
    
    /* ******************* */
    /* Singleton Functions */
    /* ******************* */
    
    public static synchronized EMPreferencesManager getInstance() {
        if(instance == null) {
            instance = new EMPreferencesManager();
        }
        return instance;
    }
    
    /* ********************* */
    /* Preferences Functions */
    /* ********************* */
    
    public synchronized EMPreferences loadPreferences() throws IOException {
        final Properties props = new Properties();
        final InputStream in = EMPreferencesManager.class.getClassLoader().getResourceAsStream(DEFAULT_PREFS_FILE_NAME);
        
        if(in != null) {
            props.load(in);
        }
        
        return createPreferences(props);
    }
    
    EMPreferences createPreferences(Properties props) {
        final EMPreferences emPrefs = new EMPreferences();
        
        if(props != null) {
            final String[] musicDirs = getStringArray(props, EMPreferencesKey.MUSIC_DIRECTORIES);
            final String metaflac = props.getProperty(EMPreferencesKey.METAFLAC.toString());
            final String dbHome = props.getProperty(EMPreferencesKey.DATABASE_HOME.toString());
            final Boolean dbRollback = getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
            
            emPrefs.setMusicDirectories(musicDirs);
            emPrefs.setMetaFLACCommand(metaflac);
            emPrefs.setDatabaseHome(dbHome);
            emPrefs.setDatabaseRollback(dbRollback);
            
            LOG.info("=== Loaded Preferences ===");
            LOG.info("Music Directories: " + Arrays.toString(musicDirs));
            LOG.info("MetaFLAC Command:  " + metaflac);
            LOG.info("Database Home:     " + dbHome);
            LOG.info("Database Rollback: " + dbRollback);
            LOG.info("===== End Preferences ====");
        }
        
        return emPrefs;
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    String[] getStringArray(Properties props, EMPreferencesKey key) {
        String[] array = null;
        
        if(props != null && key != null) {
            final String fullValue = props.getProperty(key.toString());
            
            if(fullValue == null || fullValue.length() == 0) {
                array = null;
            } else {
                array = fullValue.split(";");
            }
        }
        
        return array;
    }
    
    Boolean getBoolean(Properties props, EMPreferencesKey key) {
        String str = props.getProperty(EMPreferencesKey.DATABASE_ROLLBACK.toString());
        Boolean b = null;
        
        if(str != null) {
            str = str.trim();
            
            if("true".equalsIgnoreCase(str)) {
                b = Boolean.TRUE;
            } else if("false".equalsIgnoreCase(str)) {
                b = Boolean.FALSE;
            }
        }
        
        return b;
    }
    
    public EMPreferences getPreferences() {
        if(preferences == null) {
            try {
                preferences = loadPreferences();
            } catch(IOException e) {
                LOG.log(Level.SEVERE, "Could not load preferences", e);
                preferences = null;
            }
        }
        return preferences;
    }
    
    /* *********************** */
    /* EM Preferences Key Enum */
    /* *********************** */
    
    public static enum EMPreferencesKey {
        
        MUSIC_DIRECTORIES("em.music_directories"),
        METAFLAC("em.metaflac_command"),
        DATABASE_HOME("em.database.home"),
        DATABASE_ROLLBACK("em.database.rollback_on_close");
        
        private final String key;
        
        private EMPreferencesKey(String key) {
            this.key = key;
        }
        
        @Override
        public String toString() {
            return key;
        }
    }
}
