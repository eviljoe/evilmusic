package em.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A class containing unit tests for {@link EMPreferences}.
 * 
 * @see EMPreferences
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferencesTest {
    
    /** Tests to ensure that the {@link EMPreferences#clone()} method will clone correctly. */
    @Test
    public void testClone() {
        final EMPreferences orig = new EMPreferences();
        final EMPreferences clone;
        
        orig.setMusicDirectories(new String[] {"/home/test/foo", "foo.bar", "/"});
        orig.setMetaFLACCommand("/usr/bin/metaflac");
        
        clone = orig.clone();
        assertNotNull(clone);
        assertTrue(orig != clone);
        assertArrayEquals(orig.getMusicDirectories(), clone.getMusicDirectories());
        assertEquals(orig.getMetaFLACCommand(), clone.getMetaFLACCommand());
    }
}
