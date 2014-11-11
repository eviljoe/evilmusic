package em.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.SongInfoDAO;
import em.model.QueuedSongInfo;
import em.model.SongInfo;
import em.utils.LibraryUtils;
import em.utils.LogUtils;
import em.utils.QueueManager;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class QueueController {
    
    private static final Logger LOG = Logger.getLogger(QueueController.class.getName());
    
    @Autowired
    private SongInfoDAO songInfoDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public QueueController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @RequestMapping(value = "/rest/queue", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<QueuedSongInfo> getQueue() {
        LogUtils.createRESTCallEntry(LOG, "/rest/queue", RequestMethod.GET, "Requesting queue");
        return LibraryUtils.sanitizeForClient(QueueManager.getInstance().getContents());
    }
    
    @RequestMapping(value = "/rest/queue", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        LogUtils.createRESTCallEntry(LOG, "/rest/queue", RequestMethod.DELETE, "Clearing queue");
        QueueManager.getInstance().clear();
    }
    
    @RequestMapping(value = "/rest/queue/queueindex/{queueIndex}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeByIndex(@PathVariable("queueIndex") int queueIndex) {
        LogUtils.createRESTCallEntry(LOG, "/rest/queue/queueindex/{queueIndex}", RequestMethod.DELETE,
                "Removing from queue by index: " + queueIndex);
        QueueManager.getInstance().remove(queueIndex);
    }
    
    @RequestMapping(value = "/rest/queue/last", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<QueuedSongInfo> enqueueLast(@RequestBody List<Integer> songInfoIDs) {
        final QueueManager qmgr;
        
        LogUtils.createRESTCallEntry(LOG, "/rest/queue/last", RequestMethod.PUT, "Enqueuing last: " + songInfoIDs);
        
        qmgr = QueueManager.getInstance();
        qmgr.queueLast(songInfoDAO.findByID(songInfoIDs));
        
        return LibraryUtils.sanitizeForClient(qmgr.getContents());
    }
    
    @RequestMapping(value = "/rest/queue/playing/queueindex/{queueIndex}", method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response play(@PathVariable("queueIndex") int queueIndex, @Context HttpServletResponse response)
            throws IOException {
        
        final QueueManager qmgr;
        final QueuedSongInfo queuedInfo;
        
        LogUtils.createRESTCallEntry(LOG, "/rest/queue/playing/queueindex/{queueIndex}", RequestMethod.PUT,
                "Setting currently playing song: " + queueIndex);
        
        qmgr = QueueManager.getInstance();
        queuedInfo = qmgr.getByQueueIndex(queueIndex);
        
        if(queuedInfo != null) {
            final SongInfo fullInfo = songInfoDAO.findByID(queuedInfo.getID());
            
            if(fullInfo != null) {
                qmgr.setPlayIndex(queueIndex);
            }
            
            LibraryUtils.streamSongToResponse(response, fullInfo);
        }
        
        return Response.ok().build();
    }
}
