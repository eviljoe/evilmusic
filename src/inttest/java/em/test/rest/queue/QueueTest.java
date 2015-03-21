package em.test.rest.queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import em.controllers.queue.QueueController;
import em.model.Library;
import em.model.Queue;
import em.model.SongInfo;
import em.test.rest.calls.LibraryRESTCalls;
import em.test.rest.calls.QueueRESTCalls;

/**
 * REST tests for the {@link QueueController} end-points.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class QueueTest {
    
    private List<Queue> queuesToCleanup;
    
    /* ************** */
    /* Before / After */
    /* ************** */
    
    @Before
    public void setUp() throws IOException {
        queuesToCleanup = new ArrayList<Queue>();
        LibraryRESTCalls.rebuildLibrary();
    }
    
    @After
    public void tearDown() {
        for(Queue q : queuesToCleanup) {
            if(q != null) {
                try {
                    QueueRESTCalls.deleteQueue(q.getID());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /* ************ */
    /* Create Tests */
    /* ************ */
    
    /** Tests to ensure that a queue can be created. */
    @Test
    public void testCreateQueue() throws IOException {
        final Queue q = QueueRESTCalls.createQueue();
        
        queuesToCleanup.add(q);
        assertNotNull(q);
        assertNotNull(q.getID());
    }
    
    /* ************ */
    /* Delete Tests */
    /* ************ */
    
    /** Tests to ensure that a queue can be deleted. */
    @Test
    public void testDeleteQueue_Valid() throws IOException {
        QueueRESTCalls.deleteQueue(QueueRESTCalls.createQueue().getID());
    }
    
    /** Tests to ensure that HTTP 404 will be returned when attempting to delete a queue that does not exist. */
    @Test
    public void testDeleteQueue_Invalid() throws IOException {
        QueueRESTCalls.deleteQueue(Integer.MIN_VALUE + 11, 404);
    }
    
    /* ************** */
    /* Add Last Tests */
    /* ************** */
    
    /** Tests to ensure that a single song can be added to the queue. */
    @Test
    public void testAddLast_Single_Once() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        final SongInfo song = lib.getSongs().get(0);
        Queue q = QueueRESTCalls.createQueue();
        
        // Add the song to the queue
        queuesToCleanup.add(q);
        QueueRESTCalls.addLast(200, q.getID(), song.getID());
        
        // Get the queue and verify the song was added
        q = QueueRESTCalls.getQueue(q.getID());
        assertEquals(1, q.size());
        assertEquals(song.getID(), q.getElements().get(0).getSong().getID());
    }
    
    /** Tests to ensure that songs are added to the end of the queue when added one at a time. */
    @Test
    public void testAddLast_Single_Twice() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        final SongInfo song1 = lib.getSongs().get(0);
        final SongInfo song2 = lib.getSongs().get(1);
        Queue q = QueueRESTCalls.createQueue();
        
        // Add the songs to the queue
        queuesToCleanup.add(q);
        QueueRESTCalls.addLast(200, q.getID(), song1.getID());
        QueueRESTCalls.addLast(200, q.getID(), song2.getID());
        
        // Get the queue and verify the song was added
        q = QueueRESTCalls.getQueue(q.getID());
        assertEquals(2, q.size());
        assertEquals(song1.getID(), q.getElements().get(0).getSong().getID());
        assertEquals(song2.getID(), q.getElements().get(1).getSong().getID());
    }
    
    /** Tests to ensure that HTTP 404 will be returned when adding a song to a non-existing queue. */
    @Test
    public void testAddLast_Single_InvalidQueue() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        final SongInfo song = lib.getSongs().get(0);
        
        // Add the song to the queue using a queue ID that does not exist
        QueueRESTCalls.addLast(404, Integer.MIN_VALUE + 117, song.getID());
    }
    
    /** Tests to ensure that HTTP 404 will be returned when a non-existing song is added to a queue. */
    @Test
    public void testAddLast_Single_InvalidSong() throws IOException {
        final Queue q = QueueRESTCalls.createQueue();
        
        queuesToCleanup.add(q);
        
        // Add the song to the queue using a queue ID that does not exist
        QueueRESTCalls.addLast(404, q.getID(), Integer.MIN_VALUE + 117);
    }
    
    /** Tests to ensure that multiple songs can be added to the queue at once. */
    @Test
    public void testAddLast_Multiple_Once() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        final int songID1 = lib.getSongs().get(0).getID();
        final int songID2 = lib.getSongs().get(1).getID();
        Queue q = QueueRESTCalls.createQueue();
        final int qID;
        
        queuesToCleanup.add(q);
        qID = q.getID();
        
        // Add the songs to the queue and verify the returned queue contains the songs
        q = QueueRESTCalls.addLast(200, qID, songID1, songID2);
        assertEquals(new Integer(songID1), q.getSong(0).getID());
        assertEquals(new Integer(songID2), q.getSong(1).getID());
        
        // Get the queue and verify the songs were added
        q = QueueRESTCalls.getQueue(qID);
        assertEquals(new Integer(songID1), q.getSong(0).getID());
        assertEquals(new Integer(songID2), q.getSong(1).getID());
    }
    
    /**
     * Tests to ensure that songs are added to the end of the queue when adding multiple songs at once. This also tests
     * to ensure that the songs are added in the order that they are specified.
     */
    @Test
    public void testAddLast_Multiple_Twice() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        final int songID1 = lib.getSongs().get(0).getID();
        final int songID2 = lib.getSongs().get(1).getID();
        final int songID3 = lib.getSongs().get(2).getID();
        final int songID4 = lib.getSongs().get(3).getID();
        Queue q = QueueRESTCalls.createQueue();
        final int qID;
        
        queuesToCleanup.add(q);
        qID = q.getID();
        
        // Add the first set of songs to the queue and verify the returned queue contains the songs (in order)
        q = QueueRESTCalls.addLast(200, qID, songID1, songID2);
        assertEquals(new Integer(songID1), q.getSong(0).getID());
        assertEquals(new Integer(songID2), q.getSong(1).getID());
        
        // Add the second set of songs to the queue and verify the returned queue contains the songs (in order)
        q = QueueRESTCalls.addLast(200, qID, songID3, songID4);
        assertEquals(new Integer(songID1), q.getSong(0).getID());
        assertEquals(new Integer(songID2), q.getSong(1).getID());
        assertEquals(new Integer(songID3), q.getSong(2).getID());
        assertEquals(new Integer(songID4), q.getSong(3).getID());
        
        // Get the queue and verify the songs were added
        q = QueueRESTCalls.getQueue(qID);
        assertEquals(new Integer(songID1), q.getSong(0).getID());
        assertEquals(new Integer(songID2), q.getSong(1).getID());
        assertEquals(new Integer(songID3), q.getSong(2).getID());
        assertEquals(new Integer(songID4), q.getSong(3).getID());
    }
    
    /**
     * Tests to ensure that HTTP 404 will be returned when one of the songs being added in a single transaction is
     * invalid. Also, the queue should not be updated at all (even with the valid songs).
     */
    @Test
    public void testAddLast_Multiple_OneInvalidSong() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        Queue q = QueueRESTCalls.createQueue();
        final int qID;
        
        queuesToCleanup.add(q);
        qID = q.getID();
        
        // Add the songs to the queue and and verify the server does not allow it
        q = QueueRESTCalls.addLast(404, qID, lib.getSongs().get(0).getID(), Integer.MIN_VALUE + 117);
        
        // Get the queue and verify the valid song wasn't added
        q = QueueRESTCalls.getQueue(qID);
        assertEquals(0, q.size());
    }
    
    /* ********* */
    /* Get Tests */
    /* ********* */
    
    /** Tests to ensure that a queue can be obtained when given a valid queue ID. */
    @Test
    public void testGetQueue_Valid() throws IOException {
        Queue q = QueueRESTCalls.createQueue();
        final int qID = q.getID();
        
        queuesToCleanup.add(q);
        
        // Get the queue and verify the correct one was returned
        q = QueueRESTCalls.getQueue(qID);
        assertNotNull(q);
        assertEquals(new Integer(qID), q.getID());
    }
    
    /** Tests to ensure that HTTP 404 will be returned when an attempting to obtain a queue using an invalid ID. */
    @Test
    public void testGetQueue_Invalid() throws IOException {
        QueueRESTCalls.getQueue(404, Integer.MIN_VALUE + 117);
    }
    
    /* *********** */
    /* Clear Tests */
    /* *********** */
    
    /** Tests to ensure that a queue can be cleared. */
    @Test
    public void testClearQueue_HasElements() throws IOException {
        final Library lib = LibraryRESTCalls.getLibrary();
        Queue q = QueueRESTCalls.createQueue();
        final int qID;
        
        queuesToCleanup.add(q);
        qID = q.getID();
        
        // Add songs to the queue
        QueueRESTCalls.addLast(200, qID, lib.getSongs().get(0).getID(), lib.getSongs().get(1).getID());
        
        // Clear the queue and verify the returned queue no longer contains any songs
        q = QueueRESTCalls.clear(qID);
        assertEquals(0, q.size());
        
        // Get the queue and verify it does not contain any songs
        q = QueueRESTCalls.getQueue(qID);
        assertEquals(0, q.size());
    }
    
    /** Tests to ensure that the server will not have a problem with clearing a queue that is already empty. */
    @Test
    public void testClearQueue_Empty() throws IOException {
        Queue q = QueueRESTCalls.createQueue();
        final int qID;
        
        queuesToCleanup.add(q);
        qID = q.getID();
        
        // Clear the queue
        QueueRESTCalls.clear(qID);
    }
    
    /** Tests to ensure that an HTTP 404 will be returned when attempting to clear a queue that does not exist. */
    @Test
    public void testClearQueue_Invalid() throws IOException {
        QueueRESTCalls.clear(404, Integer.MIN_VALUE + 117);
    }
    
    /* ******************** */
    /* Remove Element Tests */
    /* ******************** */
    
    // JOE todo
    
}
