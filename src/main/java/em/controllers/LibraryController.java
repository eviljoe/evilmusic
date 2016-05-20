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

package em.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import em.dao.queue.QueueDAO;
import em.dao.song.SongInfoDAO;
import em.model.EMPreferences;
import em.model.Library;
import em.model.SongInfo;
import em.prefs.EMPreferencesManager;
import em.utils.LibraryUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class LibraryController {
    
    @Autowired
    QueueDAO qDAO;
    
    @Autowired
    SongInfoDAO songDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public LibraryController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @Transactional
    @RequestMapping(value = "/rest/library", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Library getLibrary() {
        return new Library(songDAO.getAll());
    }
    
    @Transactional
    @RequestMapping(value = "/rest/library", method = RequestMethod.DELETE)
    public Library clear() {
        qDAO.removeAll();
        songDAO.removeAll();
        
        return getLibrary();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/library", method = RequestMethod.POST)
    public Library rebuildLibrary() throws IOException, URISyntaxException {
        final EMPreferences prefs = EMPreferencesManager.getInstance().getPreferences();
        final List<SongInfo> infos = LibraryUtils.scanDirectories(prefs == null ? null : prefs.getMusicDirectories());
        
        qDAO.removeAll();
        songDAO.removeAll();
        
        for(SongInfo info : infos) {
            songDAO.add(info);
        }
        
        return getLibrary();
    }
}
