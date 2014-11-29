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
