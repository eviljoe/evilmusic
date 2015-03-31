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
