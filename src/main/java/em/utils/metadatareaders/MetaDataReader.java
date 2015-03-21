package em.utils.metadatareaders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import em.model.SongInfo;

/**
 * @since v0.1
 * @author eviljoe
 */
public abstract class MetaDataReader {
    
    public static final List<MetaDataReader> META_DATA_READERS;
    
    /* ************ */
    /* Static Block */
    /* ************ */
    
    static {
        final ArrayList<MetaDataReader> readers = new ArrayList<MetaDataReader>();
        
        readers.add(new FLACMetaDataReader());
        readers.add(new TestMetaDataReader());
        
        META_DATA_READERS = Collections.unmodifiableList(readers);
    }
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public MetaDataReader() {
        super();
    }
    
    /* ****************** */
    /* Abstract Functions */
    /* ****************** */
    
    public abstract boolean canReadMetaData(File f);
    
    public abstract SongInfo readMetaData(File f) throws MetaDataReadException;
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public static MetaDataReader getMetaDataReaderFor(File f) {
        final Iterator<MetaDataReader> it = META_DATA_READERS.iterator();
        MetaDataReader reader = null;
        
        while(it.hasNext() && reader == null) {
            final MetaDataReader r = it.next();
            
            if(r.canReadMetaData(f)) {
                reader = r;
            }
        }
        
        return reader;
    }
    
    public static SongInfo getMetaDataFor(File f) throws MetaDataReadException {
        final MetaDataReader reader = getMetaDataReaderFor(f);
        SongInfo info = null;
        
        if(reader != null) {
            info = reader.readMetaData(f);
        }
        
        return info;
    }
}
