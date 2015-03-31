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

package em.utils;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;

import em.model.QueueElement;
import em.model.SongInfo;

/**
 * A class containing JUnit tests for {@link LibraryUtils}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtilsTest {
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will correctly convert a music directory string
     * containing no keywords to a file.
     */
    @Test
    public void testConvertToFile_NoKeywords() throws URISyntaxException {
        final String sep = File.separator;
        assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will correctly convert a music directory string
     * containing the {@code $home} keyword to a file.
     */
    @Test
    public void testConvertToFile_HomeKeyword() throws URISyntaxException {
        final String home = System.getProperty("user.home");
        final String sep = File.separator;
        
        assertNull(LibraryUtils.convertToFile(""));
        assertNull(LibraryUtils.convertToFile(null));
        assertEquals(home + sep + "docs", LibraryUtils.convertToFile("$home" + sep + "docs").getPath());
        assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will correctly convert a music directory string
     * containing a {@code $timestamp} keyword to a file.
     */
    @Test
    public void testConvertToFile_TimestampKeyword() throws ParseException, URISyntaxException {
        final String sep = File.separator;
        final String prefix = sep + "foo" + sep;
        final String suffix = "bar";
        final File f = LibraryUtils.convertToFile(prefix + "$timestamp" + suffix);
        final String path = f.getPath();
        
        assertThat(path, new ContainsRecentTimestamp(prefix.length(), path.length() - suffix.length()));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will return {@code null} when given an empty
     * string.
     */
    @Test
    public void testConvertToFile_Empty() throws URISyntaxException {
        assertNull(LibraryUtils.convertToFile(""));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will return {@code null} when given {@code null}.
     */
    @Test
    public void testConvertToFile_Null() throws URISyntaxException {
        assertNull(LibraryUtils.convertToFile(null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processHomeKeyword(String) will return {@code null} when given
     * {@code null}.
     */
    @Test
    public void testProcessHomeKeyword_Null() {
        assertNull(LibraryUtils.processHomeKeyword(null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processHomeKeyword(String) will return an empty string when given an
     * empty string.
     */
    @Test
    public void testProcessHomeKeyword_Empty() {
        assertEquals("", LibraryUtils.processHomeKeyword(""));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processHomeKeyword(String) will return the given string when given one
     * without the home keyword.
     */
    @Test
    public void testProcessHomeKeyword_NoKeyword() {
        assertEquals("/foo/bar", LibraryUtils.processHomeKeyword("/foo/bar"));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processHomeKeyword(String) will return the given string when given one
     * where the home keyword is not at the beginning of the string.
     */
    @Test
    public void testProcessHomeKeyword_KeywordNotFirst() {
        assertEquals("/foo/bar/$home", LibraryUtils.processHomeKeyword("/foo/bar/$home"));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processHomeKeyword(String) will return the given string with the home
     * keyword replaced with the user's home directory.
     */
    @Test
    public void testProcessHomeKeyword_WithKeyword() {
        final String home = System.getProperty("user.home");
        assertEquals(home + "/foo/bar", LibraryUtils.processHomeKeyword("$home/foo/bar"));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return {@code null} when given
     * {@code null}.
     */
    @Test
    public void testProcessTimestampKeyword_Null() {
        assertNull(LibraryUtils.processTimestampKeyword(null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return an empty string when given
     * an empty string.
     */
    @Test
    public void testProcessTimestampKeyword_Empty() {
        assertEquals("", LibraryUtils.processTimestampKeyword(""));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return the given string when given
     * one without the time stamp keyword.
     */
    @Test
    public void testProcessTimestampKeyword_NoKeyword() {
        assertEquals("/foo/bar", LibraryUtils.processTimestampKeyword("/foo/bar"));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return the given string with the
     * time stamp keyword replaced with a time stamp.
     */
    @Test
    public void testProcessTimestampKeyword_WithKeyword_First() {
        final String suffix = "/foo/bar";
        final String processed = LibraryUtils.processTimestampKeyword("$timestamp" + suffix);
        
        assertThat(processed, new ContainsRecentTimestamp(0, processed.length() - suffix.length()));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return the given string with the
     * time stamp keyword replaced with a time stamp.
     */
    @Test
    public void testProcessTimestampKeyword_WithKeyword_Middle() {
        final String prefix = "/foo/";
        final String suffix = "/bar";
        final String processed = LibraryUtils.processTimestampKeyword(prefix + "$timestamp" + suffix);
        
        assertThat(processed, new ContainsRecentTimestamp(prefix.length(), processed.length() - suffix.length()));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#processTimestampKeyword(String) will return the given string with the
     * time stamp keyword replaced with a time stamp.
     */
    @Test
    public void testProcessTimestampKeyword_WithKeyword_Last() {
        final String prefix = "/foo/bar";
        final String processed = LibraryUtils.processTimestampKeyword(prefix + "$timestamp");
        
        assertThat(processed, new ContainsRecentTimestamp(prefix.length(), processed.length()));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#processClasspathKeyword(String)} will return {@code null} when given
     * {@code null}.
     */
    @Test
    public void testConvertToClasspathFile_Null() throws URISyntaxException {
        assertNull(LibraryUtils.processClasspathKeyword(null, LibraryUtils.class.getClassLoader()));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#processClasspathKeyword(String)} will return {@code null} when given
     * an empty string.
     */
    @Test
    public void testConvertToClasspathFile_Empty() throws URISyntaxException {
        assertNull(LibraryUtils.processClasspathKeyword("", LibraryUtils.class.getClassLoader()));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#processClasspathKeyword(String)} will return {@code null} when given
     * a string that does not contain the classpath keyword.
     */
    @Test
    public void testConvertToClasspathFile_NoKeyword() throws URISyntaxException {
        assertNull(LibraryUtils.processClasspathKeyword("/foo/bar", LibraryUtils.class.getClassLoader()));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#processClasspathKeyword(String)} will return {@code null} when given
     * a string that does not start with the classpath keyword.
     */
    @Test
    public void testConvertToClasspathFile_KeywordNotFirst() throws URISyntaxException {
        assertNull(LibraryUtils.processClasspathKeyword("/foo/$classpath/bar", LibraryUtils.class.getClassLoader()));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#processClasspathKeyword(String)} will a file whose path is the given
     * string without the classpath keyword.
     */
    @Test
    public void testConvertToClasspathFile_WithKeyword() throws URISyntaxException, MalformedURLException {
        final ClassLoader loaderMock = createMock(ClassLoader.class);
        File f = new File("");
        
        expect(loaderMock.getResource("/foo/bar")).andReturn(f.toURI().toURL());
        replay(loaderMock);
        
        assertEquals(f.getAbsolutePath(), LibraryUtils.processClasspathKeyword("$classpath/foo/bar", loaderMock)
                .getAbsolutePath());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} will remove a the file information from a
     * {@link SongInfo}.
     */
    @Test
    public void testSanitizeForClient_SongInfo() {
        final SongInfo a = new SongInfo(3);
        
        a.setFile(new File("foo.bar"));
        assertEquals(a, LibraryUtils.sanitizeForClient(a));
        assertNull(a.getFile());
        assertNotNull(a.getID());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} can handle a {@link SongInfo} whose file is
     * already {@code null} without throwing exceptions.
     */
    @Test
    public void testSanitizeForClient_SongInfo_NullFile() {
        final SongInfo b = new SongInfo(7);
        
        b.setFile(null);
        assertEquals(b, LibraryUtils.sanitizeForClient(b));
        assertNull(b.getFile());
        assertNotNull(b.getID());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} can handle a {@code null} {@link SongInfo}
     * without throwing exceptions.
     */
    @Test
    public void testSanitizeForClient_SongInfo_Null() {
        assertNull(LibraryUtils.sanitizeForClient((SongInfo)null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeSongsForClient(Collection)} will correctly remove the file from
     * each {@link SongInfo} contained within the collection.
     */
    @Test
    public void testSanitizeSongsForClient() {
        final ArrayList<SongInfo> infos = new ArrayList<>();
        final SongInfo a = new SongInfo(3);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = null;
        
        a.setFile(new File("foo.bar"));
        b.setFile(null);
        
        infos.add(a);
        infos.add(b);
        infos.add(c);
        
        assertEquals(infos, LibraryUtils.sanitizeSongsForClient(infos));
        
        assertNull(infos.get(0).getFile());
        assertNotNull(infos.get(0).getID());
        
        assertNull(infos.get(1).getFile());
        assertNotNull(infos.get(1).getID());
        
        assertNull(infos.get(2));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeSongsForClient(Collection)} will gracefully handle an empty
     * collection.
     */
    @Test
    public void testSanitizeSongsForClient_Empty() {
        final List<SongInfo> infos = new ArrayList<SongInfo>();
        assertEquals(infos, LibraryUtils.sanitizeSongsForClient(infos));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeSongsForClient(Collection)} will gracefully handle a
     * {@code null} collection.
     */
    @Test
    public void testSanitizeSongsForClient_Null() {
        assertNull(LibraryUtils.sanitizeSongsForClient(null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeElementsForClient(Collection)} will correctly remove the file
     * from each {@link QueueElement} contained within the collection.
     */
    @Test
    public void testSanitizeElementsForClient() {
        final ArrayList<QueueElement> elements = new ArrayList<>();
        final QueueElement a = new QueueElement(3, new SongInfo(5));
        final QueueElement b = new QueueElement(7, new SongInfo(11));
        final QueueElement c = new QueueElement(7, null);
        final QueueElement d = null;
        
        a.getSong().setFile(new File("foo.bar"));
        b.getSong().setFile(null);
        
        elements.add(a);
        elements.add(b);
        elements.add(c);
        elements.add(d);
        
        assertEquals(elements, LibraryUtils.sanitizeElementsForClient(elements));
        
        assertNull(elements.get(0).getSong().getFile());
        assertNotNull(elements.get(0).getID());
        
        assertNull(elements.get(1).getSong().getFile());
        assertNotNull(elements.get(1).getID());
        
        assertNull(elements.get(2).getSong());
        assertNotNull(elements.get(2).getID());
        
        assertNull(elements.get(3));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeElementsForClient(Collection)} will gracefully handle an
     * empty collection.
     */
    @Test
    public void testSanitizeElementsForClient_Empty() {
        final List<QueueElement> infos = new ArrayList<QueueElement>();
        assertEquals(infos, LibraryUtils.sanitizeElementsForClient(infos));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeElementsForClient(Collection)} will gracefully handle a
     * {@code null} collection.
     */
    @Test
    public void testSanitizeElementsForClient_Null() {
        assertNull(LibraryUtils.sanitizeElementsForClient(null));
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
