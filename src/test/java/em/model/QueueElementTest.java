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

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;

import org.junit.gen5.api.Test;

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
