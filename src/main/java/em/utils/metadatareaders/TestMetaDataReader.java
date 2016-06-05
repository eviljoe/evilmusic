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

import java.io.File;

import em.model.SongInfo;
import em.utils.EMUtils;

/**
 * A meta-data reader for EvilMusic test files. This is used by integration tests so they don't have to rely on large,
 * potentially copyrighted, "real" music files.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class TestMetaDataReader extends MetaDataReader {
    
    private static final String TEST_FILE_EXT = "emtest";
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public TestMetaDataReader() {
        super();
    }
    
    /* ************************** */
    /* Meta Data Reader Functions */
    /* ************************** */
    
    /** {@inheritDoc} */
    @Override
    public boolean canReadMetaData(File f) {
        return EMUtils.equalsIgnoreCase(TEST_FILE_EXT, EMUtils.getExtension(f).trim());
    }
    
    /** {@inheritDoc} */
    @Override
    public SongInfo readMetaData(File f) throws MetaDataReadException {
        final SongInfo info = new SongInfo();
        final String fname = f.getName();
        final int fhash = fname.hashCode();
        
        info.setArtist(fname + " artist");
        info.setAlbum(fname + " album");
        info.setTitle(fname + " title");
        info.setGenre(fname + " genre");
        info.setMillis(fhash);
        info.setTrackNumber(fhash % 12);
        info.setSampleRate(fhash % 1000);
        info.setSampleCount(fhash % 2000);
        
        return info;
    }
}
