package em.test.rest.queue;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import em.controllers.queue.QueueController;
import em.model.Queue;
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
    public void setUp() {
        queuesToCleanup = new ArrayList<Queue>();
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
    
    @Test
    public void testDeleteQueue_Valid() throws IOException {
        QueueRESTCalls.deleteQueue(QueueRESTCalls.createQueue().getID());
    }
    
    @Test
    public void testDeleteQueue_Invalid() throws IOException {
        QueueRESTCalls.deleteQueue(-3, 404);
    }
}
