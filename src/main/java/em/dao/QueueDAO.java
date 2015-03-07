package em.dao;

import java.util.Set;

import em.model.Queue;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface QueueDAO {
    
    public Set<Queue> findAll();
    
    public Queue findById(int id);
    
    public Queue save(Queue queue);
    
    public void removeAll();
    
    public void remove(int id);
}
