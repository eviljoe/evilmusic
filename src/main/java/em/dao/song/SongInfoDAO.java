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

package em.dao.song;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import em.dao.AbstractDAO;
import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class SongInfoDAO extends AbstractDAO {
    
    private static final String SELECT_ALL_SONGS_JPQL = String.format("SELECT s FROM %s s", SongInfo.class.getName());
    private static final String DELETE_ALL_SONGS_JPQL = String.format("DELETE FROM %s", SongInfo.class.getName());
    
    public SongInfo add(SongInfo song) {
        em.persist(song);
        return song;
    }
    
    public List<SongInfo> getAll() {
        return em.createQuery(SELECT_ALL_SONGS_JPQL, SongInfo.class).getResultList();
    }
    
    public SongInfo get(int id) throws SongNotFoundException {
        final SongInfo song = em.find(SongInfo.class, id);
        
        if(song == null) {
            throw new SongNotFoundException(id);
        }
        
        return song;
    }
    
    public List<SongInfo> get(Collection<Integer> ids) {
        final StringBuilder jpql = new StringBuilder();
        final TypedQuery<SongInfo> q;
        final List<SongInfo> songs;
        
        jpql.append("SELECT s FROM ").append(SongInfo.class.getName()).append(" s ");
        jpql.append("WHERE s.id IN :IDS");
        
        // JOE TODO throw Bad Request exception if idCount is null/empty
        
        q = em.createQuery(jpql.toString(), SongInfo.class);
        q.setParameter("IDS", ids);
        songs = q.getResultList();
        
        if(songs.size() < ids.size()) {
            throw new SongNotFoundException(ids);
        }
        
        return songs;
    }
    
    public void remove(int id) {
        final StringBuilder jpql = new StringBuilder();
        final TypedQuery<SongInfo> q;
        final int deleteCount;
        
        jpql.append("DELETE FROM ").append(SongInfo.class.getName()).append(" s ");
        jpql.append("WHERE s.id = :ID");
        
        q = em.createQuery(jpql.toString(), SongInfo.class);
        q.setParameter("ID", id);
        deleteCount = q.executeUpdate();
        
        if(deleteCount == 0) {
            throw new SongNotFoundException(id);
        } else if(deleteCount > 1) {
            throw new TooManySongsDeletedException(id, deleteCount);
        }
    }
    
    public void removeAll() {
        em.createQuery(DELETE_ALL_SONGS_JPQL).executeUpdate();
    }
}
