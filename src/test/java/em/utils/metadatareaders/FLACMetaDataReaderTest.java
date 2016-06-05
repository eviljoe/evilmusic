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

package em.utils.metadatareaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
public class FLACMetaDataReaderTest {
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#canReadMetaData(File)} function will correctly determine if a
     * given file is a FLAC file whose meta data can be read.
     */
    @Test
    public void testCanReadMetaData() {
        final FLACMetaDataReader reader = new FLACMetaDataReader();
        
        assertFalse(reader.canReadMetaData(new File("foo.bar")));
        assertFalse(reader.canReadMetaData(new File("flac")));
        
        assertTrue(reader.canReadMetaData(new File("foo.flac")));
        assertTrue(reader.canReadMetaData(new File("foo.FLAC")));
        assertTrue(reader.canReadMetaData(new File("foo.fLAc")));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseField(String, String)} function can parse comment lines
     * correctly.
     */
    @Test
    public void testParseField() {
        assertEquals("10", FLACMetaDataReader.parseField("    sample_rate : 10", "sample_rate"));
        assertEquals("10", FLACMetaDataReader.parseField("    sample_rate: 10", "sample_rate"));
        assertEquals("10", FLACMetaDataReader.parseField("sample_rate : 10", "sample_rate"));
        assertEquals("10", FLACMetaDataReader.parseField("sample_rate : 10", "sample_rate"));
        assertEquals("10", FLACMetaDataReader.parseField("sample_rate : 10", "sample_rate"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseField(String, String)} function can correctly ignore
     * non-comment lines.
     */
    @Test
    public void testParseField_invalid() {
        assertNull(FLACMetaDataReader.parseField("  hello : world", "sample_rate"));
        assertNull(FLACMetaDataReader.parseField("hello : world", "sample_rate"));
        assertNull(FLACMetaDataReader.parseField("hello: world", "sample_rate"));
        assertNull(FLACMetaDataReader.parseField("hello:world", "sample_rate"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseField(String, String)} function can correctly ignore
     * empty lines.
     */
    @Test
    public void testParseField_empty() {
        assertNull(FLACMetaDataReader.parseField("", "sample_rate"));
        
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseField(String, String)} function can correctly ignore
     * {@code null} lines.
     */
    @Test
    public void testParseField_null() {
        assertNull(FLACMetaDataReader.parseField(null, "sample_rate"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseComment(String, String)} function can parse comment lines
     * correctly.
     */
    @Test
    public void testParseComment() {
        assertEquals("world", FLACMetaDataReader.parseComment("    comment[1]: ARTIST=world", "artist"));
        assertEquals("world", FLACMetaDataReader.parseComment("    comment[999]: ARTIST=world", "artist"));
        assertEquals("world", FLACMetaDataReader.parseComment("comment[456] : ARTIST = world", "artist"));
        assertEquals("world", FLACMetaDataReader.parseComment("comment[] : ARTIST = world", "artist"));
        assertEquals("world", FLACMetaDataReader.parseComment("comment[] : artist = world", "artist"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseComment(String, String)} function can correctly ignore
     * non-comment lines.
     */
    @Test
    public void testParseComment_invalid() {
        assertNull(FLACMetaDataReader.parseComment("hello=world", "artist"));
        assertNull(FLACMetaDataReader.parseComment("asdf: hello=world", "artist"));
        assertNull(FLACMetaDataReader.parseComment("comment: hello=world", "artist"));
        assertNull(FLACMetaDataReader.parseComment("comment: hello = world", "artist"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseComment(String, String)} function can correctly ignore
     * empty lines.
     */
    @Test
    public void testParseComment_empty() {
        assertNull(FLACMetaDataReader.parseComment("", "artist"));
        
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseComment(String, String)} function can correctly ignore
     * {@code null} lines.
     */
    @Test
    public void testParseComment_null() {
        assertNull(FLACMetaDataReader.parseComment(null, "artist"));
    }
    
    /**
     * Tests to ensure that the {@link FLACMetaDataReader#parseMetaData(InputStream)} function correctly parses metadata
     * output.
     */
    @Test
    public void testParseMetaData() throws IOException {
        final int trackNum = 1;
        final String artist = "Conditions";
        final String album = "Full Of War";
        final String title = "Walking Separate Ways";
        final String genre = "Rock";
        final int year = 2013;
        final int sampleRate = 44100;
        final int sampleCount = 8595972;
        final InputStream in =
                new ByteArrayInputStream(createFLACMetaDataString(trackNum, artist, album, title, genre, year,
                        sampleRate, sampleCount).getBytes());
        final SongInfo info = FLACMetaDataReader.parseMetaData(in);
        double seconds = (double)sampleCount / (double)sampleRate;
        
        assertEquals(trackNum, info.getTrackNumber());
        assertEquals(artist, info.getArtist());
        assertEquals(album, info.getAlbum());
        assertEquals(title, info.getTitle());
        assertEquals(genre, info.getGenre());
        assertEquals(year, info.getYear());
        assertEquals(sampleRate, info.getSampleRate());
        assertEquals(sampleCount, info.getSampleCount());
        assertEquals((int)Math.ceil(seconds * 1000.0), info.getMillis());
    }
    
    /**
     * Tests to ensure the {@link FLACMetaDataReader#getMetaFLACCommand()} will return the default metaflac command when
     * the global metaflac command preference is {@code null}.
     */
    @Test
    public void testGetMetaFLACCommand_NullPref() {
        assertEquals(FLACMetaDataReader.DEFAULT_METAFLAC_COMMAND, new FLACMetaDataReader().getMetaFLACCommand(null));
    }
    
    /**
     * Tests to ensure the {@link FLACMetaDataReader#getMetaFLACCommand()} will return the default metaflac command when
     * the global metaflac command preference is empty.
     */
    @Test
    public void testGetMetaFLACCommand_EmptyPref() {
        assertEquals(FLACMetaDataReader.DEFAULT_METAFLAC_COMMAND, new FLACMetaDataReader().getMetaFLACCommand(""));
    }
    
    /**
     * Tests to ensure the {@link FLACMetaDataReader#getMetaFLACCommand()} will return the default metaflac command when
     * the global metaflac command preference contains only whitespace.
     */
    @Test
    public void testGetMetaFLACCommand_WhiteSpaceOnlyPref() {
        assertEquals(FLACMetaDataReader.DEFAULT_METAFLAC_COMMAND, new FLACMetaDataReader().getMetaFLACCommand("  \t "));
    }
    
    /**
     * Tests to ensure the {@link FLACMetaDataReader#getMetaFLACCommand()} will return the global metaflac command
     * preference when it is set.
     */
    @Test
    public void testGetMetaFLACCommand_ValidPref() {
        final String cmd = "/foo/bar ";
        assertEquals(cmd, new FLACMetaDataReader().getMetaFLACCommand(cmd));
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    private String createFLACMetaDataString(int trackNum, String artist, String album, String title, String genre,
            int year, int sampleRate, int sampleCount) {
        
        final StringBuilder s = new StringBuilder();
        
        appendLine(s, "METADATA block #0");
        appendLine(s, "  type: 0 (STREAMINFO)");
        appendLine(s, "  is last: false");
        appendLine(s, "  length: 34");
        appendLine(s, "  minimum blocksize: 4096 samples");
        appendLine(s, "  maximum blocksize: 4096 samples");
        appendLine(s, "  minimum framesize: 3590 bytes");
        appendLine(s, "  maximum framesize: 14083 bytes");
        appendLine(s, "  sample_rate: " + sampleRate + " Hz");
        appendLine(s, "  channels: 2");
        appendLine(s, "  bits-per-sample: 16");
        appendLine(s, "  total samples: " + sampleCount);
        appendLine(s, "  MD5 signature: d93c157810d3a7b84b2404d66f2540de");
        appendLine(s, "METADATA block #1");
        appendLine(s, "  type: 3 (SEEKTABLE)");
        appendLine(s, "  is last: false");
        appendLine(s, "  length: 360");
        appendLine(s, "  seek points: 20");
        appendLine(s, "    point 0: sample_number=0, stream_offset=0, frame_samples=4096");
        appendLine(s, "    point 1: sample_number=438272, stream_offset=706296, frame_samples=4096");
        appendLine(s, "    point 2: sample_number=880640, stream_offset=1817389, frame_samples=4096");
        appendLine(s, "    point 3: sample_number=1318912, stream_offset=3136286, frame_samples=4096");
        appendLine(s, "    point 4: sample_number=1761280, stream_offset=4540382, frame_samples=4096");
        appendLine(s, "    point 5: sample_number=2203648, stream_offset=5930625, frame_samples=4096");
        appendLine(s, "    point 6: sample_number=2641920, stream_offset=7315220, frame_samples=4096");
        appendLine(s, "    point 7: sample_number=3084288, stream_offset=8697975, frame_samples=4096");
        appendLine(s, "    point 8: sample_number=3526656, stream_offset=9964017, frame_samples=4096");
        appendLine(s, "    point 9: sample_number=3964928, stream_offset=11326915, frame_samples=4096");
        appendLine(s, "    point 10: sample_number=4407296, stream_offset=12717925, frame_samples=4096");
        appendLine(s, "    point 11: sample_number=4849664, stream_offset=14110960, frame_samples=4096");
        appendLine(s, "    point 12: sample_number=5287936, stream_offset=15489320, frame_samples=4096");
        appendLine(s, "    point 13: sample_number=5730304, stream_offset=16861716, frame_samples=4096");
        appendLine(s, "    point 14: sample_number=6172672, stream_offset=18185869, frame_samples=4096");
        appendLine(s, "    point 15: sample_number=6610944, stream_offset=19530671, frame_samples=4096");
        appendLine(s, "    point 16: sample_number=7053312, stream_offset=20923601, frame_samples=4096");
        appendLine(s, "    point 17: sample_number=7495680, stream_offset=22308625, frame_samples=4096");
        appendLine(s, "    point 18: sample_number=7933952, stream_offset=23683527, frame_samples=4096");
        appendLine(s, "    point 19: sample_number=8376320, stream_offset=25054968, frame_samples=4096");
        appendLine(s, "METADATA block #2");
        appendLine(s, "  type: 4 (VORBIS_COMMENT)");
        appendLine(s, "  is last: false");
        appendLine(s, "  length: 157");
        appendLine(s, "  vendor string: reference libFLAC 1.3.0 20130526");
        appendLine(s, "  comments: 6");
        appendLine(s, "    comment[0]: TRACKNUMBER=" + trackNum);
        appendLine(s, "    comment[1]: ARTIST=" + artist);
        appendLine(s, "    comment[2]: ALBUM=" + album);
        appendLine(s, "    comment[3]: TITLE=" + title);
        appendLine(s, "    comment[4]: GENRE=" + genre);
        appendLine(s, "    comment[5]: DATE=" + year);
        appendLine(s, "METADATA block #3");
        appendLine(s, "  type: 1 (PADDING)");
        appendLine(s, "  is last: true");
        appendLine(s, "  length: 8192");
        
        return s.toString();
    }
    
    private void appendLine(StringBuilder s, String line) {
        s.append(line).append('\n');
    }
}
