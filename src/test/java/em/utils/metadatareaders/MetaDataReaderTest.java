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
