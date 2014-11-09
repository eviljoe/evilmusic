package em.controllers;

import java.io.IOException;
import java.util.List;

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

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class LibraryController {
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    @RequestMapping(value = "/rest/library", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void rebuildLibrary() throws IOException {
        final EMPreferences prefs = EMPreferencesManager.getInstance().loadPreferences();
        final List<SongInfo> infos = LibraryUtils.scanDirectories(prefs == null ? null : prefs.getMusicDirectories());
        
        songInfoDAO.replaceAllSongs(infos);
    }
    
    @RequestMapping(value = "/rest/library", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<SongInfo> getLibrary() {
        return songInfoDAO.getAll();
    }
}
