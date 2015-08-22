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

package em.dao.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Client configuration not found")
public class ClientConfigurationNotFoundException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public ClientConfigurationNotFoundException() {
        super();
    }
    
    public ClientConfigurationNotFoundException(String message) {
        super(message);
    }
    
    public ClientConfigurationNotFoundException(Throwable cause) {
        super(cause);
    }
    
    public ClientConfigurationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ClientConfigurationNotFoundException(int id) {
        this(id, null);
    }
    
    public ClientConfigurationNotFoundException(int id, Throwable cause) {
        super(String.format("Could not find client configuration with ID, %d.", id), cause);
    }
    
    public ClientConfigurationNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
