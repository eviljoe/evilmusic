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
package em.dao.eq;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import em.model.EqualizerNode;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class EqualizerNodeDAO {
    
    private static final String DELETE_ALL_EQ_NODES_JPQL = String.format("DELETE FROM %s e ",
            EqualizerNode.class.getName());
    
    @PersistenceContext
    EntityManager em;
    
    public void removeAll() {
        em.createQuery(DELETE_ALL_EQ_NODES_JPQL).executeUpdate();
    }
    
    public void removeAllForEqualizer(int eqID) {
        final StringBuilder jpql = new StringBuilder();
        final Query q;
        
        jpql.append("DELETE FROM ").append(EqualizerNode.class.getName()).append(" e ");
        jpql.append("WHERE e.equalizer.id = :EQ_ID");
        
        q = em.createQuery(jpql.toString());
        q.setParameter("EQ_ID", eqID);
        q.executeUpdate();
    }
}
