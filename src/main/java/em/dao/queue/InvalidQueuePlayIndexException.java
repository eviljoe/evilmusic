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

package em.dao.queue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid queue play index")
public class InvalidQueuePlayIndexException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public InvalidQueuePlayIndexException() {
        super();
    }
    
    public InvalidQueuePlayIndexException(String message) {
        super(message);
    }
    
    public InvalidQueuePlayIndexException(Throwable cause) {
        super(cause);
    }
    
    public InvalidQueuePlayIndexException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidQueuePlayIndexException(int qID, int playIndex) {
        this(qID, playIndex, null);
    }
    
    public InvalidQueuePlayIndexException(int qID, int playIndex, Throwable cause) {
        super(String.format("Invalid queue play index, %d, for queue, %d", playIndex, qID), cause);
    }
    
    public InvalidQueuePlayIndexException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
