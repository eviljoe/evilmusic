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
package em.test.rest.playlist;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.gen5.api.AfterEach;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import em.model.Library;
import em.model.Playlist;
import em.model.PlaylistElement;
import em.model.SongInfo;
import em.test.rest.calls.LibraryRESTCalls;
import em.test.rest.calls.PlaylistRESTCalls;
import em.utils.IDSet;

/**
 * @since v0.1
 * @author eviljoe
 */
public class PlaylistRESTTest {
    
    private List<Playlist> playlistsToCleanup;
    
    @BeforeEach
    void beforeEach() throws IOException {
        playlistsToCleanup = new ArrayList<Playlist>();
        LibraryRESTCalls.rebuildLibrary();
    }
    
    @AfterEach
    void afterEach() throws IOException {
        for(Playlist p : playlistsToCleanup) {
            if(p != null) {
                PlaylistRESTCalls.deletePlaylist(p.getID());
            }
        }
        
        playlistsToCleanup.clear();
    }
    
    @Nested
    @DisplayName("createPlaylist")
    class CreatePlaylist {
        
        private Playlist p;
        
        @BeforeEach
        void beforeEach() throws IOException {
            p = cleanup(PlaylistRESTCalls.createPlaylist());
        }
        
        @Test
        @DisplayName("creates a playlist")
        void creates() throws IOException {
            final List<Playlist> playlists = PlaylistRESTCalls.getPlaylists();
            final int id = p.getID();
            
            assertEquals(1L, playlists.stream().filter(p -> p.getID() == id).collect(Collectors.counting()));
        }
        
        @Test
        @DisplayName("returns the created playlist")
        void returnsCreated() throws IOException {
            assertNotNull(p);
            assertNotNull(p.getID());
        }
        
        @Test
        @DisplayName("returns HTTP 400 (bad request) when given an empty name")
        void returnsErrorWhenNameEmpty() throws IOException {
            PlaylistRESTCalls.createPlaylist(400, "");
        }
        
        @Test
        @DisplayName("does not return an error when given a name that is the maximuim length")
        void doesNotErrorWhenNameAtMaxLength() throws IOException {
            final char[] array = new char[Playlist.NAME_MAX_LENGTH];
            final String name;
            
            Arrays.fill(array, 'a');
            name = new String(array);
            
            p = cleanup(PlaylistRESTCalls.createPlaylist(name));
            
            assertNotNull(p);
            assertEquals(name, p.getName());
        }
        
        @Test
        @DisplayName("returns HTTP 400 (bad request) when given an name that is too long")
        void returnsErrorWhenNameTooLong() throws IOException {
            final char[] name = new char[Playlist.NAME_MAX_LENGTH + 1];
            
            Arrays.fill(name, 'a');
            PlaylistRESTCalls.createPlaylist(400, new String(name));
        }
    }
    
    @Nested
    @DisplayName("getPlaylists")
    class GetPlaylists {
        
        @Test
        @DisplayName("returns all of the playlists")
        void getsPlaylists() throws IOException {
            final IDSet<Playlist> added = new IDSet<>(Arrays.asList( //
                    cleanup(PlaylistRESTCalls.createPlaylist()), //
                    cleanup(PlaylistRESTCalls.createPlaylist()), //
                    cleanup(PlaylistRESTCalls.createPlaylist())));
            final List<Playlist> pls = PlaylistRESTCalls.getPlaylists();
            
            assertEquals(3L, pls.stream().filter(p -> added.contains(p)).collect(Collectors.counting()));
        }
    }
    
    @Nested
    @DisplayName("getPlaylist")
    class GetPlaylist {
        
        private int pID;
        
        @BeforeEach
        void beforeEach() throws IOException {
            pID = cleanup(PlaylistRESTCalls.createPlaylist()).getID();
        }
        
