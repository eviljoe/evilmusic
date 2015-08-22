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
import java.util.List;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.queue.InvalidQueueIndexException;
import em.dao.queue.QueueDAO;
import em.dao.queue.QueueNotFoundException;
import em.dao.song.SongInfoDAO;
import em.dao.song.SongNotFoundException;
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
    QueueDAO qDAO;
    
    @Autowired
    SongInfoDAO songDAO;
    
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
        return qDAO.add(new Queue());
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQueue(@PathVariable("id") int id) {
        LogUtils.restCall(LOG, "/rest/queue/{id}", RequestMethod.DELETE, "Deleting queue: " + id);
        qDAO.remove(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/current", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue maybeCreateQueue() {
        Queue q;
        
        LogUtils.restCall(LOG, "/rest/queue/current", RequestMethod.GET, "Requesting queue, maybe create");
        
        try {
            q = qDAO.getFirst().clone();
            LibraryUtils.sanitizeElementsForClient(q.getElements());
        } catch(QueueNotFoundException e) {
            q = qDAO.add(new Queue());
        }
        
        return q;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue getQueue(@PathVariable("id") int id) {
        final Queue q;
        
        LogUtils.restCall(LOG, "/rest/queue/{id}", RequestMethod.GET, "Requesting queue: " + id);
        
        q = qDAO.get(id).clone();
        LibraryUtils.sanitizeElementsForClient(q.getElements());
        
        return q;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}/elements", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue clearQueue(@PathVariable("id") int id) {
        final Queue q;
        
        LogUtils.restCall(LOG, "/rest/queue/{id}", RequestMethod.DELETE, "Clearing queue: " + id);
        
        q = qDAO.get(id);
        q.clearElements();
        
        return qDAO.save(q);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}/queueindex/{qIndex}", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue removeByIndex(@PathVariable("id") int id, @PathVariable("qIndex") int qIndex) {
        final Queue q;
        final int size;
        
        LogUtils.restCall(LOG, "/rest/queue/{id}/queueindex/{qIndex}", RequestMethod.DELETE,
                "Removing from queue by index: id=" + id + ", qIndex=" + qIndex);
        
        q = qDAO.get(id);
        size = q.size();
        
        if(qIndex >= size) {
            LogUtils.error(LOG, "Invalid queue index: index=%d, queue_size=%d", qIndex, size);
            throw new InvalidQueueIndexException(qIndex);
        }
        
        q.removeElement(qIndex);
        
        return qDAO.save(q);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/last", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue addLast(@PathVariable("queueID") int queueID,
            @RequestParam(value = "songIDs", required = true) List<Integer> songIDs) throws SongNotFoundException,
            QueueNotFoundException {
        
        final List<SongInfo> songs;
        Queue q;
        
        LogUtils.restCall(LOG, "/rest/queue/{queueID}/last", RequestMethod.PUT, "Enqueuing last: queueID=" + queueID
                + ", songIDs=" + songIDs);
        
        q = qDAO.get(queueID);
        songs = songDAO.get(songIDs);
        
        if(EMUtils.hasValues(songs)) {
            q.addSongsLast(songs);
            qDAO.save(q);
        }
        
        return q;
    }
    
    @Transactional
    @RequestMapping( //
            value = "/rest/queue/{queueID}/stream/queueindex/{queueIndex}", //
            method = {RequestMethod.GET, RequestMethod.HEAD})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSongStream( //
            @Context HttpServletRequest request, //
            @Context HttpServletResponse response, //
            @PathVariable("qID") int qID, //
            @PathVariable("qIndex") int qIndex, //
            @RequestParam(value = "updatePlayIndex", required = true) boolean updatePlayIndex) throws IOException {
        
        final Queue q;
        final QueueElement qElem;
        final SongInfo fullSong;
        final String reqMethod = request.getMethod();
        
        LogUtils.restCall(LOG, "/rest/queue/{qID}/stream/queueindex/{qIndex}", reqMethod, String.format(
                "Streaming song to client: qID=%d, qIndex=%d, updatePlayIndex=%d", qID, qIndex, updatePlayIndex));
        
        q = qDAO.get(qID);
        qElem = q.getElement(qIndex);
        fullSong = songDAO.get(qElem.getSong().getID());
        
        q.setPlayIndex(qIndex);
        qDAO.save(q);
        
        LibraryUtils.streamSongToResponse(response, fullSong, EMUtils.equalsIgnoreCase("head", reqMethod));
        
        return Response.ok().build();
    }
}
