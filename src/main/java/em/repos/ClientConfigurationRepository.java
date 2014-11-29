package em.repos;

import org.springframework.data.repository.CrudRepository;

import em.model.ClientConfiguration;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface ClientConfigurationRepository extends CrudRepository<ClientConfiguration, Integer> {
}
