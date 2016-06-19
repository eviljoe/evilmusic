/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
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

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import em.dao.playlist.InvalidPlaylistNameException;
import em.dao.playlist.PlaylistDAO;
import em.dao.playlist.PlaylistElementNotFoundException;
import em.dao.playlist.PlaylistNotFoundException;
import em.dao.song.SongInfoDAO;
import em.dao.song.SongNotFoundException;
import em.model.Playlist;
import em.utils.EMUtils;
import em.utils.LibraryUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@RestController
public class PlaylistController {
    
    @Autowired
    PlaylistDAO playlistDAO;
    
    @Autowired
    SongInfoDAO songDAO;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public PlaylistController() {
        super();
    }
    
    /* ************** */
    /* REST Functions */
    /* ************** */
    
    @Transactional
    @RequestMapping(value = "/rest/playlists", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist createPlaylist(@RequestParam(value = "name", required = true) String name) {
        final Playlist p = new Playlist();
        
        validatePlaylistName(name);
        p.setName(name);
        
        return playlistDAO.add(p);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Playlist> getPlaylists() {
        return playlistDAO.getAll();
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist getPlaylist(@PathVariable("id") int id) {
        return playlistDAO.get(id);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists/{id}/last", method = RequestMethod.PUT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist addLast(@PathVariable("id") int id,
            @RequestParam(value = "songID", required = true) List<Integer> songIDs) throws SongNotFoundException,
            PlaylistNotFoundException {
        
        final Playlist p = playlistDAO.get(id);
        
        if(EMUtils.hasValues(songIDs) && p.addSongsLast(LibraryUtils.sortSongs(songIDs, songDAO.get(songIDs)))) {
            playlistDAO.save(p);
        }
        
        return p;
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists/{id}/elements", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist clearPlaylist(@PathVariable("id") int id) {
        final Playlist p = playlistDAO.get(id);
        
        p.clearElements();
        
        return playlistDAO.save(p);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists/{playlistID}/elements/{playlistElemID}", method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist removeElement(@PathVariable("playlistID") int playlistID,
            @PathVariable("playlistElemID") int playlistElemID) {
        
        final Playlist p = playlistDAO.get(playlistID);
        
        if(p.removeElementByID(playlistElemID) == null) {
            throw new PlaylistElementNotFoundException(String.format(
                    "The playlist elment with (id=%d) is not a member of the playlist (id=%d).", playlistID,
                    playlistElemID));
        }
        
        return playlistDAO.save(p);
    }
    
    @Transactional
    @RequestMapping(value = "/rest/playlists/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaylist(@PathVariable("id") int id) {
        playlistDAO.remove(id);
    }
    
    void validatePlaylistName(String name) throws InvalidPlaylistNameException {
        final int length = name == null ? 0 : name.length();
        
        if(length == 0) {
            throw new InvalidPlaylistNameException("A playlist name must be specified");
        } else if(length > Playlist.NAME_MAX_LENGTH) {
            throw new InvalidPlaylistNameException("A playlist's name cannot be more than " + Playlist.NAME_MAX_LENGTH
                    + " characters");
        }
    }
}
