package em.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.SongInfoDAO;
import em.model.EMPreferences;
import em.model.SongInfo;
import em.prefs.EMPreferencesManager;
import em.utils.LibraryUtils;
import em.utils.LogUtils;
import em.utils.QueueManager;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class LibraryController {
    
    private static final Logger LOG = Logger.getLogger(LibraryController.class.getName());
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public LibraryController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @RequestMapping(value = "/rest/library", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<SongInfo> getLibrary() {
        LogUtils.createRESTCallEntry(LOG, "/rest/library", RequestMethod.GET, "Requesting library");
        return LibraryUtils.sanitizeForClient(songInfoDAO.findAll());
    }
    
    @RequestMapping(value = "/rest/library", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        LogUtils.createRESTCallEntry(LOG, "/rest/library", RequestMethod.DELETE, "Clearing library");
        songInfoDAO.removeAllSongs();
        QueueManager.getInstance().clear();
    }
    
    @RequestMapping(value = "/rest/library", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void rebuildLibrary() throws IOException {
        final EMPreferences prefs;
        final List<SongInfo> infos;
        
        LogUtils.createRESTCallEntry(LOG, "/rest/queue", RequestMethod.POST, "Rebuilding library");
        
        prefs = EMPreferencesManager.getInstance().loadPreferences();
        infos = LibraryUtils.scanDirectories(prefs == null ? null : prefs.getMusicDirectories());
        
        songInfoDAO.replaceAllSongs(infos);
        QueueManager.getInstance().clear();
    }
}
