/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
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

package em.dao.eq;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Too many equalizer deleted")
public class TooManyEqualizersDeletedException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public TooManyEqualizersDeletedException() {
        super();
    }
    
    public TooManyEqualizersDeletedException(String message) {
        super(message);
    }
    
    public TooManyEqualizersDeletedException(Throwable cause) {
        super(cause);
    }
    
    public TooManyEqualizersDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TooManyEqualizersDeletedException(int id, int deleteCount) {
        this(id, deleteCount, null);
    }
    
    public TooManyEqualizersDeletedException(int id, int deleteCount, Throwable cause) {
        super(String.format("Too many equalizers deleted (%d) when deleting equalizer with ID, %d.", deleteCount, id),
                cause);
    }
    
    public TooManyEqualizersDeletedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
