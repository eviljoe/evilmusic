package em.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Equalizer implements Identifiable, Cloneable {
    
    private static final Logger LOG = Logger.getLogger(Equalizer.class.getName());
    
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
        this.nodes = EMUtils.toSet(nodes, createNodesSet());
    }
    
    private Set<EqualizerNode> createNodesSet() {
        return new TreeSet<EqualizerNode>(new FrequencyEqualizerNodeComparator());
    }
    
    @Override
    public Equalizer clone() {
        Equalizer clone = null;
        
        try {
            clone = (Equalizer)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + Equalizer.class.getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            
            if(nodes == null) {
                clone.nodes = nodes;
            } else {
                clone.nodes = createNodesSet();
                for(EqualizerNode node : nodes) {
                    clone.nodes.add(node == null ? null : node.clone());
                }
            }
        }
        
        return clone;
    }
}
