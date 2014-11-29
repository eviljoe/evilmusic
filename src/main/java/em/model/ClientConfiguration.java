package em.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class ClientConfiguration implements Identifiable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private double volume;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public ClientConfiguration() {
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
    
    public double getVolume() {
        return volume;
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
    }
}
