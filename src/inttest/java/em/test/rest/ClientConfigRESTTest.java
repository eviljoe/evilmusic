package em.test.rest;

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
}
