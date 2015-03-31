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

package em.utils.metadatareaders;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

/**
 * @since v0.1
 * @author eviljoe
 */
public class MetaDataReaderTest {
    
    @Test
    public void testGetMetaDataReaderFor() {
        assertEquals(FLACMetaDataReader.class, MetaDataReader.getMetaDataReaderFor(new File("foo.flac")).getClass());
    }
}
