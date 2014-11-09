package em.dao.repo;

import em.repos.RepoManager;

/**
 * @since v0.1
 * @author eviljoe
 */
public class AbstractRepositoryDAO {
    
    private RepoManager rmgr;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public AbstractRepositoryDAO() {
        super();
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public RepoManager getRepoManager() {
        if(rmgr == null) {
            rmgr = RepoManager.getInstance();
        }
        return rmgr;
    }
}
