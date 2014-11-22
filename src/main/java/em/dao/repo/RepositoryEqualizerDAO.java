package em.dao.repo;

import java.util.Set;

import em.dao.EqualizerDAO;
import em.model.Equalizer;
import em.repos.EqualizerRepository;
import em.utils.IDSet;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RepositoryEqualizerDAO extends AbstractRepositoryDAO implements EqualizerDAO {
    
    private EqualizerRepository eqRepo;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RepositoryEqualizerDAO() {
        super();
    }
    
    /* ************* */
    /* DAO Functions */
    /* ************* */
    
    @Override
    public Set<Equalizer> findAll() {
        final IDSet<Equalizer> eqs = new IDSet<>();
        
        for(Equalizer eq : getEqualizerRepo().findAll()) {
            eqs.add(eq);
        }
        
        return eqs;
    }
    
    @Override
    public Equalizer save(Equalizer eq) {
        return getEqualizerRepo().save(eq);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    private EqualizerRepository getEqualizerRepo() {
        if(eqRepo == null) {
            eqRepo = this.getRepoManager().getEqualizer();
        }
        return eqRepo;
    }
}
