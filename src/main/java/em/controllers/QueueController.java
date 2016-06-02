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
import em.dao.queue.InvalidQueuePlayIndexException;
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
        return qDAO.add(new Queue());
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQueue(@PathVariable("id") int id) {
        qDAO.remove(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/default", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue getDefaultQueue() {
        Queue q;
        
        try {
            q = qDAO.getFirst();
        } catch(QueueNotFoundException e) {
            q = qDAO.add(new Queue());
        }
        
        return q;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue getQueue(@PathVariable("id") int id) {
        return qDAO.get(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}/elements", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue clearQueue(@PathVariable("id") int id) {
        final Queue q = qDAO.get(id);
        q.clearElements();
        return qDAO.save(q);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{id}/queueindex/{qIndex}", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue removeByIndex(@PathVariable("id") int id, @PathVariable("qIndex") int qIndex) {
        final Queue q = qDAO.get(id);
        final int size = q.size();
        
        if(qIndex >= size) {
            LogUtils.error(LOG, "Invalid queue index: index=%d, queue_size=%d", qIndex, size);
            throw new InvalidQueueIndexException(qIndex);
        }
        
        q.removeElement(qIndex);
        
        return qDAO.save(q);
    }
    
    // TODO The name of the "songIDs" parameter should be renamed to "songID." This is because the URL will look like
    // this:
    // .../last?songID=123&songID=456&songID=789
    // It does not look like this:
    // .../last?songIDs=123,456,789
    @Transactional
    @RequestMapping(value = "/rest/queue/{queueID}/last", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Queue addLast(@PathVariable("queueID") int queueID,
            @RequestParam(value = "songIDs", required = true) List<Integer> songIDs) throws SongNotFoundException,
            QueueNotFoundException {
        
        final Queue q = qDAO.get(queueID);
        final List<SongInfo> songs = songDAO.get(songIDs);
        
        if(EMUtils.hasValues(songs)) {
            q.addSongsLast(songs);
            qDAO.save(q);
        }
        
        return q;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{qID}/playindex/{playIndex}", method = RequestMethod.PUT)
    public Queue setPlayIndex(@PathVariable("qID") int qID, @PathVariable("playIndex") int playIndex) {
        final Queue q = qDAO.get(qID);
        
        if(playIndex < 0 || playIndex > q.size() - 1) {
            throw new InvalidQueuePlayIndexException(qID, playIndex);
        }
        
        q.setPlayIndex(playIndex);
        qDAO.save(q);
        
        return q;
    }
    
    private Response getSongStream(HttpServletResponse response, int qID, int qIndex, boolean head) throws IOException {
        final QueueElement qElem = qDAO.getElement(qID, qIndex);
        final SongInfo fullSong = songDAO.get(qElem.getSong().getID());
        
        LibraryUtils.streamSongToResponse(response, fullSong, head);
        
        return Response.ok().build();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{qID}/queueindex/{qIndex}/stream", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSongStream( //
            @Context HttpServletRequest request, //
            @Context HttpServletResponse response, //
            @PathVariable("qID") int qID, //
            @PathVariable("qIndex") int qIndex) throws IOException {
        
        return getSongStream(response, qID, qIndex, false);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/queue/{qID}/queueindex/{qIndex}/stream", method = RequestMethod.HEAD)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getSongStreamHead( //
            @Context HttpServletRequest request, //
            @Context HttpServletResponse response, //
            @PathVariable("qID") int qID, //
            @PathVariable("qIndex") int qIndex) throws IOException {
        
        return getSongStream(response, qID, qIndex, true);
    }
    
}
