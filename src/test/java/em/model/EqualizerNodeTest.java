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
