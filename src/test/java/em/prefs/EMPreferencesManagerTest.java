package em.prefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

import em.model.EMPreferences;
import em.prefs.EMPreferencesManager.EMPreferencesKey;

/**
 * @since v0.1
 * @author eviljoe
 */
public class EMPreferencesManagerTest {
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#createPreferences(Properties)} function correctly creates an
     * {@link EMPreferences} object.
     */
    @Test
    public void testCreatePreferences() {
        final String musicDir1 = "/home/foo/bar";
        final String musicDir2 = "hello.world";
        final String musicDir3 = "test/dir";
        final String allMusicDirs =
                musicDir1 + EMPreferencesManager.DELIMITER + musicDir2 + EMPreferencesManager.DELIMITER + musicDir3;
        final String metaFLACCommand = "/usr/bin/metaflac";
        final String dbHome = "/home/test/db/home";
        final Boolean dbRollback = Boolean.TRUE;
        final Properties props = new Properties();
        final EMPreferences emPrefs;
        
        props.put(EMPreferencesKey.MUSIC_DIRECTORIES.toString(), allMusicDirs);
        props.put(EMPreferencesKey.METAFLAC.toString(), metaFLACCommand);
        props.put(EMPreferencesKey.DATABASE_HOME.toString(), dbHome);
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), dbRollback.toString());
        
        emPrefs = EMPreferencesManager.getInstance().createPreferences(props);
        
        assertEquals(3, emPrefs.getMusicDirectories().length);
        assertEquals(musicDir1, emPrefs.getMusicDirectories()[0]);
        assertEquals(musicDir2, emPrefs.getMusicDirectories()[1]);
        assertEquals(musicDir3, emPrefs.getMusicDirectories()[2]);
        assertEquals(metaFLACCommand, emPrefs.getMetaFLACCommand());
        assertEquals(dbHome, emPrefs.getDatabaseHome());
        assertEquals(dbRollback, emPrefs.getDatabaseRollback());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#createPreferences(Properties)} function correctly creates an
     * empty {@link EMPreferences} object when given an empty value.
     */
    @Test
    public void testCreatePreferences_empty() {
        final EMPreferences emPrefs = EMPreferencesManager.getInstance().createPreferences(new Properties());
        
        assertNull(emPrefs.getMusicDirectories());
        assertNull(emPrefs.getMetaFLACCommand());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#createPreferences(Properties)} function correctly creates an
     * empty {@link EMPreferences} object when given a {@code null} value.
     */
    @Test
    public void testCreatePreferences_null() {
        final EMPreferences emPrefs = EMPreferencesManager.getInstance().createPreferences(null);
        
        assertNull(emPrefs.getMusicDirectories());
        assertNull(emPrefs.getMetaFLACCommand());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getStringArray(Properties, EMPreferencesKey)} function
     * correctly returns a string array with a single value when reading from a property whose value is a string with no
     * delimiters.
     */
    @Test
    public void testGetStringArray_Single() {
        final String musicDir = "/home/foo/bar";
        final Properties props = new Properties();
        final String[] a;
        
        props.put(EMPreferencesKey.MUSIC_DIRECTORIES.toString(), musicDir);
        
        a = EMPreferencesManager.getInstance().getStringArray(props, EMPreferencesKey.MUSIC_DIRECTORIES);
        assertEquals(1, a.length);
        assertEquals(musicDir, a[0]);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getStringArray(Properties, EMPreferencesKey)} function
     * correctly returns a string array with multiple values when reading from a property whose value is a string with
     * delimiters.
     */
    @Test
    public void testGetStringArray_Multiple() {
        final String musicDir1 = "/home/foo/bar";
        final String musicDir2 = "hello.world";
        final String musicDir3 = "test/dir";
        final String allMusicDirs =
                musicDir1 + EMPreferencesManager.DELIMITER + musicDir2 + EMPreferencesManager.DELIMITER + musicDir3;
        final Properties props = new Properties();
        final String[] a;
        
        props.put(EMPreferencesKey.MUSIC_DIRECTORIES.toString(), allMusicDirs);
        
        a = EMPreferencesManager.getInstance().getStringArray(props, EMPreferencesKey.MUSIC_DIRECTORIES);
        assertEquals(3, a.length);
        assertEquals(musicDir1, a[0]);
        assertEquals(musicDir2, a[1]);
        assertEquals(musicDir3, a[2]);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getStringArray(Properties, EMPreferencesKey)} function
     * correctly returns an empty array when reading from a property whose value is empty string.
     */
    @Test
    public void testGetStringArray_Empty() {
        final Properties props = new Properties();
        final String[] a;
        
        props.put(EMPreferencesKey.MUSIC_DIRECTORIES.toString(), "");
        
        a = EMPreferencesManager.getInstance().getStringArray(props, EMPreferencesKey.MUSIC_DIRECTORIES);
        assertNull(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getStringArray(Properties, EMPreferencesKey)} function
     * correctly returns {@code null} when reading from a property whose value is {@code null}.
     */
    @Test
    public void testGetStringArray_Null() {
        final String[] a =
                EMPreferencesManager.getInstance().getStringArray(new Properties(), EMPreferencesKey.MUSIC_DIRECTORIES);
        
        assertNull(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code true} when a boolean property's value is set to {@code "true"}.
     */
    @Test
    public void testGetBoolean_True() {
        final Properties props = new Properties();
        final Boolean a;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "true");
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertTrue(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code true} when a boolean property's value is set to {@code "true"} (ignoring leading and trailing whitespace).
     */
    @Test
    public void testGetBoolean_TrueWithWhitespace() {
        final Properties props = new Properties();
        final Boolean a;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "\ttrue \n ");
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertTrue(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code false} when a boolean property's value is set to {@code "false"}.
     */
    @Test
    public void testGetBoolean_False() {
        final Properties props = new Properties();
        final Boolean a;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "false");
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertFalse(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code false} when a boolean property's value is set to {@code "false"} (ignoring leading and trailing
     * whitespace).
     */
    @Test
    public void testGetBoolean_FalseWithWhitespace() {
        final Properties props = new Properties();
        final Boolean a;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "\tfalse \n ");
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertFalse(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code null} when a boolean property's value is set to an empty string.
     */
    @Test
    public void testGetBoolean_Empty() {
        final Properties props = new Properties();
        final Boolean a;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "");
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertNull(a);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code null} when a boolean property's value is not set.
     */
    @Test
    public void testGetBoolean_Null() {
        final Properties props = new Properties();
        final Boolean a;
        
        a = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertNull(a);
    }
}
