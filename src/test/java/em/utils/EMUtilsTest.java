package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * A class containing JUnit tests to {@link EMUtils}.
 * 
 * @see EMUtils
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EMUtilsTest {
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(CharSequence)} function only returns <code>true</code> when
     * given a character sequence containing characters.
     */
    @Test
    public void testHasValues_CharSequence() {
        assertFalse(EMUtils.hasValues((String)null));
        assertFalse(EMUtils.hasValues(""));
        assertTrue(EMUtils.hasValues("a"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(CollectionSequence)} function only returns <code>true</code>
     * when given a collection containing elements.
     */
    @Test
    public void testHasValues_Collection() {
        assertFalse(EMUtils.hasValues((Collection<String>)null));
        assertFalse(EMUtils.hasValues(new ArrayList<String>()));
        assertTrue(EMUtils.hasValues(Arrays.asList("a")));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(Map)} function only returns <code>true</code> when given a map
     * containing entries.
     */
    @Test
    public void testHasValues_Map() {
        final HashMap<String, String> m = new HashMap<String, String>();
        
        m.put("a", "A");
        
        assertFalse(EMUtils.hasValues((Map<String, String>)null));
        assertFalse(EMUtils.hasValues(new HashMap<String, String>()));
        assertTrue(EMUtils.hasValues(m));
    }
    
    /** Tests to ensure that the {@link EMUtils#getExtension(File)} correctly returns a given file's extension. */
    @Test
    public void testGetExtension_File() {
        final String sep = File.separator;
        
        assertEquals("", EMUtils.getExtension((File)null));
        assertEquals("", EMUtils.getExtension(new File("")));
        assertEquals("", EMUtils.getExtension(new File("hello")));
        assertEquals("", EMUtils.getExtension(new File("hello.")));
        assertEquals("world", EMUtils.getExtension(new File("hello.world")));
        assertEquals("wOrLd", EMUtils.getExtension(new File("hello.wOrLd")));
        assertEquals("world", EMUtils.getExtension(new File(sep + "home" + sep + "test" + sep + "hello.world")));
        assertEquals("world", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep + "hello.world")));
        assertEquals("", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep + "hello.")));
        assertEquals("dir", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep)));
        assertEquals("", EMUtils.getExtension(new File("C:")));
        assertEquals("", EMUtils.getExtension(new File("C:hello")));
        assertEquals("world", EMUtils.getExtension(new File("C:hello.world")));
    }
    
    /** Tests to ensure that the {@link EMUtils#getExtension(String)} correctly returns a given file's extension. */
    @Test
    public void testGetExtension_String() {
        final String sep = File.separator;
        
        assertEquals("", EMUtils.getExtension((String)null));
        assertEquals("", EMUtils.getExtension(""));
        assertEquals("", EMUtils.getExtension("hello"));
        assertEquals("", EMUtils.getExtension("hello."));
        assertEquals("world", EMUtils.getExtension("hello.world"));
        assertEquals("wOrLd", EMUtils.getExtension("hello.wOrLd"));
        assertEquals("world", EMUtils.getExtension(sep + "home" + sep + "test" + sep + "hello.world"));
        assertEquals("world", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep + "hello.world"));
        assertEquals("", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep + "hello."));
        assertEquals("", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep));
        assertEquals("", EMUtils.getExtension("C:"));
        assertEquals("", EMUtils.getExtension("C:hello"));
        assertEquals("world", EMUtils.getExtension("C:hello.world"));
    }
}
