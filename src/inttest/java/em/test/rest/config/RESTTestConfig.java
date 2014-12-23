package em.test.rest.config;

import java.io.IOException;
import java.util.Properties;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RESTTestConfig {
    
    private static final String URL_PROP = "url";
    
    private static RESTTestConfig instance;
    
    private String url;
    
    /* ******************* */
    /* Singleton Functions */
    /* ******************* */
    
    public static synchronized RESTTestConfig getInstance() {
        if(instance == null) {
            instance = new RESTTestConfig();
            instance.loadProperties();
        }
        return instance;
    }
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RESTTestConfig() {
        super();
    }
    
    /* ************** */
    /* Load Functions */
    /* ************** */
    
    private void loadProperties() {
        try {
            final Properties p = new Properties();
            
            p.load(this.getClass().getClassLoader().getResourceAsStream("rest-test.properties"));
            
            setURL(p.getProperty(URL_PROP));
        } catch(IOException e) {
            throw new RESTTestConfigIOException(e);
        }
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public String getURL() {
        return url;
    }
    
    private void setURL(String url) {
        this.url = url;
    }
    
    public String getFullURL(String suffix) {
        return url + suffix;
    }
}
