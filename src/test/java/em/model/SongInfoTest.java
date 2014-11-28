package em.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * A class containing unit tests for {@link SongInfo}.
 * 
 * @see SongInfo
 * 
 * @since v0.1
 * @author eviljoe
 */
public class SongInfoTest {
    
    /** Tests to ensure that the {@link SongInfo#clone()} method will clone correctly. */
    @Test
    public void testClone() {
        final SongInfo song = new SongInfo();
        final SongInfo clone;
        
        song.setID(7);
        song.setArtist("test artist");
        song.setAlbum("test album");
        song.setTitle("test title");
        song.setGenre("test genre");
        song.setYear(2014);
        song.setMillis(250000);
        song.setTrackNumber(3);
        song.setSampleRate(44000);
        song.setSampleCount(123456);
        song.setFile(new File("foo.bar"));
        
        clone = song.clone();
        assertNotNull(clone);
        assertTrue(song != clone);
        assertEquals(song.getID(), clone.getID());
        assertEquals(song.getArtist(), clone.getArtist());
        assertEquals(song.getAlbum(), clone.getAlbum());
        assertEquals(song.getTitle(), clone.getTitle());
        assertEquals(song.getGenre(), clone.getGenre());
        assertEquals(song.getYear(), clone.getYear());
        assertEquals(song.getMillis(), clone.getMillis());
        assertEquals(song.getTrackNumber(), clone.getTrackNumber());
        assertEquals(song.getSampleRate(), clone.getSampleRate());
        assertEquals(song.getSampleCount(), clone.getSampleCount());
        assertEquals(song.getFile(), clone.getFile());
    }
}
