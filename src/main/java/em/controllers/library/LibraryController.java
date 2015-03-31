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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.QueueDAO;
import em.dao.SongInfoDAO;
import em.model.EMPreferences;
import em.model.Library;
import em.model.SongInfo;
import em.prefs.EMPreferencesManager;
import em.utils.LibraryUtils;
import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class LibraryController {
    
    private static final Logger LOG = Logger.getLogger(LibraryController.class.getName());
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    @Autowired
    private QueueDAO queueDAO;
    
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
        final List<SongInfo> songs;
        final List<SongInfo> clones = new ArrayList<>();
        final Library lib = new Library();
        
        songs = songInfoDAO.findAll();
        LogUtils.restCall(LOG, "/rest/library", RequestMethod.GET, "Requesting library");
        
        for(SongInfo song : songs) {
            clones.add(song.clone());
        }
        
        lib.setSongs(LibraryUtils.sanitizeSongsForClient(clones));
        
        return lib;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/library", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear() {
        LogUtils.restCall(LOG, "/rest/library", RequestMethod.DELETE, "Clearing library");
        
        songInfoDAO.removeAllSongs();
        queueDAO.removeAll();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/library", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rebuildLibrary() throws IOException, URISyntaxException {
        final EMPreferences prefs;
        final List<SongInfo> infos;
        
        LogUtils.restCall(LOG, "/rest/library", RequestMethod.POST, "Rebuilding library");
        
        prefs = EMPreferencesManager.getInstance().getPreferences();
        infos = LibraryUtils.scanDirectories(prefs == null ? null : prefs.getMusicDirectories());
        
        songInfoDAO.replaceAllSongs(infos);
        queueDAO.removeAll();
    }
}
