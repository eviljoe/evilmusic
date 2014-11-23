package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import em.model.Identifiable;

/**
 * A class containing JUnit tests to {@link EMUtils}.
 * 
 * @see EMUtils
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EMUtilsTest {
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(CharSequence)} function only returns <code>true</code> when
     * given a character sequence containing characters.
     */
    @Test
    public void testHasValues_CharSequence() {
        assertFalse(EMUtils.hasValues((String)null));
        assertFalse(EMUtils.hasValues(""));
        assertTrue(EMUtils.hasValues("a"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(CollectionSequence)} function only returns <code>true</code>
     * when given a collection containing elements.
     */
    @Test
    public void testHasValues_Collection() {
        assertFalse(EMUtils.hasValues((Collection<String>)null));
        assertFalse(EMUtils.hasValues(new ArrayList<String>()));
        assertTrue(EMUtils.hasValues(Arrays.asList("a")));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#hasValues(Map)} function only returns <code>true</code> when given a map
     * containing entries.
     */
    @Test
    public void testHasValues_Map() {
        final HashMap<String, String> m = new HashMap<String, String>();
        
        m.put("a", "A");
        
        assertFalse(EMUtils.hasValues((Map<String, String>)null));
        assertFalse(EMUtils.hasValues(new HashMap<String, String>()));
        assertTrue(EMUtils.hasValues(m));
    }
    
    /** Tests to ensure that the {@link EMUtils#getExtension(File)} correctly returns a given file's extension. */
    @Test
    public void testGetExtension_File() {
        final String sep = File.separator;
        
        assertEquals("", EMUtils.getExtension((File)null));
        assertEquals("", EMUtils.getExtension(new File("")));
        assertEquals("", EMUtils.getExtension(new File("hello")));
        assertEquals("", EMUtils.getExtension(new File("hello.")));
        assertEquals("world", EMUtils.getExtension(new File("hello.world")));
        assertEquals("wOrLd", EMUtils.getExtension(new File("hello.wOrLd")));
        assertEquals("world", EMUtils.getExtension(new File(sep + "home" + sep + "test" + sep + "hello.world")));
        assertEquals("world", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep + "hello.world")));
        assertEquals("", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep + "hello.")));
        assertEquals("dir", EMUtils.getExtension(new File(sep + "home" + sep + "test.dir" + sep)));
        assertEquals("", EMUtils.getExtension(new File("C:")));
        assertEquals("", EMUtils.getExtension(new File("C:hello")));
        assertEquals("world", EMUtils.getExtension(new File("C:hello.world")));
    }
    
    /** Tests to ensure that the {@link EMUtils#getExtension(String)} correctly returns a given file's extension. */
    @Test
    public void testGetExtension_String() {
        final String sep = File.separator;
        
        assertEquals("", EMUtils.getExtension((String)null));
        assertEquals("", EMUtils.getExtension(""));
        assertEquals("", EMUtils.getExtension("hello"));
        assertEquals("", EMUtils.getExtension("hello."));
        assertEquals("world", EMUtils.getExtension("hello.world"));
        assertEquals("wOrLd", EMUtils.getExtension("hello.wOrLd"));
        assertEquals("world", EMUtils.getExtension(sep + "home" + sep + "test" + sep + "hello.world"));
        assertEquals("world", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep + "hello.world"));
        assertEquals("", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep + "hello."));
        assertEquals("", EMUtils.getExtension(sep + "home" + sep + "test.dir" + sep));
        assertEquals("", EMUtils.getExtension("C:"));
        assertEquals("", EMUtils.getExtension("C:hello"));
        assertEquals("world", EMUtils.getExtension("C:hello.world"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#equalsIgnoreCase(String, String)} function correctly determines when two
     * strings are equal (ignoring case).
     */
    @Test
    public void testEqualsIgnoreCase() {
        assertTrue(EMUtils.equalsIgnoreCase(null, null));
        assertTrue(EMUtils.equalsIgnoreCase("", ""));
        assertTrue(EMUtils.equalsIgnoreCase("abc", "abc"));
        assertTrue(EMUtils.equalsIgnoreCase("abc", "ABC"));
        assertTrue(EMUtils.equalsIgnoreCase("ABC", "abc"));
        assertTrue(EMUtils.equalsIgnoreCase("ABC", "ABC"));
        assertTrue(EMUtils.equalsIgnoreCase("AbC", "aBc"));
        
        assertFalse(EMUtils.equalsIgnoreCase("", null));
        assertFalse(EMUtils.equalsIgnoreCase(null, ""));
        assertFalse(EMUtils.equalsIgnoreCase("", "abc"));
        assertFalse(EMUtils.equalsIgnoreCase("abc", ""));
        assertFalse(EMUtils.equalsIgnoreCase("abc", "xyz"));
        assertFalse(EMUtils.equalsIgnoreCase("xyz", "abc"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toList(Collection)} will return a list containing a given collection's
     * elements.
     */
    @Test
    public void testToList() {
        final HashSet<Integer> set = new HashSet<Integer>();
        final List<Integer> list;
        
        set.add(2);
        set.add(1);
        set.add(null);
        
        list = EMUtils.toList(set);
        assertEquals(null, list.get(0));
        assertEquals(new Integer(1), list.get(1));
        assertEquals(new Integer(2), list.get(2));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toList(Collection)} will return an empty list when given an empty
     * collection.
     */
    @Test
    public void testToList_Empty() {
        assertEquals(0, EMUtils.toList(new HashSet<Integer>()).size());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toList(Collection)} will return {@code null} when given a {@code null}
     * collection.
     */
    @Test
    public void testToList_Null() {
        assertNull(EMUtils.toList(null));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#compare(Comparable, Object)} function can correctly compare two non-
     * {@code null} values.
     */
    @Test
    public void testCompare_NoNull() {
        final Integer less = new Integer(7);
        final Integer greater = new Integer(11);
        
        assertTrue(EMUtils.compare(less, greater) < 0);
        assertTrue(EMUtils.compare(greater, less) > 0);
        assertTrue(EMUtils.compare(less, less) == 0);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#compare(Comparable, Object, NullComparator.Order)} will consider a
     * {@code null} value to be less than a non-{@code null} value when using the order
     * {@link NullComparator.Order#ASC_NULLS_FIRST}.
     */
    @Test
    public void testCompare_AscNullsFirst() {
        final Integer less = null;
        final Integer greater = new Integer(11);
        
        assertTrue(EMUtils.compare(less, greater, NullComparator.Order.ASC_NULLS_FIRST) < 0);
        assertTrue(EMUtils.compare(greater, less, NullComparator.Order.ASC_NULLS_FIRST) > 0);
        assertTrue(EMUtils.compare(null, null, NullComparator.Order.ASC_NULLS_FIRST) == 0);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#compare(Comparable, Object, NullComparator.Order)} will consider a
     * {@code null} value to be greater than a non-{@code null} value when using the order
     * {@link NullComparator.Order#ASC_NULLS_LAST}.
     */
    @Test
    public void testCompare_AscNullsLast() {
        final Integer less = new Integer(7);
        final Integer greater = null;
        
        assertTrue(EMUtils.compare(less, greater, NullComparator.Order.ASC_NULLS_LAST) < 0);
        assertTrue(EMUtils.compare(greater, less, NullComparator.Order.ASC_NULLS_LAST) > 0);
        assertTrue(EMUtils.compare(null, null, NullComparator.Order.ASC_NULLS_LAST) == 0);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#compare(Comparable, Object, NullComparator.Order)} will consider a
     * {@code null} value to be greater than a non-{@code null} value when using the order
     * {@link NullComparator.Order#DSC_NULLS_FIRST}.
     */
    @Test
    public void testCompare_DescNullsFirst() {
        final Integer less = new Integer(7);
        final Integer greater = null;
        
        assertTrue(EMUtils.compare(less, greater, NullComparator.Order.DESC_NULLS_FIRST) > 0);
        assertTrue(EMUtils.compare(greater, less, NullComparator.Order.DESC_NULLS_FIRST) < 0);
        assertTrue(EMUtils.compare(null, null, NullComparator.Order.ASC_NULLS_FIRST) == 0);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#compare(Comparable, Object, NullComparator.Order)} will consider a
     * {@code null} value to be less than a non-{@code null} value when using the order
     * {@link NullComparator.Order#DESC_NULLS_LAST}.
     */
    @Test
    public void testCompare_DescNullsLast() {
        final Integer less = new Integer(7);
        final Integer greater = null;
        
        assertTrue(EMUtils.compare(less, greater, NullComparator.Order.DESC_NULLS_LAST) < 0);
        assertTrue(EMUtils.compare(greater, less, NullComparator.Order.DESC_NULLS_LAST) > 0);
        assertTrue(EMUtils.compare(null, null, NullComparator.Order.ASC_NULLS_LAST) == 0);
    }
    
    /** Tests that the {@link EMUtils#toSet(Collection, Set)} function will convert a collection into a set. */
    @Test
    public void testToSet() {
        final Integer a = 5;
        final Integer b = 7;
        final Integer c = 11;
        final ArrayList<Integer> list = new ArrayList<>();
        final Set<Integer> set;
        
        list.add(a);
        list.add(b);
        list.add(c);
        
        set = EMUtils.toSet(list, new HashSet<Integer>());
        
        assertEquals(3, set.size());
        assertTrue(set.containsAll(list));
    }
    
    /**
     * Tests that the {@link EMUtils#toSet(Collection, Set)} function will return a {@code null} set when given a
     * {@code null} collection.
     */
    @Test
    public void testToSet_NullCollection() {
        assertNull(EMUtils.toSet(null, new HashSet<Integer>()));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toSet(Collection, Set)} function will throw a
     * {@link NullPointerException} when given a {@code null} set to add the collection elements to.
     */
    @Test(expected = NullPointerException.class)
    public void testToSet_NullSet() {
        EMUtils.toSet(new ArrayList<Integer>(), null);
    }
    
    /**
     * Tests that the {@link EMUtils#toSet(Collection, Set)} function will return an empty set when given an empty
     * collection.
     */
    @Test
    public void testToSet_EmptyCollection() {
        final HashSet<Integer> set = EMUtils.toSet(new ArrayList<Integer>(), new HashSet<Integer>());
        
        assertNotNull(set);
        assertTrue(set.isEmpty());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function can find an element in a
     * collection using that element's ID.
     */
    @Test
    public void testFindByID() {
        final TestIdentifiable a = new TestIdentifiable(5);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(null);
        final TestIdentifiable d = new TestIdentifiable(11);
        final TestIdentifiable e = new TestIdentifiable(17);
        final List<TestIdentifiable> list = Arrays.asList(a, b, c, d, e);
        final TestIdentifiable found = EMUtils.findByID(list, d.getID());
        
        assertNotNull(found);
        assertEquals(d, found);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function will return {@code null} when
     * given an ID that does not correspond to an element in the collection.
     */
    @Test
    public void testFindByID_IDNotInCollection() {
        final TestIdentifiable a = new TestIdentifiable(5);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(null);
        final TestIdentifiable d = new TestIdentifiable(11);
        final TestIdentifiable e = new TestIdentifiable(17);
        final List<TestIdentifiable> list = Arrays.asList(a, b, c, d, e);
        final TestIdentifiable found = EMUtils.findByID(list, 19);
        
        assertNull(found);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function will return {@code null} when
     * trying to find an element in an empty collection.
     */
    @Test
    public void testFindByID_EmptyCollection() {
        assertNull(EMUtils.findByID(new ArrayList<TestIdentifiable>(), 5));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function will return {@code null} when
     * trying to find an element in a {@code null} collection.
     */
    @Test
    public void testFindByID_NullCollection() {
        assertNull(EMUtils.findByID(null, 5));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function can find an element whose ID is
     * {@code null}.
     */
    @Test
    public void testFindByID_NullIDInCollection() {
        final TestIdentifiable a = new TestIdentifiable(5);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(null);
        final TestIdentifiable d = new TestIdentifiable(11);
        final TestIdentifiable e = new TestIdentifiable(17);
        final List<TestIdentifiable> list = Arrays.asList(a, b, c, d, e);
        final TestIdentifiable found = EMUtils.findByID(list, c.getID());
        
        assertNotNull(found);
        assertEquals(c, found);
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#findByID(Collection, Integer)} function will return {@code null} when
     * trying to find a {@code null} ID within a collection with no {@code null} IDs.
     */
    @Test
    public void testFindByID_NullIDNotInCollection() {
        final TestIdentifiable a = new TestIdentifiable(5);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final TestIdentifiable d = new TestIdentifiable(13);
        final TestIdentifiable e = new TestIdentifiable(17);
        final List<TestIdentifiable> list = Arrays.asList(a, b, c, d, e);
        
        assertNull(EMUtils.findByID(list, null));
    }
    
    /* *************** */
    /* Utility Classes */
    /* *************** */
    
    /**
     * @since v0.1
     * @author eviljoe
     */
    private static class TestIdentifiable implements Identifiable {
        
        private Integer id;
        
        public TestIdentifiable(Integer id) {
            super();
            setID(id);
        }
        
        @Override
        public Integer getID() {
            return id;
        }
        
        @Override
        public void setID(Integer id) {
            this.id = id;
        }
    }
}
