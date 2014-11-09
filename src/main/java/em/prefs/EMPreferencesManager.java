package em.prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import em.dao.MusicDirectoryDAO;
import em.model.EMPreferences;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferencesManager {
    
    private static final String DEFAULT_PREFS_FILE_NAME = "em-properties.json";
    
    private static EMPreferencesManager instance;
    
    @Autowired
    private MusicDirectoryDAO musicDirDAO;
    
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
    
    public synchronized EMPreferences loadPreferences() {
        final ObjectMapper mapper = new ObjectMapper();
        EMPreferences prefs = null;
        
        try {
            prefs =
                    mapper.readValue(EMPreferencesManager.class.getClassLoader().getResource(DEFAULT_PREFS_FILE_NAME),
                            EMPreferences.class);
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return prefs;
    }
    
    public synchronized void savePreferences(EMPreferences prefs) {
        final ObjectMapper mapper = new ObjectMapper();
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        if(prefs == null) {
            prefs = new EMPreferences();
        }
        
        try {
            mapper.writeValue(
                    new FileOutputStream(new File(EMPreferencesManager.class.getClassLoader()
                            .getResource(DEFAULT_PREFS_FILE_NAME).toURI())), prefs);
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
