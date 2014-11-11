package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import em.model.SongInfo;

/**
 * A class containing JUnit tests for {@link LibraryUtils}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtilsTest {
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    /**
     * Tests to ensure that the {@link LibraryUtils#isMusicFile(File)} will correctly determine if a file is a music
     * file.
     */
    @Test
    public void testIsMusicFile() {
        assertFalse(LibraryUtils.isMusicFile(null));
        assertFalse(LibraryUtils.isMusicFile(new File("")));
        assertFalse(LibraryUtils.isMusicFile(new File("foo.")));
        assertTrue(LibraryUtils.isMusicFile(new File("foo.flac")));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#convertToFile(String)} will correctly convert a music directory
     * string to a file.
     */
    @Test
    public void convertToFile() {
        final String home = System.getProperty("user.home");
        final String sep = File.separator;
        
        assertNull(LibraryUtils.convertToFile(""));
        assertNull(LibraryUtils.convertToFile(null));
        assertEquals(home + sep + "docs", LibraryUtils.convertToFile("$home" + sep + "docs").getPath());
        assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
        
        System.out.println();
        
    }
    
    @Test
    public void testSanitizeForClient_SongInfo() {
        final SongInfo a = new SongInfo(3);
        final SongInfo b = new SongInfo(7);
        
        a.setFile(new File("foo.bar"));
        assertEquals(a, LibraryUtils.sanitizeForClient(a));
        assertNull(a.getFile());
        assertNotNull(a.getID());
        
        b.setFile(null);
        assertEquals(b, LibraryUtils.sanitizeForClient(b));
        assertNull(b.getFile());
        assertNotNull(b.getID());
        
        // Call this to make sure no NPEs are thrown
        LibraryUtils.sanitizeForClient((SongInfo)null);
    }
    
    @Test
    public void testSanitizeForClient_Collection() {
        final ArrayList<SongInfo> infos = new ArrayList<>();
        final SongInfo a = new SongInfo(3);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = null;
        
        a.setFile(new File("foo.bar"));
        b.setFile(null);
        
        infos.add(a);
        infos.add(b);
        infos.add(c);
        
        assertEquals(infos, LibraryUtils.sanitizeForClient(infos));
        
        assertNull(infos.get(0).getFile());
        assertNotNull(infos.get(0).getID());
        
        assertNull(infos.get(1).getFile());
        assertNotNull(infos.get(1).getID());
    }
}
