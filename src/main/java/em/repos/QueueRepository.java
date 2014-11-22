package em.repos;

import org.springframework.data.repository.CrudRepository;

import em.model.Queue;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface QueueRepository extends CrudRepository<Queue, Integer> {
    
}
