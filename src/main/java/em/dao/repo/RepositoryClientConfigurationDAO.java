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

package em.dao.repo;

import java.util.Set;

import org.springframework.stereotype.Service;

import em.dao.ClientConfigurationDAO;
import em.model.ClientConfiguration;
import em.repos.ClientConfigurationRepository;
import em.repos.RepoManager;
import em.utils.IDSet;

/**
 * @since v0.1
 * @author eviljoe
 */
@Service
public class RepositoryClientConfigurationDAO implements ClientConfigurationDAO {
    
    private ClientConfigurationRepository clientConfigRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositoryClientConfigurationDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    public Set<ClientConfiguration> findAll() {
        final IDSet<ClientConfiguration> set = new IDSet<>();
        
        for(ClientConfiguration config : getClientConfigurationRepo().findAll()) {
            set.add(config);
        }
        
        return set;
    }
    
    @Override
    public ClientConfiguration findByID(Integer id) {
        return getClientConfigurationRepo().findOne(id);
    }
    
    @Override
    public ClientConfiguration save(ClientConfiguration config) {
        return getClientConfigurationRepo().save(config);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public ClientConfigurationRepository getClientConfigurationRepo() {
        if(clientConfigRepo == null) {
            clientConfigRepo = RepoManager.getInstance().getClientConfiguration();
        }
        return clientConfigRepo;
    }
}
