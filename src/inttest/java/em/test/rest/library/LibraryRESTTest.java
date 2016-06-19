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

package em.test.rest.library;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;
import java.util.Collection;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import em.controllers.LibraryController;
import em.model.Library;
import em.model.SongInfo;
import em.test.rest.calls.LibraryRESTCalls;
import em.utils.IDSet;

/**
 * A class containing REST tests for {@link LibraryController}.
 * 
 * @see LibraryController
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryRESTTest {
    
    @BeforeEach
    public void beforeEach() throws IOException {
        LibraryRESTCalls.rebuildLibrary();
    }
    
    @Nested
    @DisplayName("Get")
    class Get {
        
        @Test
        @DisplayName("requests the library")
        public void gets() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            
            assertThat(lib, is(not(nullValue())));
            assertThat(lib.getSongs(), is(not(nullValue())));
            assertThat(lib.getSongs(), is(not(empty())));
        }
        
        @Test
        @DisplayName("does not contain song files")
        public void songsDoNotHaveFile() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            
            for(SongInfo song : lib.getSongs()) {
                assertThat(song.getFile(), is(nullValue()));
            }
        }
    }
    
    @Nested
    @DisplayName("Rebuild")
    class Rebuild {
        
        @Test
        @DisplayName("rebuilds the library")
        public void rebuilds() throws IOException {
            final Library lib;
            final Collection<SongInfo> songs;
            
            LibraryRESTCalls.rebuildLibrary();
            lib = LibraryRESTCalls.getLibrary();
            
            assertThat(lib, is(not(nullValue())));
            
            songs = lib.getSongs();
            assertThat(songs, is(not(nullValue())));
            assertThat(songs, is(not(empty())));
        }
        
        @Test
        @DisplayName("returns the library as it exists on the server")
        public void returnsLibrary() throws IOException {
            assertThat(LibraryRESTCalls.rebuildLibrary(), is(not(nullValue())));
        }
        
        @Test
        @DisplayName("maintains a songs ID in the rebuilt library")
        public void hasSameSongIDs() throws IOException {
            final IDSet<SongInfo> songsBefore = new IDSet<>(LibraryRESTCalls.getLibrary().getSongs());
            final Collection<SongInfo> songsAfter = LibraryRESTCalls.rebuildLibrary().getSongs();
            
            // Make sure that all of the songs after the rebuild can be matched up to one before the rebuild
            for(SongInfo song : songsAfter) {
                assertThat(songsBefore.remove(song), is(not(nullValue())));
            }
            
            assertThat(songsBefore, is(empty()));
        }
    }
    
    @Nested
    @DisplayName("clear")
    class Clear {
        
        @Test
        @DisplayName("clears the library")
        public void clears() throws IOException {
            LibraryRESTCalls.clearLibrary();
            assertThat(LibraryRESTCalls.getLibrary().getSongs(), is(empty()));
        }
        
        @Test
        @DisplayName("returns the library as it exists on the server")
        public void returnsLibrary() throws IOException {
            assertThat(LibraryRESTCalls.clearLibrary(), is(not(nullValue())));
        }
    }
}
