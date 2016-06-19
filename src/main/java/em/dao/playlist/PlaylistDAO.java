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
package em.dao.playlist;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import em.dao.AbstractDAO;
import em.model.Playlist;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class PlaylistDAO extends AbstractDAO {
    
    private static final String SELECT_ALL_PLS_JPQL = String.format("SELECT p FROM %s p ", Playlist.class.getName());
    
    @Autowired
    PlaylistElementDAO playlistElemDAO;
    
    public Playlist add(Playlist p) {
        em.persist(p);
        return p;
    }
    
    public Playlist save(Playlist p) {
        em.merge(p);
        return p;
    }
    
    public List<Playlist> getAll() {
        return em.createQuery(SELECT_ALL_PLS_JPQL, Playlist.class).getResultList();
    }
    
    public Playlist get(int id) throws PlaylistNotFoundException {
        final Playlist p = em.find(Playlist.class, id);
        
        if(p == null) {
            throw new PlaylistNotFoundException(id);
        }
        
        return p;
    }
    
    public void remove(int id) throws PlaylistNotFoundException, TooManyPlaylistsDeletedException {
        final StringBuilder jpql = new StringBuilder();
        final Query q;
        final int deleteCount;
        
        playlistElemDAO.removeAllForPlaylist(id);
        
        jpql.append("DELETE FROM ").append(Playlist.class.getName()).append(" p ");
        jpql.append("WHERE p.id = :ID ");
        
        q = em.createQuery(jpql.toString());
        q.setParameter("ID", id);
        deleteCount = q.executeUpdate();
        
        if(deleteCount == 0) {
            throw new PlaylistNotFoundException(id);
        } else if(deleteCount > 1) {
            throw new TooManyPlaylistsDeletedException(id, deleteCount);
        }
    }
}
