package em.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import em.model.EqualizerNode.FrequencyEqualizerNodeComparator;
import em.utils.EMUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class Equalizer implements Identifiable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<EqualizerNode> nodes = null;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public Equalizer() {
        super();
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    @Override
    public Integer getID() {
        return id;
    }
    
    @Override
    public void setID(Integer id) {
        this.id = id;
    }
    
    public Set<EqualizerNode> getNodes() {
        return nodes;
    }
    
    public void setNodes(Collection<EqualizerNode> nodes) {
        this.nodes = EMUtils.toSet(nodes, new TreeSet<EqualizerNode>(new FrequencyEqualizerNodeComparator()));
    }
}
