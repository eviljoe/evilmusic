package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import em.model.SongInfo;

public class QueueManagerTest {
    
    /* *************************** */
    /* Setup & Tear Down Functions */
    /* *************************** */
    
    @BeforeClass
    public static void setupClass() {
        QueueManager.getInstance();
    }
    
    @AfterClass
    public static void tearDownClass() {
        QueueManager.getInstance().clear();
    }
    
    @Before
    public void setup() {
        QueueManager.getInstance().clear();
    }
    
    @After
    public void tearDown() {
        QueueManager.getInstance().clear();
    }
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    @Test
    public void testClear() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertTrue(qmgr.queueLast(createSongInfo(5)));
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        
        assertEquals(3, qmgr.size());
        qmgr.clear();
        assertEquals(0, qmgr.size());
    }
    
    @Test
    public void testSize() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertEquals(0, qmgr.size());
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertEquals(1, qmgr.size());
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        assertEquals(2, qmgr.size());
        assertTrue(qmgr.queueLast(createSongInfo(11)));
        assertEquals(3, qmgr.size());
        qmgr.clear();
        assertEquals(0, qmgr.size());
    }
    
    @Test
    public void testIsEmpty() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertTrue(qmgr.isEmpty());
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        assertTrue(qmgr.queueLast(createSongInfo(11)));
        assertFalse(qmgr.isEmpty());
        qmgr.clear();
        assertTrue(qmgr.isEmpty());
    }
    
    @Test
    public void testRemove() {
        final QueueManager qmgr = QueueManager.getInstance();
        final SongInfo a = createSongInfo(3);
        final SongInfo b = createSongInfo(7);
        final SongInfo c = createSongInfo(11);
        
        assertTrue(qmgr.queueLast(a));
        assertTrue(qmgr.queueLast(b));
        assertTrue(qmgr.queueLast(c));
        
        assertEquals(b.getID(), qmgr.remove(1).getID());
        assertEquals(2, qmgr.size());
        
        assertEquals(c.getID(), qmgr.remove(1).getID());
        assertEquals(1, qmgr.size());
        
        assertEquals(a.getID(), qmgr.remove(0).getID());
        assertEquals(0, qmgr.size());
    }
    
    @Test
    public void testRemove_RemoveFromEmpty() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        qmgr.clear();
        assertNull(qmgr.remove(0));
    }
    
    @Test
    public void testRemove_IndexTooHigh() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        assertTrue(qmgr.queueLast(createSongInfo(11)));
        
        assertNull(qmgr.remove(Integer.MAX_VALUE));
    }
    
    @Test
    public void testRemove_IndexTooLow() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        assertTrue(qmgr.queueLast(createSongInfo(11)));
        
        assertNull(qmgr.remove(Integer.MIN_VALUE));
    }
    
    @Test
    public void testQueueLast_Collection() {
        final QueueManager qmgr = QueueManager.getInstance();
        final ArrayList<SongInfo> infos1 = new ArrayList<>();
        final ArrayList<SongInfo> infos2 = new ArrayList<>();
        final SongInfo a = createSongInfo(2);
        final SongInfo b = createSongInfo(3);
        final SongInfo c = createSongInfo(5);
        final SongInfo d = createSongInfo(7);
        final SongInfo e = createSongInfo(11);
        final SongInfo f = createSongInfo(13);
        
        infos1.add(a);
        infos1.add(b);
        infos1.add(c);
        
        assertTrue(qmgr.queueLast(infos1));
        assertEquals(3, qmgr.size());
        assertEquals(a.getID(), qmgr.getContents().get(0).getID());
        assertEquals(b.getID(), qmgr.getContents().get(1).getID());
        assertEquals(c.getID(), qmgr.getContents().get(2).getID());
        
        infos2.add(a);
        infos2.add(b);
        infos2.add(c);
        infos2.add(d);
        infos2.add(e);
        infos2.add(f);
        
        assertTrue(qmgr.queueLast(infos2));
        assertEquals(9, qmgr.size());
        assertEquals(a.getID(), qmgr.getContents().get(0).getID());
        assertEquals(b.getID(), qmgr.getContents().get(1).getID());
        assertEquals(c.getID(), qmgr.getContents().get(2).getID());
        assertEquals(a.getID(), qmgr.getContents().get(3).getID());
        assertEquals(b.getID(), qmgr.getContents().get(4).getID());
        assertEquals(c.getID(), qmgr.getContents().get(5).getID());
        assertEquals(d.getID(), qmgr.getContents().get(6).getID());
        assertEquals(e.getID(), qmgr.getContents().get(7).getID());
        assertEquals(f.getID(), qmgr.getContents().get(8).getID());
    }
    
    @Test
    public void testQueueLast_Collection_Empty() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertFalse(qmgr.queueLast(new ArrayList<SongInfo>()));
        assertEquals(0, qmgr.size());
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertEquals(1, qmgr.size());
        assertFalse(qmgr.queueLast(new ArrayList<SongInfo>()));
        assertEquals(1, qmgr.size());
    }
    
    @Test
    public void testQueueLast_Collection_ContainsNulls() {
        final QueueManager qmgr = QueueManager.getInstance();
        final ArrayList<SongInfo> infos1 = new ArrayList<>();
        final ArrayList<SongInfo> infos2 = new ArrayList<>();
        
        infos1.add(null);
        infos1.add(null);
        
        infos2.add(null);
        infos2.add(null);
        infos2.add(null);
        
        assertFalse(qmgr.queueLast(infos1));
        assertEquals(0, qmgr.size());
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertEquals(1, qmgr.size());
        assertFalse(qmgr.queueLast(infos2));
        assertEquals(1, qmgr.size());
    }
    
    @Test
    public void testQueueLast_Collection_Null() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertFalse(qmgr.queueLast((Collection<SongInfo>)null));
        assertEquals(0, qmgr.size());
        
        assertTrue(qmgr.queueLast(createSongInfo(3)));
        assertEquals(1, qmgr.size());
        assertFalse(qmgr.queueLast((Collection<SongInfo>)null));
        assertEquals(1, qmgr.size());
    }
    
    @Test
    public void testQueueLast_SongInfo() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertTrue(qmgr.queueLast(createSongInfo(11)));
        assertTrue(qmgr.queueLast(createSongInfo(7)));
        assertTrue(qmgr.queueLast(createSongInfo(5)));
        
        assertEquals(3, qmgr.size());
        assertEquals(new Integer(11), qmgr.getContents().get(0).getID());
        assertEquals(new Integer(7), qmgr.getContents().get(1).getID());
        assertEquals(new Integer(5), qmgr.getContents().get(2).getID());
    }
    
    @Test
    public void testQueueLast_SongInfo_Null() {
        final QueueManager qmgr = QueueManager.getInstance();
        
        assertFalse(qmgr.queueLast((SongInfo)null));
        assertEquals(0, qmgr.size());
        
        assertTrue(qmgr.queueLast(createSongInfo(1)));
        assertEquals(1, qmgr.size());
        assertFalse(qmgr.queueLast((SongInfo)null));
        assertEquals(1, qmgr.size());
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    private SongInfo createSongInfo(int id) {
        return new SongInfo(id);
    }
}
