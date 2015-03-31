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

package em.controllers.user;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import em.controllers.library.LibraryController;
import em.dao.QueueDAO;
import em.dao.SongInfoDAO;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class UserController {
    
    private static final Logger LOG = Logger.getLogger(LibraryController.class.getName());
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    @Autowired
    private QueueDAO queueDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public UserController() {
        super();
    }
}
