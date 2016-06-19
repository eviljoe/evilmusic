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
package em.controllers;

import static org.junit.gen5.api.Assertions.assertThrows;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import em.dao.playlist.InvalidPlaylistNameException;
import em.model.Playlist;

/**
 * @since v0.1
 * @author eviljoe
 */
public class PlaylistControllerTest {
    
    private PlaylistController controller;
    
    @BeforeEach
    void beforeEach() {
        controller = new PlaylistController();
    }
    
    @Nested
    @DisplayName("validatePlaylistName")
    class ValidatePlaylistName {
        
        @Test
        @DisplayName("throws exception when given null")
        void throwsExceptionGivenNull() {
            assertThrows(InvalidPlaylistNameException.class, () -> controller.validatePlaylistName(null));
        }
        
        @Test
        @DisplayName("throws exception when given empty string")
        void throwsExceptionGivenEmtpy() {
            assertThrows(InvalidPlaylistNameException.class, () -> controller.validatePlaylistName(""));
        }
        
        @Test
        @DisplayName("throws exception when given string longer than max length")
        void throwsExceptionGivenTooLong() {
            final char[] name = new char[Playlist.NAME_MAX_LENGTH + 1];
            assertThrows(InvalidPlaylistNameException.class, () -> controller.validatePlaylistName(new String(name)));
        }
        
        @Test
        @DisplayName("does not throw exception when given a max. length string")
        void doesNotThrowExceptionGivenMaxLengthString() {
            final char[] name = new char[Playlist.NAME_MAX_LENGTH];
            controller.validatePlaylistName(new String(name));
        }
        
        @Test
        @DisplayName("does not throw exception when given a string whose length is between zero and max. length")
        void doesNotThrowExceptionGivenBetweenEmptyAndMaxLength() {
            controller.validatePlaylistName("asdf");
        }
    }
}
