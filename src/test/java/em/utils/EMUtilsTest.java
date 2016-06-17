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

package em.utils;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.gen5.api.Test;

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
    @Test
    public void testToSet_NullSet() {
        assertThrows(NullPointerException.class, () -> EMUtils.toSet(new ArrayList<Integer>(), null));
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
    
    /** Tests to ensure that the {@code toBoolean} functions will correctly convert a boolean wrapper to a primitive. */
    @Test
    public void testToBoolean() {
        assertEquals(true, EMUtils.toBoolean(Boolean.TRUE));
        assertEquals(false, EMUtils.toBoolean(null));
        assertEquals(false, EMUtils.toBoolean(Boolean.FALSE, true));
        assertEquals(true, EMUtils.toBoolean(null, true));
    }
    
    /** Tests to ensure that the {@code toByte} functions will correctly convert a byte wrapper to a primitive. */
    @Test
    public void testToByte() {
        assertEquals((byte)7, EMUtils.toByte(new Byte((byte)7)));
        assertEquals((byte)0, EMUtils.toByte(null));
        assertEquals((byte)11, EMUtils.toByte(new Byte((byte)11), (byte)13));
        assertEquals((byte)13, EMUtils.toByte(null, (byte)13));
    }
    
    /** Tests to ensure that the {@code toShort} functions will correctly convert a short wrapper to a primitive. */
    @Test
    public void testToShort() {
        assertEquals((short)7, EMUtils.toShort(new Short((short)7)));
        assertEquals((short)0, EMUtils.toShort(null));
        assertEquals((short)11, EMUtils.toShort(new Short((short)11), (short)13));
        assertEquals((short)13, EMUtils.toShort(null, (short)13));
    }
    
    /** Tests to ensure that the {@code toInt} functions will correctly convert an integer wrapper to a primitive. */
    @Test
    public void testToInt() {
        assertEquals(7, EMUtils.toInt(new Integer(7)));
        assertEquals(0, EMUtils.toInt(null));
        assertEquals(11, EMUtils.toInt(new Integer(11), 13));
        assertEquals(13, EMUtils.toInt(null, 13));
    }
    
    /** Tests to ensure that the {@code toLong} functions will correctly convert a long wrapper to a primitive. */
    @Test
    public void testToLong() {
        assertEquals(7L, EMUtils.toLong(new Long(7L)));
        assertEquals(0L, EMUtils.toLong(null));
        assertEquals(11L, EMUtils.toLong(new Long(11L), 13L));
        assertEquals(13L, EMUtils.toLong(null, 13L));
    }
    
    /** Tests to ensure that the {@code toFloat} functions will correctly convert a float wrapper to a primitive. */
    @Test
    public void testToFloat() {
        assertEquals(7.0f, EMUtils.toFloat(new Float(7.0f)));
        assertEquals(0.0f, EMUtils.toFloat(null));
        assertEquals(11.0f, EMUtils.toFloat(new Float(11.0f), 13.0f));
        assertEquals(13.0f, EMUtils.toFloat(null, 13.0f));
    }
    
    /** Tests to ensure that the {@code toDouble} functions will correctly convert a double wrapper to a primitive. */
    @Test
    public void testToDouble() {
        assertEquals(7.0, EMUtils.toDouble(new Double(7.0)));
        assertEquals(0.0, EMUtils.toDouble(null));
        assertEquals(11.0, EMUtils.toDouble(new Double(11.0), 13.0));
        assertEquals(13.0, EMUtils.toDouble(null, 13.0));
    }
    
    /** Tests to ensure that the {@code toChar} functions will correctly convert a character wrapper to a primitive. */
    @Test
    public void testToChar() {
        assertEquals('a', EMUtils.toChar(new Character('a')));
        assertEquals('\0', EMUtils.toChar(null));
        assertEquals('b', EMUtils.toChar(new Character('b'), 'c'));
        assertEquals('c', EMUtils.toChar(null, 'c'));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will throw a
     * {@link NullPointerException} when given a null {@link Appendable}.
     */
    @Test
    public void testToCSV_AppendableIterableString_NullAppendable() throws IOException {
        assertThrows(NullPointerException.class, () -> EMUtils.toCSV(null, Arrays.asList(1, 2, 3), ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will not append anything
     * when given a {@code null} iterable.
     */
    @Test
    public void testToCSV_AppendableIterableString_NullIterable() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, null, ":");
        assertEquals(0, csv.length());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will not append anything
     * when given an empty iterable.
     */
    @Test
    public void testToCSV_AppendableIterableString_EmptyIterable() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, Arrays.asList(new String[0]), ":");
        assertEquals(0, csv.length());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will use the default
     * delimiter when given a {@code null} delimiter.
     */
    @Test
    public void testToCSV_AppendableIterableString_NullDelimiter() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, Arrays.asList("a", "b", "c"), null);
        assertEquals("a, b, c", csv.toString());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will not use a delimiter
     * when given an empty delimiter.
     */
    @Test
    public void testToCSV_AppendableIterableString_EmptyDelimiter() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, Arrays.asList("a", "b", "c"), "");
        assertEquals("abc", csv.toString());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will use a custom delimiter
     * when given a custom delimiter.
     */
    @Test
    public void testToCSV_AppendableIterableString_CustomDelimiter() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, Arrays.asList("a", "b", "c"), ":zz:");
        assertEquals("a:zz:b:zz:c", csv.toString());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Appendable, Iterable, String)} function will convert null values
     * correctly.
     */
    @Test
    public void testToCSV_AppendableIterableString_IterableWithNullValues() throws IOException {
        final StringBuilder csv = new StringBuilder();
        
        EMUtils.toCSV(csv, Arrays.asList("a", null, "c"), ":");
        assertEquals("a:null:c", csv.toString());
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will return an empty string when given
     * a {@code null} array.
     */
    @Test
    public void testToCSV_ArrayString_NullArray() {
        assertEquals("", EMUtils.toCSV((String[])null, ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will return an empty string when given
     * an empty array.
     */
    @Test
    public void testToCSV_ArrayString_EmptyArray() {
        assertEquals("", EMUtils.toCSV(new String[0], ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will use the default delimiter when
     * given a {@code null} delimiter.
     */
    @Test
    public void testToCSV_ArrayString_NullDelimiter() {
        assertEquals("a, b, c", EMUtils.toCSV(new String[] {"a", "b", "c"}, null));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will not use a delimiter when given an
     * empty delimiter.
     */
    @Test
    public void testToCSV_ArrayString_EmptyDelimiter() {
        assertEquals("abc", EMUtils.toCSV(new String[] {"a", "b", "c"}, ""));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will use a custom delimiter when given
     * a custom delimiter.
     */
    @Test
    public void testToCSV_ArrayString_CustomDelimiter() {
        assertEquals("a::b::c", EMUtils.toCSV(new String[] {"a", "b", "c"}, "::"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Object[], String)} function will not throw exceptions when creating
     * CSV from an array containing {@code null} values.
     */
    @Test
    public void testToCSV_ArrayString_ArrayWithNullValues() {
        assertEquals("a:null:c", EMUtils.toCSV(new String[] {"a", null, "c"}, ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(int[], String)} function will return an empty string when given a
     * {@code null} array.
     */
    @Test
    public void testToCSV_IntArrayString_NullArray() {
        assertEquals("", EMUtils.toCSV((int[])null, ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(int[], String)} function will return an empty string when given an
     * empty array.
     */
    @Test
    public void testToCSV_IntArrayString_EmptyArray() {
        assertEquals("", EMUtils.toCSV(new int[0], ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(int[], String)} function will use the default delimiter when given
     * a {@code null} delimiter.
     */
    @Test
    public void testToCSV_IntArrayString_NullDelimiter() {
        assertEquals("7, 11, 13", EMUtils.toCSV(new int[] {7, 11, 13}, null));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(int[], String)} function will not use a delimiter when given an
     * empty delimiter.
     */
    @Test
    public void testToCSV_IntArrayString_EmptyDelimiter() {
        assertEquals("71113", EMUtils.toCSV(new int[] {7, 11, 13}, ""));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(int[], String)} function will use a custom delimiter when given a
     * custom delimiter.
     */
    @Test
    public void testToCSV_IntArrayString_CustomDelimiter() {
        assertEquals("7::11::13", EMUtils.toCSV(new int[] {7, 11, 13}, "::"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will return an empty string when
     * given a {@code null} array.
     */
    @Test
    public void testToCSV_CollectionString_NullArray() {
        assertEquals("", EMUtils.toCSV((Collection<String>)null, ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will return an empty string when
     * given an empty array.
     */
    @Test
    public void testToCSV_CollectionString_EmptyArray() {
        assertEquals("", EMUtils.toCSV(new ArrayList<String>(0), ":"));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will use the default delimiter when
     * given a {@code null} delimiter.
     */
    @Test
    public void testToCSV_CollectionString_NullDelimiter() {
        final ArrayList<String> l = new ArrayList<String>();
        
        l.add("a");
        l.add("b");
        l.add("c");
        
        assertEquals("a, b, c", EMUtils.toCSV(l, null));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will not use a delimiter when given
     * an empty delimiter.
     */
    @Test
    public void testToCSV_CollectionString_EmptyDelimiter() {
        final ArrayList<String> l = new ArrayList<String>();
        
        l.add("a");
        l.add("b");
        l.add("c");
        
        assertEquals("abc", EMUtils.toCSV(l, ""));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will use a custom delimiter when
     * given a custom delimiter.
     */
    @Test
    public void testToCSV_CollectionString_CustomDelimiter() {
        final ArrayList<String> l = new ArrayList<String>();
        
        l.add("a");
        l.add("b");
        l.add("c");
        
        assertEquals("a.b.c", EMUtils.toCSV(l, "."));
    }
    
    /**
     * Tests to ensure that the {@link EMUtils#toCSV(Collection, String)} function will not throw exceptions when
     * creating CSV from an array containing {@code null} values.
     */
    @Test
    public void testToCSV_CollectionString_ArrayWithNullValues() {
        final ArrayList<String> l = new ArrayList<String>();
        
        l.add("a");
        l.add(null);
        l.add("c");
        
        assertEquals("a.null.c", EMUtils.toCSV(l, "."));
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
