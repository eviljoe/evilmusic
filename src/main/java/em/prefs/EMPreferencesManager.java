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

package em.prefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private volatile File commandLinePreferencesFile;
    
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
    
    public InputStream getPreferencesStream() {
        final File clFile = getCommandLinePreferencesFile();
        InputStream in = null;
        
        if(clFile != null && clFile.exists() && !clFile.isDirectory()) {
            LOG.info("Using command line specified properties file: " + clFile.getAbsolutePath());
            
            try {
                in = new FileInputStream(clFile);
            } catch(FileNotFoundException e) {
                LOG.log(Level.SEVERE, "Could not find preferences file", e);
                in = null;
            }
        } else {
            LOG.info("Using default properties file: " + DEFAULT_PREFS_FILE_NAME);
            in = EMPreferencesManager.class.getClassLoader().getResourceAsStream(DEFAULT_PREFS_FILE_NAME);
        }
        
        return in;
    }
    
    public synchronized EMPreferences loadPreferences() throws IOException {
        final Properties props = new Properties();
        final InputStream in = getPreferencesStream();
        
        if(in != null) {
            try {
                props.load(in);
            } finally {
                in.close();
            }
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
            final Integer serverPort = getInteger(props, EMPreferencesKey.SERVER_PORT);
            
            emPrefs.setMusicDirectories(musicDirs);
            emPrefs.setMetaFLACCommand(metaflac);
            emPrefs.setDatabaseHome(dbHome);
            emPrefs.setDatabaseRollback(dbRollback);
            emPrefs.setServerPort(serverPort);
            
            LOG.info("=== Loaded Preferences ===");
            LOG.info("Music Directories: " + Arrays.toString(musicDirs));
            LOG.info("MetaFLAC Command:  " + metaflac);
            LOG.info("Database Home:     " + dbHome);
            LOG.info("Database Rollback: " + dbRollback);
            LOG.info("Server Port:       " + serverPort);
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
        String str = props.getProperty(key.toString());
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
    
    Integer getInteger(Properties props, EMPreferencesKey key) {
        String str = props.getProperty(key.toString());
        Integer i = null;
        
        if(str != null) {
            str = str.trim();
            
            try {
                i = Integer.parseInt(str);
            } catch(NumberFormatException e) {
                LOG.log(Level.SEVERE, "Exception while parsing integer property", e);
            }
        }
        
        return i;
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
    
    public synchronized File getCommandLinePreferencesFile() {
        return commandLinePreferencesFile;
    }
    
    public synchronized void setCommandLinePreferencesFile(File commandLinePreferencesFile) {
        this.commandLinePreferencesFile = commandLinePreferencesFile;
    }
    
    /* *********************** */
    /* EM Preferences Key Enum */
    /* *********************** */
    
    public static enum EMPreferencesKey {
        
        MUSIC_DIRECTORIES("em.music_directories"),
        METAFLAC("em.metaflac_command"),
        DATABASE_HOME("em.database.home"),
        DATABASE_ROLLBACK("em.database.rollback_on_close"),
        SERVER_PORT("em.server.port");
        
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
