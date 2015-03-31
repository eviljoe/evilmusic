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

package em.controllers.library;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Song not found")
public class SongNotFoundException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public SongNotFoundException() {
        super();
    }
    
    public SongNotFoundException(String message) {
        super(message);
    }
    
    public SongNotFoundException(Throwable cause) {
        super(cause);
    }
    
    public SongNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SongNotFoundException(int id, Throwable cause) {
        super(String.format("Could not find song with ID, %d.", id), cause);
    }
    
    public SongNotFoundException(Collection<Integer> ids) {
        this(ids, null);
    }
    
    public SongNotFoundException(Collection<Integer> ids, Throwable cause) {
        super(String.format("Could not find at least one song using IDs, %s.", ids == null ? "[]" : ids.toString()),
                cause);
    }
    
    public SongNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
