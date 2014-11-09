package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * A class containing JUnit tests for {@link LibraryUtils}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtilsTest {
    
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
}
