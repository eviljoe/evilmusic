package em.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import em.utils.EMUtils;
import em.utils.NullComparator;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class EqualizerNode implements Identifiable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private int frequency;
    private double q;
    private double gain;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EqualizerNode() {
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
    
    public int getFrequency() {
        return frequency;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public double getQ() {
        return q;
    }
    
    public void setQ(double q) {
        this.q = q;
    }
    
    public double getGain() {
        return gain;
    }
    
    public void setGain(double gain) {
        this.gain = gain;
    }
    
    /* *********** */
    /* Comparators */
    /* *********** */
    
    /**
     * @since v0.1
     * @author eviljoe
     */
    public static class FrequencyEqualizerNodeComparator extends NullComparator<EqualizerNode> {
        
        /** {@inheritDoc} */
        @Override
        public int compareNonNulls(EqualizerNode a, EqualizerNode b) {
            return EMUtils.compare(a.getFrequency(), b.getFrequency(), getOrder());
        }
    }
}
