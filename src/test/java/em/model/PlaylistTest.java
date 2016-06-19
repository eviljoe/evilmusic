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
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertNotSame;
import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.gen5.api.Assertions.assertSame;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

/**
 * @since v0.1
 * @author eviljoe
 */
@DisplayName("Playlist")
class PlaylistTest {
    
    private Playlist p;
    
    @BeforeEach
    void beforeEach() {
        p = new Playlist();
    }
    
    @Nested
    @DisplayName("getElement")
    class GetElement {
        
        @BeforeEach
        void beforeEach() {
            p.setElements(Arrays.asList(new PlaylistElement(0), new PlaylistElement(1), new PlaylistElement(2)));
        }
        
        @Test
        @DisplayName("returns null when the elements list is null")
        void returnsNullWhenElementsNull() {
            p.setElements(null);
            assertNull(p.getElement(0));
        }
        
        @Test
        @DisplayName("returns the element at the given index")
        void returnsElementAtIndex() {
            assertEquals(1, p.getElement(1).getID());
        }
        
        @Test
        @DisplayName("throws an exception when given an index that is too low")
        void throwsExceptionWhenTooLow() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.getElement(-1));
        }
        
        @Test
        @DisplayName("throws an exception when given an index that is too high")
        void throwsExceptionWhenTooHigh() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.getElement(4));
        }
    }
    
    @Nested
    @DisplayName("getSong")
    class GetSong {
        
        @BeforeEach
        void beforeEach() {
            p.addSongsLast(Arrays.asList(new SongInfo(0), new SongInfo(1), new SongInfo(2)));
        }
        
        @Test
        @DisplayName("returns null when the elements list is null")
        void returnsNullWhenElementsNull() {
            p.setElements(null);
            assertNull(p.getSong(0));
        }
        
        @Test
        @DisplayName("returns the song at the given index")
        void returnsElementAtIndex() {
            assertEquals(1, p.getSong(1).getID());
        }
        
        @Test
        @DisplayName("throws an exception when given an index that is too low")
        void throwsExceptionWhenTooLow() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.getSong(-1));
        }
        
        @Test
        @DisplayName("throws an exception when given an index that is too high")
        void throwsExceptionWhenTooHigh() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.getSong(4));
        }
    }
    
    @Nested
    @DisplayName("addSongLast")
    class AddSongLast {
        
        @Test
        @DisplayName("adds the song after the last existing element")
        void addsSongLast() {
            SongInfo s0 = new SongInfo();
            SongInfo s1 = new SongInfo();
            
            p.addSongLast(s0);
            p.addSongLast(s1);
            
            assertSame(s0, p.getSong(0));
            assertSame(s1, p.getSong(1));
        }
        
        @Test
        @DisplayName("does not add null")
        void doesNotAddNull() {
            p.addSongLast(null);
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("returns true if the song is added")
        void returnsTrueIfAdded() {
            assertTrue(p.addSongLast(new SongInfo()));
        }
        
        @Test
        @DisplayName("returns false if the song is null")
        void returnsFalseIfNotAdded() {
            assertFalse(p.addSongLast(null));
        }
    }
    
    @Nested
    @DisplayName("addSongsLast")
    class AddSongsLast {
        
        @Test
        @DisplayName("does not add anything when given null")
        void doesNotAddNullCollection() {
            p.addSongsLast(null);
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("does not add anything when given an empty collection")
        void doesNotAddEmtpyCollection() {
            p.addSongsLast(new ArrayList<>(0));
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("does not add null elements in the collection")
        void doesNotAddNullElements() {
            p.addSongsLast(Arrays.asList(null, new SongInfo(7), null));
            assertEquals(1, p.size());
            assertEquals(7, p.getSong(0).getID());
        }
        
        @Test
        @DisplayName("can add a single song")
        void addSingleSong() {
            p.addSongsLast(Arrays.asList(new SongInfo(10)));
            assertEquals(1, p.size());
        }
        
        @Test
        @DisplayName("can add multiple songs last")
        void addMultipleSongs() {
            p.addSongsLast(Arrays.asList(new SongInfo(10), new SongInfo(11), new SongInfo(12)));
            assertEquals(3, p.size());
            assertEquals(10, p.getSong(0).getID());
            assertEquals(11, p.getSong(1).getID());
            assertEquals(12, p.getSong(2).getID());
        }
        
        @Test
        @DisplayName("returns true if at least one song is added")
        void returnsTrueIfAdded() {
            assertFalse(p.addSongsLast(Arrays.asList(null, null, null)));
        }
        
        @Test
        @DisplayName("returns false if no song is added")
        void returnsFalseIfNotAdded() {
            assertTrue(p.addSongsLast(Arrays.asList(null, null, new SongInfo(12))));
        }
    }
    
    @Nested
    @DisplayName("addElementLast")
    class AddElementLast {
        
        @Test
        @DisplayName("does not add null elements")
        void doesNotAddNull() {
            p.addElementLast(null);
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("adds the element after the last element")
        void addsAfterLastElement() {
            p.addElementLast(new PlaylistElement(10));
            p.addElementLast(new PlaylistElement(11));
            p.addElementLast(new PlaylistElement(12));
            
            assertEquals(10, p.getElement(0).getID());
            assertEquals(11, p.getElement(1).getID());
            assertEquals(12, p.getElement(2).getID());
        }
        
        @Test
        @DisplayName("sets the playlist on the element")
        void setsPlaylistOnElement() {
            final PlaylistElement e = new PlaylistElement(10);
            
            e.setPlaylist(null);
            p.addElementLast(e);
            
            assertSame(p, e.getPlaylist());
        }
        
        @Test
        @DisplayName("returns true if the element is added")
        void returnsTrueIfNotAdded() {
            assertTrue(p.addElementLast(new PlaylistElement(1)));
        }
        
        @Test
        @DisplayName("returns false if the element is not added")
        void returnsFalseIfNotAdded() {
            assertFalse(p.addElementLast(null));
        }
    }
    
    @Nested
    @DisplayName("clearElements")
    class ClearElements {
        
        @Test
        @DisplayName("removes all elements from the playlist")
        void removesAllElements() {
            p.addSongsLast(Arrays.asList(new SongInfo(1), new SongInfo(2), new SongInfo(3)));
            p.clearElements();
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("does not cause an exception when there are no elements in the playlist")
        void doesNotThrowException() {
            p.clearElements();
            assertEquals(0, p.size());
        }
    }
    
    @Nested
    @DisplayName("removeElement")
    class RemoveElement {
        
        @BeforeEach
        void beforeEach() {
            p.addSongsLast(Arrays.asList(new SongInfo(10), new SongInfo(11), new SongInfo(12)));
        }
        
        @Test
        @DisplayName("does not remove anything when there are no elements")
        void doesNotRemoveWhenNoElements() {
            p.setElements(null);
            p.removeElement(0);
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("removes element at the given index")
        void removesElementAtIndex() {
            p.removeElement(1);
            
            assertEquals(2, p.size());
            assertEquals(10, p.getElement(0).getSong().getID());
            assertEquals(12, p.getElement(1).getSong().getID());
        }
        
        @Test
        @DisplayName("returns null when nothing is removed")
        void returnsNullWhenNothingRemoved() {
            p.setElements(null);
            assertNull(p.removeElement(0));
        }
        
        @Test
        @DisplayName("returns the removed element")
        void returnsRemovedElement() {
            assertEquals(11, p.removeElement(1).getSong().getID());
        }
        
        @Test
        @DisplayName("throws an exception when the index to remove is too low")
        void throwsExceptionWhenIndexTooLow() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.removeElement(-1));
        }
        
        @Test
        @DisplayName("throws an exception when the index to remove is too high")
        void throwsExceptionWhenIndexTooHigh() {
            assertThrows(IndexOutOfBoundsException.class, () -> p.removeElement(4));
        }
    }
    
    @Nested
    @DisplayName("removeElementByID")
    class RemoveElementByID {
        
        @BeforeEach
        void beforeEach() {
            p.addElementLast(new PlaylistElement(10));
            p.addElementLast(new PlaylistElement(11));
            p.addElementLast(new PlaylistElement(12));
        }
        
        @Test
        @DisplayName("removes the element with the given ID")
        void removesElementWithTheGivenID() {
            assertNotNull(p.removeElementByID(11));
            
            for(PlaylistElement e : p.getElements()) {
                assertNotEquals(11, e.getID());
            }
        }
        
        @Test
        @DisplayName("returns the removed element")
        void returnsRemovedElement() {
            assertEquals(11, p.removeElementByID(11).getID());
        }
        
        @Test
        @DisplayName("returns null if no element is removed")
        void returnsNullIfNoElementRemoved() {
            assertNull(p.removeElementByID(-1));
        }
        
        @Test
        @DisplayName("does not remove anything if there are no elements")
        void doesNotRemoveWhenEmpty() {
            p.clearElements();
            assertNull(p.removeElementByID(10));
        }
    }
    
    @Nested
    @DisplayName("size")
    class Size {
        
        @Test
        @DisplayName("returns 0 when the elements are null")
        void returnsZeroWhenNull() {
            p.setElements(null);
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("returns 0 when the elements are empty")
        void returnsZeroWhenEmpty() {
            p.setElements(new ArrayList<>(0));
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("returns the number of elements")
        void returnsNumberOfElements() {
            p.addSongsLast(Arrays.asList(new SongInfo(), new SongInfo(), new SongInfo()));
            assertEquals(3, p.size());
        }
    }
    
    @Nested
    @DisplayName("clone")
    class Clone {
        
        @BeforeEach
        void beforeEach() {
            p.setID(7);
            p.addSongLast(new SongInfo(10));
        }
        
        @Test
        @DisplayName("clones the playlist")
        void clonesPlaylist() {
            final Playlist clone = p.clone();
            
            assertNotNull(clone);
            assertNotSame(p, clone);
        }
        
        @Test
        @DisplayName("clones the ID")
        void clonesID() {
            assertEquals(p.getID(), p.clone().getID());
        }
        
        @Test
        @DisplayName("clones the name")
        void clonesName() {
            assertEquals(p.getName(), p.clone().getName());
        }
        
        @Test
        @DisplayName("clones the elements list")
        void clonesElementsList() {
            assertNotSame(p.getElements(), p.clone().getElements());
        }
        
        @Test
        @DisplayName("clones each element in the elements list")
        void clonesEachElement() {
            final Playlist clone = p.clone();
            
            assertNotSame(p.getElements().get(0), clone.getElements().get(0));
            assertEquals(p.getElements().get(0).getID(), clone.getElements().get(0).getID());
        }
    }
}
