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

package em.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A class containing unit tests for {@link EqualizerNode}.
 * 
 * @see EqualizerNode
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EqualizerNodeTest {
    
    /** Tests to ensure that the {@link EqualizerNode#clone()} method will clone correctly. */
    @Test
    public void testClone() {
        final EqualizerNode node = new EqualizerNode();
        final EqualizerNode clone;
        
        node.setID(5);
        node.setFrequency(55);
        node.setQ(2.5);
        node.setGain(3.5);
        
        clone = node.clone();
        assertNotNull(clone);
        assertTrue(node != clone);
        assertEquals(node.getID(), clone.getID());
        assertEquals(node.getFrequency(), clone.getFrequency());
        assertEquals(node.getQ(), clone.getQ(), 0.0);
        assertEquals(node.getGain(), clone.getGain(), 0.0);
    }
}
