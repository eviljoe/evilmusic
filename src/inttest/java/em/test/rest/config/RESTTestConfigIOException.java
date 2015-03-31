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

/**
 * @since v0.1
 * @author eviljoe
 */
public class RESTTestConfigIOException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RESTTestConfigIOException() {
        super();
    }
    
    public RESTTestConfigIOException(String msg) {
        super(msg);
    }
    
    public RESTTestConfigIOException(Throwable cause) {
        super(cause);
    }
    
    public RESTTestConfigIOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
