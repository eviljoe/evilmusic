/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
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

package em.utils;

import java.util.ArrayList;
import java.util.List;

import em.model.Equalizer;
import em.model.EqualizerNode;

/**
 * A class containing utility functions related to {@link Equalizer}s.
 * 
 * @see Equalizer
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EqualizerUtils {
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** A private constructor so this class cannot be instantiated or extended. */
    private EqualizerUtils() {
        super();
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    public static Equalizer createDefaultEqualizer() {
        final Equalizer eq = new Equalizer();
        final DefaultEqualizerSettings[] settings = DefaultEqualizerSettings.values();
        final List<EqualizerNode> sliders = new ArrayList<>(settings.length);
        
        for(DefaultEqualizerSettings setting : settings) {
            final EqualizerNode slider = new EqualizerNode();
            
            slider.setFrequency(setting.getFrequency());
            slider.setQ(setting.getQ());
            slider.setGain(setting.getGain());
            
            sliders.add(slider);
        }
        
        eq.setNodes(sliders);
        
        return eq;
    }
    
    /* ************************ */
    /* Default EQ Settings Enum */
    /* ************************ */
    
    /**
     * @since v0.1
     * @author eviljoe
     */
    private static enum DefaultEqualizerSettings {
        
        SLIDER_1(55),
        SLIDER_2(77),
        SLIDER_3(110),
        SLIDER_4(156),
        SLIDER_5(311),
        SLIDER_6(440),
        SLIDER_7(622),
        SLIDER_8(880),
        SLIDER_9(1200),
        SLIDER_10(1800),
        SLIDER_11(3500),
        SLIDER_12(5000),
        SLIDER_13(7000),
        SLIDER_14(10000),
        SLIDER_15(14000),
        SLIDER_16(20000);
        
        private final int frequency;
        private final double q;
        private final double gain;
        
        private DefaultEqualizerSettings(int frequency) {
            this.frequency = frequency;
            this.q = 2.5;
            this.gain = 0.0;
        }
        
        public int getFrequency() {
            return frequency;
        }
        
        public double getQ() {
            return q;
        }
        
        public double getGain() {
            return gain;
        }
    }
}
