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

package em.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertTrue;

import org.junit.gen5.api.Test;

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
        orig.setDatabaseHome("/home/destroyerofworlds/.dbhome");
        orig.setDatabaseRollback(false);
        orig.setServerPort(31337);
        
        clone = orig.clone();
        assertNotNull(clone);
        assertTrue(orig != clone);
        assertThat(clone.getMusicDirectories(), is(arrayContaining(orig.getMusicDirectories())));
        assertEquals(orig.getMetaFLACCommand(), clone.getMetaFLACCommand());
        assertEquals(orig.getDatabaseHome(), clone.getDatabaseHome());
        assertEquals(orig.getDatabaseRollback(), clone.getDatabaseRollback());
        assertEquals(orig.getServerPort(), clone.getServerPort());
    }
}
