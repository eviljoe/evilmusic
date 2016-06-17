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

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.gen5.api.Test;

import em.utils.EMUtils;

/**
 * A class containing unit tests for {@link Equalizer}.
 * 
 * @see Equalizer
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EqualizerTest {
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    /** Tests to ensure that the {@link Equalizer#clone()} method will correctly create a deep clone. */
    @Test
    public void testClone() {
        final Equalizer orig = new Equalizer();
        final Equalizer cloneEQ;
        final EqualizerNode node1 = createNode(1, 11000);
        final EqualizerNode node2 = createNode(2, 12000);
        final EqualizerNode cloneNode1;
        final EqualizerNode cloneNode2;
        
        orig.setID(5);
        orig.setNodes(Arrays.asList(node1, node2));
        
        cloneEQ = orig.clone();
        assertNotNull(cloneEQ);
        assertTrue(orig != cloneEQ);
        assertEquals(orig.getID(), cloneEQ.getID());
        
        cloneNode1 = EMUtils.findByID(cloneEQ.getNodes(), node1.getID());
        assertNotNull(cloneNode1);
        assertTrue(node1 != cloneNode1);
        assertEquals(node1.getID(), cloneNode1.getID());
        
        cloneNode2 = EMUtils.findByID(cloneEQ.getNodes(), node2.getID());
        assertTrue(node2 != cloneNode2);
        assertNotNull(cloneNode2);
        assertEquals(node2.getID(), cloneNode2.getID());
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    private EqualizerNode createNode(int id, int freq) {
        final EqualizerNode node = new EqualizerNode();
        
        node.setID(id);
        node.setFrequency(freq);
        
        return node;
    }
}
