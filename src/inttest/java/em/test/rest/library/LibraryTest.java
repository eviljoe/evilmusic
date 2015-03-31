/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2015 Joe Falascino
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import em.controllers.library.LibraryController;
import em.model.Library;
import em.model.SongInfo;
import em.test.rest.calls.LibraryRESTCalls;
import em.utils.EMUtils;

/**
 * A class containing REST tests for {@link LibraryController}.
 * 
 * @see LibraryController
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryTest {
    
    /* ************** */
    /* Before / After */
    /* ************** */
    
    @Before
    public void setUp() throws IOException {
        LibraryRESTCalls.rebuildLibrary();
    }
    
    /* ************* */
    /* Rebuild Tests */
    /* ************* */
    
    /** Tests to ensure that the library can be rebuilt. */
    @Test
    public void testRebuildLibrary() throws IOException {
        final Library lib;
        final Collection<SongInfo> songs;
        
        LibraryRESTCalls.rebuildLibrary();
        lib = LibraryRESTCalls.getLibrary();
        
        assertNotNull(lib);
        
        songs = lib.getSongs();
        assertNotNull(songs);
        assertTrue(songs.size() > 0);
    }
    
    /* ************* */
    /* Clear Tests */
    /* ************* */
    
    /** Tests to ensure that the library can be cleared. */
    @Test
    public void testClearLibrary() throws IOException {
        LibraryRESTCalls.clearLibrary();
        assertFalse(EMUtils.hasValues(LibraryRESTCalls.getLibrary().getSongs()));
    }
}
