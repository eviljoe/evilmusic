package em.dao;

import java.util.Set;

import em.model.ClientConfiguration;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface ClientConfigurationDAO {
    
    public Set<ClientConfiguration> findAll();
    
    public ClientConfiguration findByID(Integer id);
    
    public ClientConfiguration save(ClientConfiguration config);
}