        @Test
        @DisplayName("returns the playlist with the given ID")
        void getsPlaylist() throws IOException {
            final Playlist p = PlaylistRESTCalls.getPlaylist(pID);
            
            assertNotNull(p);
            assertEquals(pID, p.getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when no playlist exists with the given ID")
        void errorsWhenPlaylistDNE() throws IOException {
            PlaylistRESTCalls.getPlaylist(404, -1);
        }
    }
    
    @Nested
    @DisplayName("setPlaylistName")
    class SetPlaylistName {
        
        private Playlist p;
        
        @BeforeEach
        void beforeEach() throws IOException {
            p = cleanup(PlaylistRESTCalls.createPlaylist());
        }
        
        @Test
        @DisplayName("changes a playlist's name")
        void changesPlaylistName() throws IOException {
            final String newName = p.getName() + " (modified)";
            
            p = PlaylistRESTCalls.setPlaylistName(p.getID(), newName);
            assertEquals(newName, p.getName());
        }
        
        @Test
        @DisplayName("returns HTTP 405 when given an empty name")
        void errorsWhenUsingEmptyName() throws IOException {
            PlaylistRESTCalls.setPlaylistName(405, p.getID(), "");
        }
        
        @Test
        @DisplayName("returns HTTP 400 when given a name longer than the max. length")
        void errorsWhenNameTooLong() throws IOException {
            final char[] name = new char[Playlist.NAME_MAX_LENGTH + 1];
            
            Arrays.fill(name, 'a');
            PlaylistRESTCalls.setPlaylistName(400, p.getID(), new String(name));
        }
        
        @Test
        @DisplayName("can change a playlist's name to be the max. length")
        void doesNotErrorWhenNameIsMaxLength() throws IOException {
            char[] array = new char[Playlist.NAME_MAX_LENGTH];
            final String name;
            
            Arrays.fill(array, 'a');
            name = new String(array);
            
            p = PlaylistRESTCalls.setPlaylistName(p.getID(), name);
            assertEquals(name, p.getName());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when using an invalid playlist ID")
        void errorsWithInvalidPlaylistID() throws IOException {
            PlaylistRESTCalls.setPlaylistName(404, -1, p.getName() + " (modified)");
        }
    }
    
    @Nested
    @DisplayName("addLast")
    class AddLast {
        
        private Playlist p;
        private SongInfo s0;
        private SongInfo s1;
        private SongInfo s2;
        private SongInfo s3;
        private SongInfo s4;
        
        @BeforeEach
        void beforeEach() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            
            p = cleanup(PlaylistRESTCalls.createPlaylist());
            s0 = lib.getSongs().get(0);
            s1 = lib.getSongs().get(1);
            s2 = lib.getSongs().get(2);
            s3 = lib.getSongs().get(3);
            s4 = lib.getSongs().get(4);
        }
        
        @Test
        @DisplayName("can add a single song to the end of the playlist")
        void singleOnce() throws IOException {
            p = PlaylistRESTCalls.addLast(200, p.getID(), s0.getID());
            assertEquals(1, p.size());
            assertEquals(s0.getID(), p.getElement(0).getSong().getID());
        }
        
        @Test
        @DisplayName("can add multiple songs to the end of the playlist one at a time")
        void singleTwice() throws IOException {
            p = PlaylistRESTCalls.addLast(200, p.getID(), s0.getID());
            p = PlaylistRESTCalls.addLast(200, p.getID(), s1.getID());
            
            assertEquals(2, p.size());
            assertEquals(s0.getID(), p.getElement(0).getSong().getID());
            assertEquals(s1.getID(), p.getElement(1).getSong().getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when adding a single song using an invalid playlist ID")
        void singleInvalidPlaylist() throws IOException {
            PlaylistRESTCalls.addLast(404, -1, s0.getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when adding a single song using an invalid song ID")
        void singleInvalidSong() throws IOException {
            PlaylistRESTCalls.addLast(404, -1, -2);
        }
        
        @Test
        @DisplayName("can add multiple songs to the end of the playlist at once")
        void multipleOnce() throws IOException {
            p = PlaylistRESTCalls.addLast(200, p.getID(), s0.getID(), s1.getID(), s2.getID());
            
            assertEquals(3, p.size());
            assertEquals(s0.getID(), p.getElement(0).getSong().getID());
            assertEquals(s1.getID(), p.getElement(1).getSong().getID());
            assertEquals(s2.getID(), p.getElement(2).getSong().getID());
        }
        
        @Test
        @DisplayName("can add multiple songs to the end of the playlist at once... multiple times")
        void multipleTwice() throws IOException {
            p = PlaylistRESTCalls.addLast(200, p.getID(), s0.getID(), s1.getID(), s2.getID());
            p = PlaylistRESTCalls.addLast(200, p.getID(), s3.getID(), s4.getID());
            
            assertEquals(5, p.size());
            assertEquals(s0.getID(), p.getElement(0).getSong().getID());
            assertEquals(s1.getID(), p.getElement(1).getSong().getID());
            assertEquals(s2.getID(), p.getElement(2).getSong().getID());
            assertEquals(s3.getID(), p.getElement(3).getSong().getID());
            assertEquals(s4.getID(), p.getElement(4).getSong().getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when adding multiple songs using an invalid song ID")
        void multipleOneInvalidSong() throws IOException {
            PlaylistRESTCalls.addLast(404, p.getID(), s0.getID(), -1, s2.getID());
        }
    }
    
    @Nested
    @DisplayName("clearPlaylist")
    class ClearPlaylist {
        
        private Playlist p;
        
        @BeforeEach
        void beforeEach() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            
            p = cleanup(PlaylistRESTCalls.createPlaylist());
            p = PlaylistRESTCalls.addLast(200, p.getID(), //
                    lib.getSongs().get(0).getID(), //
                    lib.getSongs().get(1).getID(), //
                    lib.getSongs().get(2).getID());
        }
        
        @Test
        @DisplayName("clears the playlist")
        void clears() throws IOException {
            p = PlaylistRESTCalls.clearPlaylist(p.getID());
            assertEquals(0, p.size());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when clearing a playlist using an invalid ID")
        void returnsErrorWhenInvalidID() throws IOException {
            PlaylistRESTCalls.clearPlaylist(404, -1);
        }
    }
    
    @Nested
    @DisplayName("removeElement")
    class RemoveElement {
        
        private Playlist p;
        private PlaylistElement e0;
        private PlaylistElement e1;
        private PlaylistElement e2;
        
        @BeforeEach
        void beforeEach() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            
            p = cleanup(PlaylistRESTCalls.createPlaylist());
            p = PlaylistRESTCalls.addLast(200, p.getID(), //
                    lib.getSongs().get(0).getID(), //
                    lib.getSongs().get(1).getID(), //
                    lib.getSongs().get(2).getID());
            
            e0 = p.getElement(0);
            e1 = p.getElement(1);
            e2 = p.getElement(2);
        }
        
        @Test
        @DisplayName("removes an element from the playlist")
        void removesElement() throws IOException {
            p = PlaylistRESTCalls.removeElement(p.getID(), e0.getID());
            
            assertEquals(2, p.size());
            assertEquals(e1.getID(), p.getElement(0).getID());
            assertEquals(e2.getID(), p.getElement(1).getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when using an invalid playlist ID")
        void errorsWhenUsingInvalidPlaylistID() throws IOException {
            PlaylistRESTCalls.removeElement(404, -1, e0.getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when using an invalid playlist element ID")
        void errorsWhenUsingInvalidPlaylistElementID() throws IOException {
            PlaylistRESTCalls.removeElement(404, p.getID(), -1);
        }
    }
    
    @Nested
    @DisplayName("deletePlaylist")
    class DeletePlaylist {
        
        @Test
        @DisplayName("deletes the playlist")
        void deletesPlaylist() throws IOException {
            final int id = PlaylistRESTCalls.createPlaylist().getID();
            final List<Playlist> playlists;
            
            PlaylistRESTCalls.deletePlaylist(id);
            playlists = PlaylistRESTCalls.getPlaylists();
            
            assertEquals(0L, playlists.stream().filter(p -> p.getID() == id).collect(Collectors.counting()));
        }
        
        @Test
        @DisplayName("can delete a playlist that has songs")
        void deletesPlaylistWithSongs() throws IOException {
            final Library lib = LibraryRESTCalls.getLibrary();
            Playlist p = PlaylistRESTCalls.createPlaylist();
            
            p = PlaylistRESTCalls.addLast(200, p.getID(), //
                    lib.getSongs().get(0).getID(), //
                    lib.getSongs().get(1).getID(), //
                    lib.getSongs().get(2).getID());
            
            PlaylistRESTCalls.deletePlaylist(204, p.getID());
        }
        
        @Test
        @DisplayName("returns HTTP 404 when using an invalid playlist ID")
        void errorsWhenUsingInvalidPlaylistID() throws IOException {
            PlaylistRESTCalls.deletePlaylist(404, -1);
        }
    }
    
    private Playlist cleanup(Playlist p) {
        playlistsToCleanup.add(p);
        return p;
    }
}
