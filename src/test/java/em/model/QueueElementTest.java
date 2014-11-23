package em.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A class containing unit tests for {@link QueueElement}.
 * 
 * @see QueueElement
 * 
 * @since v0.1
 * @author eviljoe
 */
public class QueueElementTest {
    
    /** Tests to ensure that the {@link QueueElement#clone()} method will clone correctly. */
    @Test
    public void testClone() {
        final QueueElement elem = new QueueElement();
        final QueueElement clone;
        final SongInfo song = new SongInfo(5);
        final SongInfo cloneSong;
        
        elem.setID(7);
        elem.setQueueIndex(11);
        elem.setPlayIndex(13);
        elem.setSong(song);
        
        clone = elem.clone();
        assertNotNull(clone);
        assertTrue(elem != clone);
        assertEquals(elem.getID(), clone.getID());
        assertEquals(elem.getQueueIndex(), clone.getQueueIndex());
        assertEquals(elem.getPlayIndex(), clone.getPlayIndex());
        
        cloneSong = clone.getSong();
        assertNotNull(cloneSong);
        assertTrue(song != cloneSong);
        assertEquals(song.getID(), cloneSong.getID());
    }
}
