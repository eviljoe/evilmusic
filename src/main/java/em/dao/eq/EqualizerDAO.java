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
package em.dao.eq;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import em.model.Equalizer;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class EqualizerDAO {
    
    private static final String SELECT_ALL_EQS_JPQL = String.format("SELECT e FROM %s e ", Equalizer.class.getName());
    private static final String DELETE_ALL_EQS_JPQL = String.format("DELETE FROM %s e ", Equalizer.class.getName());
    
    @PersistenceContext
    EntityManager em;
    
    @Autowired
    EqualizerNodeDAO eqNodeDAO;
    
    public Equalizer getFirst() throws EqualizerNotFoundException {
        final Equalizer eq;
        
        try {
            eq = em.createQuery(SELECT_ALL_EQS_JPQL, Equalizer.class).getSingleResult();
        } catch(NoResultException e) {
            throw new EqualizerNotFoundException(e);
        }
        
        return eq;
    }
    
    public List<Equalizer> getAll() {
        return em.createQuery(SELECT_ALL_EQS_JPQL, Equalizer.class).getResultList();
    }
    
    public Equalizer get(int id) throws EqualizerNotFoundException {
        final Equalizer eq = em.find(Equalizer.class, id);
        
        if(eq == null) {
            throw new EqualizerNotFoundException(id);
        }
        
        return eq;
    }
    
    public boolean exists(int id, boolean strict) throws EqualizerNotFoundException {
        final StringBuilder jpql = new StringBuilder();
        final TypedQuery<Integer> q;
        final List<Integer> results;
        final boolean exists;
        
        jpql.append("SELECT eq.id ");
        jpql.append("FROM ").append(Equalizer.class.getName()).append(" eq ");
        jpql.append("WHERE eq.id = :ID ");
        
        q = em.createQuery(jpql.toString(), Integer.class);
        q.setParameter("ID", id);
        results = q.getResultList();
        exists = results != null && results.size() > 0;
        
        if(!exists && strict) {
            throw new EqualizerNotFoundException(id);
        }
        
        return exists;
    }
    
    public Equalizer save(Equalizer eq) {
        em.merge(eq);
        return eq;
    }
    
    public Equalizer add(Equalizer eq) {
        em.persist(eq);
        return eq;
    }
    
    public void removeAll() {
        eqNodeDAO.removeAll();
        em.createQuery(DELETE_ALL_EQS_JPQL).executeUpdate();
    }
    
    public void remove(int id) throws EqualizerNotFoundException, TooManyEqualizersDeletedException {
        final StringBuilder jpql = new StringBuilder();
        final Query q;
        final int deleteCount;
        
        eqNodeDAO.removeAllForEqualizer(id);
        
        jpql.append("DELETE FROM ").append(Equalizer.class.getName()).append(" e ");
        jpql.append("WHERE e.id = :ID ");
        
        q = em.createQuery(jpql.toString());
        q.setParameter("ID", id);
        deleteCount = q.executeUpdate();
        
        if(deleteCount == 0) {
            throw new EqualizerNotFoundException(id);
        } else if(deleteCount > 1) {
            throw new TooManyEqualizersDeletedException(id, deleteCount);
        }
    }
}
