package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import em.model.QueueElement;
import em.model.SongInfo;

/**
 * A class containing JUnit tests for {@link LibraryUtils}.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LibraryUtilsTest {
    
    /**
     * Tests to ensure that {@link LibraryUtils#convertToFile(String)} will correctly convert a music directory string
     * to a file.
     */
    @Test
    public void convertToFile() {
        final String home = System.getProperty("user.home");
        final String sep = File.separator;
        
        assertNull(LibraryUtils.convertToFile(""));
        assertNull(LibraryUtils.convertToFile(null));
        assertEquals(home + sep + "docs", LibraryUtils.convertToFile("$home" + sep + "docs").getPath());
        assertEquals("foo" + sep + "bar", LibraryUtils.convertToFile("foo" + sep + "bar").getPath());
        
        System.out.println();
        
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} will remove a the file information from a
     * {@link SongInfo}.
     */
    @Test
    public void testSanitizeForClient_SongInfo() {
        final SongInfo a = new SongInfo(3);
        
        a.setFile(new File("foo.bar"));
        assertEquals(a, LibraryUtils.sanitizeForClient(a));
        assertNull(a.getFile());
        assertNotNull(a.getID());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} can handle a {@link SongInfo} whose file is
     * already {@code null} without throwing exceptions.
     */
    @Test
    public void testSanitizeForClient_SongInfo_NullFile() {
        final SongInfo b = new SongInfo(7);
        
        b.setFile(null);
        assertEquals(b, LibraryUtils.sanitizeForClient(b));
        assertNull(b.getFile());
        assertNotNull(b.getID());
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeForClient(SongInfo)} can handle a {@code null} {@link SongInfo}
     * without throwing exceptions.
     */
    @Test
    public void testSanitizeForClient_SongInfo_Null() {
        assertNull(LibraryUtils.sanitizeForClient((SongInfo)null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeSongsForClient(Collection)} will correctly remove the file from
     * each {@link SongInfo} contained within the collection.
     */
    @Test
    public void testSanitizeSongsForClient() {
        final ArrayList<SongInfo> infos = new ArrayList<>();
        final SongInfo a = new SongInfo(3);
        final SongInfo b = new SongInfo(7);
        final SongInfo c = null;
        
        a.setFile(new File("foo.bar"));
        b.setFile(null);
        
        infos.add(a);
        infos.add(b);
        infos.add(c);
        
        assertEquals(infos, LibraryUtils.sanitizeSongsForClient(infos));
        
        assertNull(infos.get(0).getFile());
        assertNotNull(infos.get(0).getID());
        
        assertNull(infos.get(1).getFile());
        assertNotNull(infos.get(1).getID());
        
        assertNull(infos.get(2));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeSongsForClient(Collection)} will gracefully handle an empty
     * collection.
     */
    @Test
    public void testSanitizeSongsForClient_Empty() {
        final List<SongInfo> infos = new ArrayList<SongInfo>();
        assertEquals(infos, LibraryUtils.sanitizeSongsForClient(infos));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeSongsForClient(Collection)} will gracefully handle a
     * {@code null} collection.
     */
    @Test
    public void testSanitizeSongsForClient_Null() {
        assertNull(LibraryUtils.sanitizeSongsForClient(null));
    }
    
    /**
     * Tests to ensure that {@link LibraryUtils#sanitizeElementsForClient(Collection)} will correctly remove the file
     * from each {@link QueueElement} contained within the collection.
     */
    @Test
    public void testSanitizeElementsForClient() {
        final ArrayList<QueueElement> elements = new ArrayList<>();
        final QueueElement a = new QueueElement(3, new SongInfo(5));
        final QueueElement b = new QueueElement(7, new SongInfo(11));
        final QueueElement c = new QueueElement(7, null);
        final QueueElement d = null;
        
        a.getSong().setFile(new File("foo.bar"));
        b.getSong().setFile(null);
        
        elements.add(a);
        elements.add(b);
        elements.add(c);
        elements.add(d);
        
        assertEquals(elements, LibraryUtils.sanitizeElementsForClient(elements));
        
        assertNull(elements.get(0).getSong().getFile());
        assertNotNull(elements.get(0).getID());
        
        assertNull(elements.get(1).getSong().getFile());
        assertNotNull(elements.get(1).getID());
        
        assertNull(elements.get(2).getSong());
        assertNotNull(elements.get(2).getID());
        
        assertNull(elements.get(3));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeElementsForClient(Collection)} will gracefully handle an
     * empty collection.
     */
    @Test
    public void testSanitizeElementsForClient_Empty() {
        final List<QueueElement> infos = new ArrayList<QueueElement>();
        assertEquals(infos, LibraryUtils.sanitizeElementsForClient(infos));
    }
    
    /**
     * Tests to ensure that the {@link LibraryUtils#sanitizeElementsForClient(Collection)} will gracefully handle a
     * {@code null} collection.
     */
    @Test
    public void testSanitizeElementsForClient_Null() {
        assertNull(LibraryUtils.sanitizeElementsForClient(null));
    }
}
