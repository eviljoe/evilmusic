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
import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.gen5.api.Assertions.assertNotNull;
import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.gen5.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.gen5.api.Test;

import em.model.Identifiable;

/**
 * A class containing unit tests for {@link IDSet}.
 *
 * @see IDSet
 *
 * @since v0.1
 * @author eviljoe
 */
public class IDSetTest {
    
    /** Tests to ensure that the {@link IDSet#IDSet()} constructor will create an empty set. */
    @Test
    public void testConstructor() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        assertEquals(0, s.size());
    }
    
    /** Tests to ensure that the {@link IDSet#addAll(Iterator)} function will correctly add values to the set. */
    @Test
    public void testAddAll_Iterator_Values() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final TestIdentifiable d = new TestIdentifiable(13);
        final TestIdentifiable e = new TestIdentifiable(17);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        final ArrayList<TestIdentifiable> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        
        set.add(d);
        set.addAll(list.iterator());
        
        assertTrue(set.contains(a));
        assertTrue(set.contains(b));
        assertTrue(set.contains(c));
        assertTrue(set.contains(d));
        assertFalse(set.contains(e));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#addAll(Iterator)} function will not add any values to the set when given an
     * empty iterator.
     */
    @Test
    public void testAddAll_Iterator_Empty() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        final ArrayList<TestIdentifiable> list = new ArrayList<>();
        
        set.add(a);
        set.addAll(list.iterator());
        
        assertTrue(set.contains(a));
        assertFalse(set.contains(b));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#addAll(Iterator)} function will not add any values to the set when given a
     * <code>null</code> iterator.
     */
    @Test
    public void testAddAll_Iterator_Null() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        
        set.add(a);
        set.addAll((Iterator<TestIdentifiable>)null);
        
        assertTrue(set.contains(a));
        assertFalse(set.contains(b));
    }
    
    /** Tests to ensure that the {@link IDSet#addAll(Iterable)} function will correctly add values to the set. */
    @Test
    public void testAddAll_Iterable_Values() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final TestIdentifiable d = new TestIdentifiable(13);
        final TestIdentifiable e = new TestIdentifiable(17);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        final ArrayList<TestIdentifiable> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        
        set.add(d);
        set.addAll(list.iterator());
        
        assertTrue(set.contains(a));
        assertTrue(set.contains(b));
        assertTrue(set.contains(c));
        assertTrue(set.contains(d));
        assertFalse(set.contains(e));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#addAll(Iterable)} function will not add any values to the set when given an
     * empty iterator.
     */
    @Test
    public void testAddAll_Iterable_Empty() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        final ArrayList<TestIdentifiable> list = new ArrayList<>();
        
        set.add(a);
        set.addAll(list.iterator());
        
        assertTrue(set.contains(a));
        assertFalse(set.contains(b));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#addAll(Iterable)} function will not add any values to the set when given a
     * <code>null</code> iterator.
     */
    @Test
    public void testAddAll_Iterable_Null() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final IDSet<TestIdentifiable> set = new IDSet<>();
        
        set.add(a);
        set.addAll((Iterator<TestIdentifiable>)null);
        
        assertTrue(set.contains(a));
        assertFalse(set.contains(b));
    }
    
    /** Tests to ensure that the {@link IDSet#add(Identifiable)} function will add a value to the set. */
    @Test
    public void testAdd_Identifiable_NotNull() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        assertTrue(s.add(a));
        assertEquals(1, s.size());
        assertTrue(s.contains(a));
        
        assertTrue(s.add(b));
        assertEquals(2, s.size());
        assertTrue(s.contains(b));
        
        assertTrue(s.add(c));
        assertEquals(3, s.size());
        assertTrue(s.contains(c));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#add(Identifiable)} function will not add a <code>null</code> value to the
     * set.
     */
    @Test
    public void testAdd_Identifiable_Null() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        assertFalse(s.add(null));
        assertEquals(0, s.size());
    }
    
    /**
     * Tests to ensure that the {@link IDSet#add(Identifiable)} function will not add an {@link Identifiable} with a
     * <code>null</code> ID to the set.
     */
    @Test
    public void testAdd_Identifiable_NullID() {
        final TestIdentifiable a = new TestIdentifiable(null);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        assertFalse(s.add(a));
        assertEquals(0, s.size());
    }
    
    /** Tests to ensure that the {@link IDSet#get(Identifiable)} function will return the element from the set. */
    @Test
    public void testGet_Identifiable() {
        final TestIdentifiable a = new TestIdentifiable(1);
        final TestIdentifiable b = new TestIdentifiable(2);
        final TestIdentifiable c = new TestIdentifiable(3);
        final IDSet<TestIdentifiable> s = new IDSet<>(Arrays.asList(a, b, c));
        
        assertNull(s.get((TestIdentifiable)null));
        assertNull(s.get(new TestIdentifiable(null)));
        assertNull(s.get(new TestIdentifiable(4)));
        assertEquals(b, s.get(new TestIdentifiable(2)));
    }
    
    /** Tests to ensure that the {@link IDSet#get(Integer)} function will return the element from the set. */
    @Test
    public void testGet_Integer() {
        final TestIdentifiable a = new TestIdentifiable(1);
        final TestIdentifiable b = new TestIdentifiable(2);
        final TestIdentifiable c = new TestIdentifiable(3);
        final IDSet<TestIdentifiable> s = new IDSet<>(Arrays.asList(a, b, c));
        
        assertNull(s.get((Integer)null));
        assertNull(s.get(Integer.valueOf(4)));
        assertEquals(b, s.get(2));
    }
    
    /** Tests to ensure that the {@link IDSet#remove(Object)} function will remove the same objects that are added. */
    @Test
    @SuppressWarnings("deprecation")
    public void testRemove_Object_Same() {
        final Object a = new TestIdentifiable(3);
        final Object b = new TestIdentifiable(7);
        final Object c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add((TestIdentifiable)a);
        s.add((TestIdentifiable)b);
        s.add((TestIdentifiable)c);
        
        assertFalse(s.remove((Object)null));
        
        assertTrue(s.remove(a));
        assertFalse(s.remove(a));
        
        assertTrue(s.remove(b));
        assertFalse(s.remove(b));
        
        assertTrue(s.remove(c));
        assertFalse(s.remove(c));
    }
    
    /** Tests to ensure that the {@link IDSet#remove(Object)} function will remove equal objects. */
    @Test
    @SuppressWarnings("deprecation")
    public void testRemove_Object_Equal() {
        final Object a = new TestIdentifiable(3);
        final Object b = new TestIdentifiable(7);
        final Object c = new TestIdentifiable(11);
        final Object aE = new TestIdentifiable(3);
        final Object bE = new TestIdentifiable(7);
        final Object cE = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add((TestIdentifiable)a);
        s.add((TestIdentifiable)b);
        s.add((TestIdentifiable)c);
        
        assertFalse(s.remove((Object)null));
        
        assertTrue(s.remove(aE));
        assertFalse(s.remove(aE));
        
        assertTrue(s.remove(bE));
        assertFalse(s.remove(bE));
        
        assertTrue(s.remove(cE));
        assertFalse(s.remove(cE));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#remove(Object)} function will remove {@link Identifiable}s whose
     * {@link Identifiable#getID() ID} is equal a given <code>int</code>.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testRemove_Object_IntegerEqual() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add(new TestIdentifiable(3));
        s.add(new TestIdentifiable(7));
        s.add(new TestIdentifiable(11));
        
        assertFalse(s.remove((Object)null));
        
        assertTrue(s.remove((Object)3));
        assertFalse(s.remove((Object)3));
        
        assertTrue(s.remove((Object)7));
        assertFalse(s.remove((Object)7));
        
        assertTrue(s.remove((Object)11));
        assertFalse(s.remove((Object)11));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#remove(Object)} function will remove {@link Identifiable}s whose
     * {@link Identifiable#getID() ID} is equal a given {@link Number}.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testRemove_Object_NumberEqual() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add(new TestIdentifiable(3));
        s.add(new TestIdentifiable(7));
        s.add(new TestIdentifiable(11));
        
        assertFalse(s.remove((Object)null));
        
        assertTrue(s.remove(3.0f));
        assertFalse(s.remove(3.0f));
        
        assertTrue(s.remove(new BigDecimal("7.0")));
        assertFalse(s.remove(new BigDecimal("7.0")));
        
        assertTrue(s.remove(new Long(11)));
        assertFalse(s.remove(new Long(11)));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#remove(Identifiable)} function removes the {@link Identifiable} whose
     * {@link Identifiable#getID() ID} is the same as the ID of the <code>Identifiable</code> passed to the function.
     */
    @Test
    public void testRemove_Identifiable() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add(a);
        s.add(b);
        s.add(c);
        
        assertFalse(s.remove((TestIdentifiable)null));
        
        assertTrue(s.contains(a));
        assertTrue(s.remove(a));
        assertFalse(s.contains(a));
        assertFalse(s.remove(a));
        
        assertTrue(s.contains(b));
        assertTrue(s.remove(b));
        assertFalse(s.contains(b));
        assertFalse(s.remove(b));
        
        assertTrue(s.contains(c));
        assertTrue(s.remove(c));
        assertFalse(s.contains(c));
        assertFalse(s.remove(c));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#remove(Integer)} function removes the {@link Identifiable} whose
     * {@link Identifiable#getID() ID} is the same as the <code>int</code> passed to the function.
     */
    @Test
    public void testRemove_Integer() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        final TestIdentifiable aR;
        final TestIdentifiable bR;
        final TestIdentifiable cR;
        
        s.add(a);
        s.add(b);
        s.add(c);
        
        assertNull(s.remove((Integer)null));
        
        aR = s.remove(3);
        assertEquals(a, aR);
        assertNull(s.remove(3));
        
        bR = s.remove(7);
        assertEquals(b, bR);
        assertNull(s.remove(7));
        
        cR = s.remove(11);
        assertEquals(c, cR);
        assertNull(s.remove(11));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#contains(Object)} function returns <code>true</code> only when...
     * <ul>
     * <li>passed an {@link Identifiable} whose {@link Identifiable#getID() ID} is the same as one of the
     * <code>Identifiable</code>s stored within the set.</li>
     * <li>passed a {@link Number} whose {@link Number#intValue()} is the same as one of the <code>Identifiable</code>s
     * stored within the set.</li>
     * </ul>
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testContains_Object() {
        final Object a = new TestIdentifiable(3);
        final Object b = new TestIdentifiable(7);
        final Object c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add((TestIdentifiable)a);
        s.add((TestIdentifiable)b);
        s.add((TestIdentifiable)c);
        
        assertTrue(s.contains(a));
        assertTrue(s.contains(b));
        assertTrue(s.contains(c));
        assertTrue(s.contains((Object)new TestIdentifiable(3)));
        assertTrue(s.contains((Object)new TestIdentifiable(7)));
        assertTrue(s.contains((Object)new TestIdentifiable(11)));
        assertTrue(s.contains((Object)3));
        assertTrue(s.contains((Object)7));
        assertTrue(s.contains((Object)11));
        assertTrue(s.contains(3.0f));
        assertTrue(s.contains(7.0f));
        assertTrue(s.contains(11.0f));
        assertTrue(s.contains((Object)new Integer(3)));
        assertTrue(s.contains((Object)new Integer(7)));
        assertTrue(s.contains((Object)new Integer(11)));
        assertTrue(s.contains(new BigDecimal("3")));
        assertTrue(s.contains(new BigDecimal("7")));
        assertTrue(s.contains(new BigDecimal("11")));
        
        assertFalse(s.contains((Object)null));
        assertFalse(s.contains((Object)new TestIdentifiable(-1)));
        assertFalse(s.contains((Object)new TestIdentifiable(0)));
        assertFalse(s.contains((Object)new TestIdentifiable(1)));
        assertFalse(s.contains((Object)new TestIdentifiable(Integer.MIN_VALUE)));
        assertFalse(s.contains((Object)new TestIdentifiable(Integer.MAX_VALUE)));
        assertFalse(s.contains((Object)(-1)));
        assertFalse(s.contains((Object)0));
        assertFalse(s.contains((Object)1));
        assertFalse(s.contains((Object)Integer.MIN_VALUE));
        assertFalse(s.contains((Object)Integer.MAX_VALUE));
        assertFalse(s.contains((-1.0f)));
        assertFalse(s.contains(0.0f));
        assertFalse(s.contains(1.0f));
        assertFalse(s.contains(Float.MIN_VALUE));
        assertFalse(s.contains(Float.MAX_VALUE));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#contains(Identifiable)} function returns <code>true</code> only when passed
     * an {@link Identifiable} whose {@link Identifiable#getID() ID} is the same as one of the <code>Identifiable</code>
     * s stored within the set.
     */
    @Test
    public void testContains_Identifiable() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add(a);
        s.add(b);
        s.add(c);
        
        assertTrue(s.contains(a));
        assertTrue(s.contains(b));
        assertTrue(s.contains(c));
        assertTrue(s.contains(new TestIdentifiable(a.getID())));
        assertTrue(s.contains(new TestIdentifiable(b.getID())));
        assertTrue(s.contains(new TestIdentifiable(c.getID())));
        
        assertFalse(s.contains((TestIdentifiable)null));
        assertFalse(s.contains(new TestIdentifiable(-1)));
        assertFalse(s.contains(new TestIdentifiable(0)));
        assertFalse(s.contains(new TestIdentifiable(1)));
        assertFalse(s.contains(new TestIdentifiable(Integer.MIN_VALUE)));
        assertFalse(s.contains(new TestIdentifiable(Integer.MAX_VALUE)));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#contains(Integer)} function returns <code>true</code> only when passed an
     * <code>int</code> that is used as an {@link Identifiable#getID() ID} by one of the {@link Identifiable}s stored
     * within the set.
     */
    @Test
    public void testContains_Integer() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        s.add(new TestIdentifiable(3));
        s.add(new TestIdentifiable(7));
        s.add(new TestIdentifiable(11));
        
        assertTrue(s.contains(3));
        assertTrue(s.contains(7));
        assertTrue(s.contains(11));
        
        assertFalse(s.contains((Integer)null));
        assertFalse(s.contains(-1));
        assertFalse(s.contains(0));
        assertFalse(s.contains(1));
        assertFalse(s.contains(Integer.MIN_VALUE));
        assertFalse(s.contains(Integer.MAX_VALUE));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#iterator()} function returns an iterator that will function correctly when
     * the set is empty.
     */
    @Test
    public void testIterator_empty() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        final Iterator<TestIdentifiable> it = s.iterator();
        
        assertNotNull(it);
        assertFalse(it.hasNext());
    }
    
    /**
     * Tests to ensure that the {@link IDSet#iterator()} function returns an iterator that will correctly iterate
     * through the set's values.
     */
    @Test
    public void testIterator_NotEmpty() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final ArrayList<TestIdentifiable> itVals = new ArrayList<>();
        final IDSet<TestIdentifiable> s = new IDSet<>();
        final Iterator<TestIdentifiable> it;
        
        s.add(a);
        s.add(b);
        s.add(c);
        it = s.iterator();
        
        while(it.hasNext()) {
            itVals.add(it.next());
        }
        
        assertEquals(3, itVals.size());
        assertTrue(itVals.contains(a));
        assertTrue(itVals.contains(b));
        assertTrue(itVals.contains(c));
    }
    
    /**
     * Tests to ensure that the {@link IDSet#iterator()} function returns an iterator whose {@link Iterator#remove()}
     * function works correctly.
     */
    @Test
    public void testIterator_Remove() {
        final TestIdentifiable a = new TestIdentifiable(3);
        final TestIdentifiable b = new TestIdentifiable(7);
        final TestIdentifiable c = new TestIdentifiable(11);
        final IDSet<TestIdentifiable> s = new IDSet<>();
        final Iterator<TestIdentifiable> it;
        
        s.add(a);
        s.add(b);
        s.add(c);
        assertEquals(3, s.size());
        
        it = s.iterator();
        
        it.next();
        it.remove();
        assertEquals(2, s.size());
        
        it.next();
        it.remove();
        assertEquals(1, s.size());
        
        it.next();
        it.remove();
        assertEquals(0, s.size());
    }
    
    /** Tests to ensure that the {@link IDSet#size()} returns the set's size correctly. */
    @Test
    public void testSize() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        
        assertEquals(0, s.size());
        
        s.add(new TestIdentifiable(3));
        assertEquals(1, s.size());
        
        s.add(new TestIdentifiable(7));
        assertEquals(2, s.size());
        
        s.add(new TestIdentifiable(11));
        assertEquals(3, s.size());
        
        s.clear();
        assertEquals(0, s.size());
        assertEquals("[]", s.toString());
    }
    
    /** Tests to ensure that the {@link IDSet#clear()} function empties the set. */
    @Test
    @SuppressWarnings("unused")
    public void testClear() {
        final IDSet<TestIdentifiable> s = new IDSet<>();
        int count = 0;
        
        s.add(new TestIdentifiable(3));
        s.add(new TestIdentifiable(7));
        s.add(new TestIdentifiable(11));
        
        assertNotEquals(0, s.size());
        assertNotEquals("[]", s.toString());
        s.clear();
        assertEquals(0, s.size());
        
        for(TestIdentifiable i : s) {
            count++;
        }
        
        assertEquals(0, count);
        assertEquals("[]", s.toString());
    }
    
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
        
        @Override
        public String toString() {
            return id == null ? "null" : id.toString();
        }
    }
}
