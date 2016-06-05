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
