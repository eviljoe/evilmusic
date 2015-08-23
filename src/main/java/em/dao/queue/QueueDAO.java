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

package em.dao.queue;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import em.dao.AbstractDAO;
import em.model.Queue;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class QueueDAO extends AbstractDAO {
    
    private static final String SELECT_ALL_QS_JPQL = String.format("SELECT e FROM %s e ", Queue.class.getName());
    private static final String DELETE_ALL_QS_JPQL = String.format("DELETE FROM %s", Queue.class.getName());
    
    @Autowired
    QueueElementDAO qElemDAO;
    
    public Queue add(Queue q) {
        em.persist(q);
        return q;
    }
    
    public Queue getFirst() {
        final Queue q;
        
        try {
            q = em.createQuery(SELECT_ALL_QS_JPQL, Queue.class).getSingleResult();
        } catch(NoResultException e) {
            throw new QueueNotFoundException(e);
        }
        
        return q;
    }
    
    public List<Queue> getAll() {
        return em.createQuery(SELECT_ALL_QS_JPQL, Queue.class).getResultList();
    }
    
    public Queue get(int id) throws QueueNotFoundException {
        final Queue q = em.find(Queue.class, id);
        
        if(q == null) {
            throw new QueueNotFoundException(id);
        }
        
        return q;
    }
    
    public Queue save(Queue q) {
        em.merge(q);
        return q;
    }
    
    public void removeAll() {
        qElemDAO.removeAll();
        em.createQuery(DELETE_ALL_QS_JPQL).executeUpdate();
    }
    
    public void remove(int id) throws QueueNotFoundException, TooManyQueuesDeletedException {
        final StringBuilder jpql = new StringBuilder();
        final Query q;
        final int deleteCount;
        
        qElemDAO.removeAllForQueue(id);
        
        jpql.append("DELETE FROM ").append(Queue.class.getName()).append(" q ");
        jpql.append("WHERE q.id = :ID ");
        
        q = em.createQuery(jpql.toString());
        q.setParameter("ID", id);
        deleteCount = q.executeUpdate();
        
        if(deleteCount == 0) {
            throw new QueueNotFoundException(id);
        } else if(deleteCount > 1) {
            throw new TooManyQueuesDeletedException(id, deleteCount);
        }
    }
}
