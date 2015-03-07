package em.dao.repo;

import java.util.Set;

import org.springframework.stereotype.Service;

import em.dao.QueueDAO;
import em.model.Queue;
import em.repos.QueueRepository;
import em.repos.RepoManager;
import em.utils.IDSet;

/**
 * @since v0.1
 * @author eviljoe
 */
@Service
public class RepositoryQueueDAO implements QueueDAO {
    
    private QueueRepository queueRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositoryQueueDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    public Set<Queue> findAll() {
        final IDSet<Queue> allQueues = new IDSet<>();
        
        for(Queue queue : getQueueRepository().findAll()) {
            allQueues.add(queue);
        }
        
        return allQueues;
    }
    
    @Override
    public Queue findById(int id) {
        return getQueueRepository().findOne(id);
    }
    
    @Override
    public Queue save(Queue queue) {
        return getQueueRepository().save(queue);
    }
    
    @Override
    public void removeAll() {
        getQueueRepository().deleteAll();
    }
    
    @Override
    public void remove(int id) {
        getQueueRepository().delete(id);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    private QueueRepository getQueueRepository() {
        if(queueRepo == null) {
            queueRepo = RepoManager.getInstance().getQueue();
        }
        return queueRepo;
    }
}
