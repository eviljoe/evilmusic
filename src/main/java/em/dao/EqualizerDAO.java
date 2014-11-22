package em.dao;

import java.util.Set;

import em.model.Equalizer;

/**
 * @since v0.1
 * @author eviljoe
 */
public interface EqualizerDAO {
    
    public Set<Equalizer> findAll();
    
    public Equalizer save(Equalizer eq);
}
