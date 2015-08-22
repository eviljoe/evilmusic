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
package em.dao.client;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import em.model.ClientConfiguration;

/**
 * @since v0.1
 * @author eviljoe
 */
@Component
public class ClientConfigurationDAO {
    
    private static final String SELECT_ALL_CONFIGS_JPQL = String.format("SELECT c FROM %s c ",
            ClientConfiguration.class.getName());
    
    @PersistenceContext
    EntityManager em;
    
    public ClientConfiguration getFirst() {
        final ClientConfiguration config;
        
        try {
            config = em.createQuery(SELECT_ALL_CONFIGS_JPQL, ClientConfiguration.class).getSingleResult();
        } catch(NoResultException e) {
            throw new ClientConfigurationNotFoundException(e);
        }
        
        return config;
    }
    
    public ClientConfiguration save(ClientConfiguration config) {
        em.persist(config);
        return config;
    }
}
