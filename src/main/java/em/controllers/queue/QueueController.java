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

package em.controllers.queue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.controllers.library.SongNotFoundException;
import em.dao.QueueDAO;
import em.dao.SongInfoDAO;
import em.model.Queue;
import em.model.QueueElement;
import em.model.SongInfo;
import em.utils.EMUtils;
import em.utils.LibraryUtils;
import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class QueueController {
    
    private static final Logger LOG = Logger.getLogger(QueueController.class.getName());
    
    @Autowired
    private QueueDAO queueDAO;
    
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
    
    @Transactional
    @RequestMapping(value = "/rest/queue", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue createQueue() {
        LogUtils.restCall(LOG, "/rest/queue", RequestMethod.GET, "Creating queue");
        return queueDAO.save(new Queue());
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQueue(@PathVariable("queueID") int queueID) throws QueueNotFoundException {
        LogUtils.restCall(LOG, "/rest/queue/{queueID}", RequestMethod.DELETE, "Deleting queue: " + queueID);
        
        try {
            queueDAO.remove(queueID);
        } catch(EmptyResultDataAccessException e) {
            throw new QueueNotFoundException(queueID, e);
        }
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/current", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue maybeCreateQueue() {
        final Set<Queue> allQueues;
        final Queue queue;
        
        LogUtils.restCall(LOG, "/rest/queue/current", RequestMethod.GET, "Requesting queue, maybe create");
        
        allQueues = queueDAO.findAll();
        
        if(EMUtils.hasValues(allQueues)) {
            queue = allQueues.iterator().next().clone();
            LibraryUtils.sanitizeElementsForClient(queue.getElements());
        } else {
            queue = queueDAO.save(new Queue());
        }
        
        return queue;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue getQueue(@PathVariable("queueID") int queueID) {
        final Queue queue;
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}", RequestMethod.GET, "Requesting queue: " + queueID);
        
        queue = findQueue(queueID).clone();
        LibraryUtils.sanitizeElementsForClient(queue.getElements());
        
        return queue;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/elements", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue clearQueue(@PathVariable("queueID") int queueID) {
        final Queue queue;
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}", RequestMethod.DELETE, "Clearing queue: " + queueID);
        
        queue = findQueue(queueID);
        queue.clearElements();
        queueDAO.save(queue);
        
        return queue;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/queueindex/{queueIndex}", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue removeByIndex(@PathVariable("queueID") int queueID, @PathVariable("queueIndex") int queueIndex) {
        final Queue queue;
        final int size;
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}/queueindex/{queueIndex}", RequestMethod.DELETE,
                "Removing from queue by index: queueID=" + queueID + ", queueIndex=" + queueIndex);
        
        queue = findQueue(queueID);
        size = queue.size();
        
        if(queueIndex >= size) {
            LogUtils.error(LOG, "Invalid queue index: index=%d, queue_size=%d", queueIndex, size);
            throw new InvalidQueueIndexException(queueIndex);
        }
        
        queue.removeElement(queueIndex);
        
        return queueDAO.save(queue);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/last", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue addLast(@PathVariable("queueID") int queueID,
            @RequestParam(value = "songIDs", required = true) List<Integer> songIDs) throws SongNotFoundException,
            QueueNotFoundException {
        
        final List<SongInfo> songs;
        Queue queue;
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}/last", RequestMethod.PUT, "Enqueuing last: queueID=" + queueID
                + ", songIDs=" + songIDs);
        
        queue = findQueue(queueID);
        songs = findSongs(songIDs);
        
        if(EMUtils.hasValues(songs)) {
            queue.addSongsLast(songs);
            queue = queueDAO.save(queue);
        }
        
        return queue;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/stream/queueindex/{queueIndex}", method = {RequestMethod.GET,
            RequestMethod.HEAD})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSongStream(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathVariable("queueID") int queueID, @PathVariable("queueIndex") int queueIndex, @RequestParam(
                    value = "updatePlayIndex", required = true) boolean updatePlayIndex) throws IOException {
        
        final Queue queue;
        final QueueElement queueElem;
        final SongInfo fullSong;
        final String reqMethod = request.getMethod();
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}/stream/queueindex/{queueIndex}", reqMethod,
                "Streaming song to client: queueID=" + queueID + ", queueIndex=" + queueIndex + ", updatePlayIndex="
                        + updatePlayIndex);
        
        queue = findQueue(queueID);
        queueElem = queue.getElement(queueIndex);
        fullSong = songInfoDAO.findByID(queueElem.getSong().getID());
        
        queue.setPlayIndex(queueIndex);
        queueDAO.save(queue);
        
        LibraryUtils.streamSongToResponse(response, fullSong, EMUtils.equalsIgnoreCase("head", reqMethod));
        
        return Response.ok().build();
    }
    
    private Queue findQueue(Integer id) {
        Queue queue = null;
        
        try {
            queue = queueDAO.findById(id);
        } catch(EmptyResultDataAccessException e) {
            LogUtils.exception(LOG, e, "Could not find queue: %s", id);
            throw new QueueNotFoundException(id, e);
        }
        
        if(queue == null) {
            LogUtils.error(LOG, "Could not find queue: %s", id);
            throw new QueueNotFoundException(id);
        }
        
        return queue;
    }
    
    private List<SongInfo> findSongs(Collection<Integer> songIDs) {
        final List<SongInfo> songs;
        
        try {
            songs = songInfoDAO.findByID(songIDs);
        } catch(EmptyResultDataAccessException e) {
            LogUtils.exception(LOG, e, "Could not find songs: %s", songIDs);
            throw new SongNotFoundException(songIDs, e);
        }
        
        if(!EMUtils.hasValues(songs)) {
            LogUtils.error(LOG, "Could not find songs: %s", songIDs);
            throw new SongNotFoundException(songIDs);
        } else if(songIDs != null && songIDs.size() != songs.size()) {
            LogUtils.error(LOG, "Could not find all songs: %s", songIDs);
            throw new SongNotFoundException(songIDs);
        }
        
        return songs;
    }
}
