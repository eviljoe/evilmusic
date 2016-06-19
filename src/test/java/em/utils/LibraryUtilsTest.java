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

package em.utils;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;

import em.model.SongInfo;

/**
 * A class containing JUnit tests for {@link LibraryUtils}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtilsTest {
    
    @Nested
    @DisplayName("convertToFile")
    class ConvertToFile {
        
        @Test
        @DisplayName("will correctly convert a music directory string containing no keywords to a file")
        void noKeywords() throws URISyntaxException {
            final String sep = File.separator;
            assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
        }
        
        @Test
        @DisplayName("will correctly convert a music directory string containing the $home keyword to a file")
        void homeKeyword() throws URISyntaxException {
            final String home = System.getProperty("user.home");
            final String sep = File.separator;
            
            assertNull(LibraryUtils.convertToFile(""));
            assertNull(LibraryUtils.convertToFile(null));
            assertEquals(home + sep + "docs", LibraryUtils.convertToFile("$home" + sep + "docs").getPath());
            assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
        }
        
        @Test
        @DisplayName("will correctly convert a music directory string containing a $timestamp keyword to a file")
        void timestampKeyword() throws ParseException, URISyntaxException {
            final String sep = File.separator;
            final String prefix = sep + "foo" + sep;
            final String suffix = "bar";
            final File f = LibraryUtils.convertToFile(prefix + "$timestamp" + suffix);
            final String path = f.getPath();
            
            assertThat(path, new ContainsRecentTimestamp(prefix.length(), path.length() - suffix.length()));
        }
        
        @Test
        @DisplayName("will return null when given an empty string")
        void empty() throws URISyntaxException {
            assertNull(LibraryUtils.convertToFile(""));
        }
        
        @Test
        @DisplayName("will return {@code null} when given null")
        void convertsNull() throws URISyntaxException {
            assertNull(LibraryUtils.convertToFile(null));
        }
    }
    
    @Nested
    @DisplayName("processHomeKeyword")
    class ProcessHomeKeyword {
        
        @Test
        @DisplayName("will return null when given null")
        void processNull() {
            assertNull(LibraryUtils.processHomeKeyword(null));
        }
        
        @Test
        @DisplayName("will return an empty string when given an empty string")
        void empty() {
            assertEquals("", LibraryUtils.processHomeKeyword(""));
        }
        
        @Test
        @DisplayName("will return the given string when given one without the home keyword")
        void noKeyword() {
            assertEquals("/foo/bar", LibraryUtils.processHomeKeyword("/foo/bar"));
        }
        
        @Test
        @DisplayName("will return the given string when given one where the home keyword is not at the beginning of the string")
        void keywordNotFirst() {
            assertEquals("/foo/bar/$home", LibraryUtils.processHomeKeyword("/foo/bar/$home"));
        }
        
        @Test
        @DisplayName("will return the given string with the home keyword replaced with the user's home directory")
        void withKeyword() {
            final String home = System.getProperty("user.home");
            assertEquals(home + "/foo/bar", LibraryUtils.processHomeKeyword("$home/foo/bar"));
        }
    }
    
    @Nested
    @DisplayName("processTimestampKeyword")
    class ProcessTimestampKeyword {
        
        @Test
        @DisplayName("will return null when given null")
        void processNull() {
            assertNull(LibraryUtils.processTimestampKeyword(null));
        }
        
        @Test
        @DisplayName("will return an empty string when given an empty string")
        void empty() {
            assertEquals("", LibraryUtils.processTimestampKeyword(""));
        }
        
        @Test
        @DisplayName("will return the given string when given one without the time stamp keyword")
        void noKeyword() {
            assertEquals("/foo/bar", LibraryUtils.processTimestampKeyword("/foo/bar"));
        }
        
        @Test
        @DisplayName("will return the given string with the time stamp keyword replaced with a time stamp")
        void withKeywordFirst() {
            final String suffix = "/foo/bar";
            final String processed = LibraryUtils.processTimestampKeyword("$timestamp" + suffix);
            
            assertThat(processed, new ContainsRecentTimestamp(0, processed.length() - suffix.length()));
        }
        
        @Test
        @DisplayName("will return the given string with the time stamp keyword replaced with a time stamp")
        void withKeywordMiddle() {
            final String prefix = "/foo/";
            final String suffix = "/bar";
            final String processed = LibraryUtils.processTimestampKeyword(prefix + "$timestamp" + suffix);
            
            assertThat(processed, new ContainsRecentTimestamp(prefix.length(), processed.length() - suffix.length()));
        }
        
        @Test
        @DisplayName("will return the given string with the time stamp keyword replaced with a time stamp")
        void withKeywordLast() {
            final String prefix = "/foo/bar";
            final String processed = LibraryUtils.processTimestampKeyword(prefix + "$timestamp");
            
            assertThat(processed, new ContainsRecentTimestamp(prefix.length(), processed.length()));
        }
    }
    
    @Nested
    @DisplayName("convertToClasspathFile")
    class ConvertToClasspathFile {
        
        @Test
        @DisplayName("will return null when given null")
        void processNull() throws URISyntaxException {
            assertNull(LibraryUtils.processClasspathKeyword(null, LibraryUtils.class.getClassLoader()));
        }
        
        @Test
        @DisplayName("will return null when given an empty string")
        void empty() throws URISyntaxException {
            assertNull(LibraryUtils.processClasspathKeyword("", LibraryUtils.class.getClassLoader()));
        }
        
        @Test
        @DisplayName("will return null when given a string that does not contain the classpath keyword")
        void noKeyword() throws URISyntaxException {
            assertNull(LibraryUtils.processClasspathKeyword("/foo/bar", LibraryUtils.class.getClassLoader()));
        }
        
        @Test
        @DisplayName("will return null when given a string that does not start with the classpath keyword")
        void keywordNotFirst() throws URISyntaxException {
            assertNull(LibraryUtils.processClasspathKeyword("/foo/$classpath/bar", LibraryUtils.class.getClassLoader()));
        }
        
        @Test
        @DisplayName("will return the file's absolute path when given a string starting with the classpath keyword")
        void withKeyword() throws URISyntaxException, MalformedURLException {
            final ClassLoader loaderMock = createMock(ClassLoader.class);
            File f = new File("");
            
            expect(loaderMock.getResource("/foo/bar")).andReturn(f.toURI().toURL());
            replay(loaderMock);
            
            assertEquals(f.getAbsolutePath(), LibraryUtils.processClasspathKeyword("$classpath/foo/bar", loaderMock)
                    .getAbsolutePath());
        }
    }
    
    @Nested
    @DisplayName("sortSongs")
    class SortSongs {
        
        private Collection<Integer> ids;
        private Collection<SongInfo> songs;
        private SongInfo s0;
        private SongInfo s1;
        private SongInfo s2;
        
        @BeforeEach
        void beforeEach() {
            ids = Arrays.asList(0, 1, 2);
            
            s0 = new SongInfo(0);
            s1 = new SongInfo(1);
            s2 = new SongInfo(2);
            songs = Arrays.asList(s2, s1, s0);
        }
        
        @Test
        @DisplayName("returns an empty list when the ordered song IDs are null/empty")
        void emptySongIDs() {
            assertEquals(0, LibraryUtils.sortSongs(null, songs).size());
            assertEquals(0, LibraryUtils.sortSongs(new ArrayList<>(0), songs).size());
        }
        
        @Test
        @DisplayName("returns an empty list when the songs null/empty")
        void emptySongs() {
            assertEquals(0, LibraryUtils.sortSongs(ids, null).size());
            assertEquals(0, LibraryUtils.sortSongs(ids, new ArrayList<>(0)).size());
        }
        
        @Test
        @DisplayName("returns a list of songs ordered according to the given IDs")
        void ordersSongs() {
            final List<SongInfo> sorted = LibraryUtils.sortSongs(ids, songs);
            
            assertEquals(0, sorted.get(0).getID());
            assertEquals(1, sorted.get(1).getID());
            assertEquals(2, sorted.get(2).getID());
        }
        
        @Test
        @DisplayName("can order when there are more songs than IDs given")
        void orderWithMoreSongsThanIDs() {
            final List<SongInfo> sorted;
            
            ids = Arrays.asList(0, 1);
            sorted = LibraryUtils.sortSongs(ids, songs);
            
            assertEquals(0, sorted.get(0).getID());
            assertEquals(1, sorted.get(1).getID());
        }
        
        @DisplayName("can order when there are more IDs than songs given")
        void orderWithMoreIDsThanSongs() {
            final List<SongInfo> sorted;
            
            songs = Arrays.asList(s0, s1);
            sorted = LibraryUtils.sortSongs(ids, songs);
            
            assertEquals(0, sorted.get(0).getID());
            assertEquals(1, sorted.get(1).getID());
        }
        
        @DisplayName("can order when the same ID is given more than once")
        void orderWithSameIDGivenMoreThanOnce() {
            final List<SongInfo> sorted;
            
            ids = Arrays.asList(1, 1, 2);
            sorted = LibraryUtils.sortSongs(ids, songs);
            
            assertEquals(1, sorted.get(0).getID());
            assertEquals(1, sorted.get(1).getID());
            assertEquals(2, sorted.get(2).getID());
        }
    }
    
    /**
     * @since v0.1
     * @author eviljoe
     */
    private static class ContainsRecentTimestamp extends BaseMatcher<Object> {
        
        /**
         * Make sure the time stamp is within two seconds of the current time. This is being pretty generous, but I am
         * trying to avoid false failures.
         */
        private static final long DELTA = 2000;
        private static final SimpleDateFormat FORMAT = new SimpleDateFormat(LibraryUtils.TIMESTAMP_FORMAT);
        
        private final int timestampStartIndex;
        private final int timestampEndIndex;
        private final Date now;
        
        public ContainsRecentTimestamp(int start, int end) {
            super();
            this.timestampStartIndex = start;
            this.timestampEndIndex = end;
            now = new Date();
        }
        
        @Override
        public boolean matches(Object item) {
            final String str = item == null ? null : item.toString();
            boolean valid = false;
            
            if(str != null) {
                final String timestampStr = str.toString().substring(timestampStartIndex, timestampEndIndex);
                final Date timestamp;
                
                try {
                    timestamp = FORMAT.parse(timestampStr);
                    valid = Math.abs((now.getTime() - timestamp.getTime())) < DELTA;
                } catch(ParseException e) {
                    valid = false;
                }
            }
            
            return valid;
        }
        
        @Override
        public void describeTo(Description desc) {
            desc.appendText("A string containing a timestamp within ");
            desc.appendValue(DELTA);
            desc.appendText(" ms of ");
            desc.appendText(FORMAT.format(now));
        }
        
        @Override
        public void describeMismatch(Object item, Description desc) {
            desc.appendValue(item);
            desc.appendText(" does not contain timestamp within ");
            desc.appendValue(DELTA);
            desc.appendText(" ms of ");
            desc.appendText(FORMAT.format(now));
        }
    }
}
