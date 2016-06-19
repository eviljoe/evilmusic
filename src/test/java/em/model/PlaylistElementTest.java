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
import static org.junit.gen5.api.Assertions.assertNotSame;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

/**
 * @since v0.1
 * @author eviljoe
 */
public class PlaylistElementTest {
    
    PlaylistElement elem;
    
    @BeforeEach
    void beforeEach() {
        elem = new PlaylistElement();
    }
    
    @Nested
    @DisplayName("clone")
    class Clone {
        
        @BeforeEach
        void beforeEach() {
            elem.setID(7);
            elem.setSong(new SongInfo(10));
        }
        
        @Test
        @DisplayName("clones the element")
        void clonesTheElement() {
            final PlaylistElement clone = elem.clone();
            
            assertNotNull(clone);
            assertNotSame(elem, clone);
        }
        
        @Test
        @DisplayName("clones the ID")
        void clonesTheID() {
            assertEquals(elem.getID(), elem.clone().getID());
        }
        
        @Test
        @DisplayName("clones the song")
        void clonesTheSong() {
            final PlaylistElement clone = elem.clone();
            
            assertNotSame(elem.getSong(), clone.getSong());
            assertEquals(elem.getSong().getID(), clone.getSong().getID());
        }
    }
}
