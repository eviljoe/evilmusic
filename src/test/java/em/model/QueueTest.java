package em.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class QueueTest {
    
    /** Tests to ensure that the {@link Queue#addLast(SongInfo)} function will add a song to the queue. */
    @Test
    public void testAddSongLast() {
        final Queue queue = new Queue();
        final SongInfo a = new SongInfo(5);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = new SongInfo(11);
        final QueueElement eA;
        final QueueElement eB;
        final QueueElement eC;
        
        assertTrue(queue.addSongLast(a));
        eA = queue.getElements().get(0);
        assertEquals(a.getID(), eA.getSong().getID());
        
        assertTrue(queue.addSongLast(b));
        eB = queue.getElements().get(1);
        assertEquals(b.getID(), eB.getSong().getID());
        
        assertTrue(queue.addSongLast(c));
        eC = queue.getElements().get(2);
        assertEquals(c.getID(), eC.getSong().getID());
    }
    
    /** Tests to ensure that the {@link Queue#addLast(SongInfo)} function not add a {@code null} song. */
    @Test
    public void testAddSongLast_Null() {
        final Queue queue = new Queue();
        assertFalse(queue.addSongLast(null));
    }
    
    /**
     * Tests to ensure that {@link Queue#addSongsLast(Collection)} will correctly add a collection of songs to the end
     * of the queue.
     */
    @Test
    public void testAddSongsLast() {
        final Queue queue = new Queue();
        final SongInfo a = new SongInfo(5);
        final SongInfo b = null;
        final SongInfo c = new SongInfo(7);
        final SongInfo d = new SongInfo(11);
        final List<SongInfo> songs = Arrays.asList(a, b, c, d);
        
        assertTrue(queue.addSongsLast(songs));
        assertEquals(3, queue.size());
        
        assertEquals(a.getID(), queue.getElement(0).getSong().getID());
        assertEquals(c.getID(), queue.getElement(1).getSong().getID());
        assertEquals(d.getID(), queue.getElement(2).getSong().getID());
    }
    
    /**
     * Tests to ensure that {@link Queue#addSongsLast(Collection)} will not add anything to the queue when given an
     * empty collection.
     */
    @Test
    public void testAddSongsLast_Empty() {
        final Queue queue = new Queue();
        final List<SongInfo> songs = new ArrayList<>();
        
        assertFalse(queue.addSongsLast(songs));
        assertEquals(0, queue.size());
        
    }
    
    /**
     * Tests to ensure that {@link Queue#addSongsLast(Collection)} will not add anything to the queue when given a
     * collection containing only {@code null} elements.
     */
    @Test
    public void testAddSongsLast_NullElements() {
        final Queue queue = new Queue();
        final List<SongInfo> songs = Arrays.asList(null, null, null);
        
        assertFalse(queue.addSongsLast(songs));
        assertEquals(0, queue.size());
        
    }
    
    /**
     * Tests to ensure that {@link Queue#addSongsLast(Collection)} will not add anything to the queue when given a
     * {@code null} collection.
     */
    @Test
    public void testAddSongsLast_Null() {
        final Queue queue = new Queue();
        
        assertFalse(queue.addSongsLast(null));
        assertEquals(0, queue.size());
    }
    
    /** Tests to ensure that {@link Queue#removeElement(int)} will remove the element at the given index. */
    @Test
    public void testRemoveSong() {
        final Queue queue = new Queue();
        final SongInfo a = new SongInfo(5);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = new SongInfo(11);
        final List<SongInfo> songs = Arrays.asList(a, b, c);
        
        queue.addSongsLast(songs);
        
        assertEquals(b.getID(), queue.removeElement(1).getSong().getID());
        assertEquals(2, queue.size());
        assertEquals(a.getID(), queue.getElement(0).getSong().getID());
        assertEquals(c.getID(), queue.getElement(1).getSong().getID());
        
        assertEquals(a.getID(), queue.removeElement(0).getSong().getID());
        assertEquals(1, queue.size());
        assertEquals(c.getID(), queue.getElement(0).getSong().getID());
        
        assertEquals(c.getID(), queue.removeElement(0).getSong().getID());
        assertEquals(0, queue.size());
    }
    
    /**
     * Tests to ensure that {@link Queue#removeElement(int)} will throw an exception when given an index that is too
     * high.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveSong_IndexTooHigh() throws IndexOutOfBoundsException {
        final Queue queue = new Queue();
        final List<SongInfo> songs = Arrays.asList(new SongInfo(5), new SongInfo(7), new SongInfo(11));
        
        queue.addSongsLast(songs);
        queue.removeElement(-1);
    }
    
    /**
     * Tests to ensure that {@link Queue#removeElement(int)} will throw an exception when given an index that is too
     * low.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveSong_IndexTooLow() throws IndexOutOfBoundsException {
        final Queue queue = new Queue();
        final List<SongInfo> songs = Arrays.asList(new SongInfo(5), new SongInfo(7), new SongInfo(11));
        
        queue.addSongsLast(songs);
        queue.removeElement(3);
    }
    
    /**
     * Tests to ensure that the queue elements' indices are correct after the {@link Queue#setElements(Collection)}
     * function is used to add elements to the queue.
     */
    @Test
    public void testIndicesAfterSetElements() {
        final Queue queue = new Queue();
        // @formatter:off
        final List<QueueElement> elements = Arrays.asList(
                new QueueElement(5, new SongInfo(7)),
                new QueueElement(11, new SongInfo(13)),
                new QueueElement(17, new SongInfo(19)),
                new QueueElement(23, new SongInfo(29)),
                new QueueElement(31, new SongInfo(37)));
        // @formatter:on
        
        queue.setPlayIndex(2);
        queue.setElements(elements);
        
        assertEquals(0, queue.getElement(0).getQueueIndex());
        assertEquals(1, queue.getElement(1).getQueueIndex());
        assertEquals(2, queue.getElement(2).getQueueIndex());
        assertEquals(3, queue.getElement(3).getQueueIndex());
        assertEquals(4, queue.getElement(4).getQueueIndex());
        
        assertEquals(-2, queue.getElement(0).getPlayIndex());
        assertEquals(-1, queue.getElement(1).getPlayIndex());
        assertEquals(0, queue.getElement(2).getPlayIndex());
        assertEquals(1, queue.getElement(3).getPlayIndex());
        assertEquals(2, queue.getElement(4).getPlayIndex());
    }
    
    /**
     * Tests to ensure that the queue elements' indices are correct after the {@link Queue#addSongLast(SongInfo)}
     * function is used to add a song to the queue.
     */
    @Test
    public void testIndicesAfterAddSongLast() {
        final Queue queue = new Queue();
        final SongInfo a = new SongInfo(5);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = new SongInfo(11);
        
        queue.addSongLast(a);
        assertEquals(0, queue.getElement(0).getPlayIndex());
        assertEquals(0, queue.getElement(0).getQueueIndex());
        
        queue.addSongLast(b);
        assertEquals(0, queue.getElement(0).getPlayIndex());
        assertEquals(0, queue.getElement(0).getQueueIndex());
        assertEquals(1, queue.getElement(1).getPlayIndex());
        assertEquals(1, queue.getElement(1).getQueueIndex());
        
        queue.addSongLast(c);
        assertEquals(0, queue.getElement(0).getPlayIndex());
        assertEquals(0, queue.getElement(0).getQueueIndex());
        assertEquals(1, queue.getElement(1).getPlayIndex());
        assertEquals(1, queue.getElement(1).getQueueIndex());
        assertEquals(2, queue.getElement(2).getPlayIndex());
        assertEquals(2, queue.getElement(2).getQueueIndex());
    }
    
    /**
     * Tests to ensure that the queue elements' indices are correct after the {@link Queue#addSongsLast(Collection)}
     * function is used to add songs to the queue.
     */
    @Test
    public void testIndicesAfterAddSongsLast() {
        final Queue queue = new Queue();
        // @formatter:off
        final List<SongInfo> songs = Arrays.asList(
                new SongInfo(5),
                new SongInfo(7),
                new SongInfo(11),
                new SongInfo(13),
                new SongInfo(17));
        // @formatter:on
        
        queue.addSongsLast(songs);
        queue.setPlayIndex(1);
        
        assertEquals(0, queue.getElement(0).getQueueIndex());
        assertEquals(1, queue.getElement(1).getQueueIndex());
        assertEquals(2, queue.getElement(2).getQueueIndex());
        assertEquals(3, queue.getElement(3).getQueueIndex());
        assertEquals(4, queue.getElement(4).getQueueIndex());
        
        assertEquals(-1, queue.getElement(0).getPlayIndex());
        assertEquals(0, queue.getElement(1).getPlayIndex());
        assertEquals(1, queue.getElement(2).getPlayIndex());
        assertEquals(2, queue.getElement(3).getPlayIndex());
        assertEquals(3, queue.getElement(4).getPlayIndex());
    }
    
    /**
     * Tests to ensure that the queue elements' indices are correct after the queue's play index is changed using the
     * {@link Queue#setPlayIndex(int)} function.
     */
    @Test
    public void testIndicesAfterSetPlayIndex() {
        final Queue queue = new Queue();
        // @formatter:off
        final List<QueueElement> elements = Arrays.asList(
                new QueueElement(5, new SongInfo(7)),
                new QueueElement(11, new SongInfo(13)),
                new QueueElement(17, new SongInfo(19)),
                new QueueElement(23, new SongInfo(29)),
                new QueueElement(31, new SongInfo(37)));
        // @formatter:on
        
        queue.setElements(elements);
        queue.setPlayIndex(3);
        
        assertEquals(0, queue.getElement(0).getQueueIndex());
        assertEquals(1, queue.getElement(1).getQueueIndex());
        assertEquals(2, queue.getElement(2).getQueueIndex());
        assertEquals(3, queue.getElement(3).getQueueIndex());
        assertEquals(4, queue.getElement(4).getQueueIndex());
        
        assertEquals(-3, queue.getElement(0).getPlayIndex());
        assertEquals(-2, queue.getElement(1).getPlayIndex());
        assertEquals(-1, queue.getElement(2).getPlayIndex());
        assertEquals(0, queue.getElement(3).getPlayIndex());
        assertEquals(1, queue.getElement(4).getPlayIndex());
    }
}
