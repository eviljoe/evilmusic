package em.test.rest.clientconfig;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import em.test.rest.calls.ClientConfigRESTCalls;

/**
 * @since v0.1
 * @author eviljoe
 */
public class ClientConfigRESTTest {
    
    @Test
    public void testGetVolume() {
        ClientConfigRESTCalls.getVolume();
    }
    
    @Test
    public void testPutVolume() {
        double volume = 20.0;
        
        assertEquals(volume, ClientConfigRESTCalls.putVolume(volume), 0.0);
        assertEquals(volume, ClientConfigRESTCalls.getVolume(), 0.0);
    }
}
