/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package em.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import em.utils.EMUtils;
import em.utils.NullComparator;

/**
 * @since v0.1
 * @author eviljoe
 */
@Entity
public class EqualizerNode implements Identifiable, Cloneable {
    
    private static final Logger LOG = Logger.getLogger(EqualizerNode.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EqualierNode_to_Equalizer"))
    private Equalizer equalizer;
    
    private int frequency;
    private double q;
    private double gain;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public EqualizerNode() {
        this(null);
    }
    
    public EqualizerNode(Integer id) {
        super();
        setID(id);
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
    
    @JsonIgnore
    public Equalizer getEqualizer() {
        return equalizer;
    }
    
    @JsonIgnore
    public void setEqualizer(Equalizer equalizer) {
        this.equalizer = equalizer;
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
    
    @Override
    public EqualizerNode clone() {
        EqualizerNode clone = null;
        
        try {
            clone = (EqualizerNode)super.clone();
        } catch(CloneNotSupportedException e) {
            LOG.log(Level.SEVERE, "Could not clone " + EqualizerNode.class.getName(), e);
            clone = null;
        }
        
        if(clone != null) {
            clone.id = id;
            clone.frequency = frequency;
            clone.q = q;
            clone.gain = gain;
        }
        
        return clone;
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
