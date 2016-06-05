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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import em.utils.EMUtils;

/**
 * A class containing unit tests for {@link Queue}.
 * 
 * @see Queue
 * 
 * @since v0.1
 * @author eviljoe
 */
public class QueueTest {
    
    /** Tests to ensure that the {@link Queue#clone()} method will correctly create a deep clone. */
    @Test
    public void testClone() {
        final Queue orig = new Queue();
        final Queue cloneQueue;
        final QueueElement elem1 = new QueueElement(1);
        final QueueElement elem2 = new QueueElement(2);
        final QueueElement cloneElem1;
        final QueueElement cloneElem2;
        
        orig.setID(5);
        orig.setPlayIndex(1);
        orig.setElements(Arrays.asList(elem1, elem2));
        
        cloneQueue = orig.clone();
        assertNotNull(cloneQueue);
        assertTrue(orig != cloneQueue);
        assertEquals(orig.getID(), cloneQueue.getID());
        assertEquals(orig.getPlayIndex(), cloneQueue.getPlayIndex());
        
        cloneElem1 = EMUtils.findByID(cloneQueue.getElements(), elem1.getID());
        assertNotNull(cloneElem1);
        assertTrue(elem1 != cloneElem1);
        assertEquals(elem1.getID(), cloneElem1.getID());
        
        cloneElem2 = EMUtils.findByID(cloneQueue.getElements(), elem2.getID());
        assertNotNull(cloneElem2);
        assertTrue(elem2 != cloneElem2);
        assertEquals(elem2.getID(), cloneElem2.getID());
    }
    
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
    
    /**
     * Tests to ensure that the {@link Queue#clearElements()} function can be called when the queue does not contain any
     * elements (makes sure it doesn't throw any exceptions).
     */
    @Test
    public void testClearElements_NoElements() {
        final Queue queue = new Queue();
        
        queue.clearElements();
        assertEquals(0, queue.size());
    }
    
    /** Tests to ensure that the {@link Queue#clearElements()} function removes all elements from the queue. */
    @Test
    public void testClearElements_WithElements() {
        final Queue queue = new Queue();
        
        queue.addSongLast(new SongInfo(5));
        queue.addSongLast(new SongInfo(7));
        queue.addSongLast(new SongInfo(11));
        
        assertNotEquals(0, queue.size());
        queue.clearElements();
        assertEquals(0, queue.size());
    }
}
