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
