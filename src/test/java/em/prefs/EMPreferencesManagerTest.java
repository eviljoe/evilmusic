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
    public void testCreatePreferences_Empty() {
        final EMPreferences emPrefs = EMPreferencesManager.getInstance().createPreferences(new Properties());
        
        assertNull(emPrefs.getMusicDirectories());
        assertNull(emPrefs.getMetaFLACCommand());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#createPreferences(Properties)} function correctly creates an
     * empty {@link EMPreferences} object when given a {@code null} value.
     */
    @Test
    public void testCreatePreferences_Null() {
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
        final Boolean b;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "true");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertTrue(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code true} when a boolean property's value is set to {@code "true"} (ignoring leading and trailing whitespace).
     */
    @Test
    public void testGetBoolean_TrueWithWhitespace() {
        final Properties props = new Properties();
        final Boolean b;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "\ttrue \n ");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertTrue(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code false} when a boolean property's value is set to {@code "false"}.
     */
    @Test
    public void testGetBoolean_False() {
        final Properties props = new Properties();
        final Boolean b;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "false");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertFalse(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code false} when a boolean property's value is set to {@code "false"} (ignoring leading and trailing
     * whitespace).
     */
    @Test
    public void testGetBoolean_FalseWithWhitespace() {
        final Properties props = new Properties();
        final Boolean b;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "\tfalse \n ");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertFalse(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code null} when a boolean property's value is set to an empty string.
     */
    @Test
    public void testGetBoolean_Empty() {
        final Properties props = new Properties();
        final Boolean b;
        
        props.put(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertNull(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code null} when a boolean property's value is not set.
     */
    @Test
    public void testGetBoolean_Null() {
        final Properties props = new Properties();
        final Boolean b;
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertNull(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getBoolean(Properties, EMPreferencesKey)} function returns
     * {@code null} when a boolean property's value is not valid.
     */
    @Test
    public void testGetBoolean_Invalid() {
        final Properties props = new Properties();
        final Boolean b;
        
        props.setProperty(EMPreferencesKey.DATABASE_ROLLBACK.toString(), "asdf");
        
        b = EMPreferencesManager.getInstance().getBoolean(props, EMPreferencesKey.DATABASE_ROLLBACK);
        assertNull(b);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * the integer value of the given positive integer string.
     */
    @Test
    public void testGetInteger_PositiveInteger() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "123");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertEquals(123, i.intValue());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * the integer value of the given negative integer string.
     */
    @Test
    public void testGetInteger_NegativeInteger() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "-123");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertEquals(-123, i.intValue());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * the integer value of the given positive integer string that has leading and trailing whitespace.
     */
    @Test
    public void testGetInteger_PositiveIntegerWithWhitespace() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "\t 123\t ");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertEquals(123, i.intValue());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * the integer value of the given negative integer string that has leading and trailing whitespace.
     */
    @Test
    public void testGetInteger_NegativeIntegerWithWhitespace() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "\t -123\t ");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertEquals(-123, i.intValue());
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * {@code null} when given an empty string.
     */
    @Test
    public void testGetInteger_Empty() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertNull(i);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * {@code null} when given a {@code null} string.
     */
    @Test
    public void testGetInteger_Null() {
        final Properties props = new Properties();
        final Integer i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertNull(i);
    }
    
    /**
     * Tests to ensure that the {@link EMPreferencesManager#getInteger(Properties, EMPreferencesKey)} function returns
     * {@code null} when given a string that is not a valid integer.
     */
    @Test
    public void testGetInteger_Invalid() {
        final Properties props = new Properties();
        final Integer i;
        
        props.setProperty(EMPreferencesKey.SERVER_PORT.toString(), "asdf");
        i = EMPreferencesManager.getInstance().getInteger(props, EMPreferencesKey.SERVER_PORT);
        
        assertNull(i);
    }
}
