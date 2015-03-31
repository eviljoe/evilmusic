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

package em.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * A class containing unit tests for {@link NullComparator}.
 * 
 * @see NullComparator
 * 
 * @since v0.1
 * @author eviljoe
 */
public class NullComparatorTest {
    
    /* ************** */
    /* Test Functions */
    /* ************** */
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that does not contain {@code null}
     * elements in ascending order.
     */
    @Test
    public void testSortAscNoNulls() {
        final Integer a = 13;
        final Integer b = 17;
        final Integer c = 7;
        final Integer d = 19;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(null));
        
        assertEquals(c, list.get(0));
        assertEquals(e, list.get(1));
        assertEquals(a, list.get(2));
        assertEquals(b, list.get(3));
        assertEquals(d, list.get(4));
    }
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that contains {@code null}
     * elements in ascending order. The {@code null}s should be at the beginning of the sorted collection.
     */
    @Test
    public void testSortAscNullsFirst() {
        final Integer a = 13;
        final Integer b = null;
        final Integer c = 7;
        final Integer d = null;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(NullComparator.Order.ASC_NULLS_FIRST));
        
        assertNull(list.get(0));
        assertNull(list.get(1));
        assertEquals(c, list.get(2));
        assertEquals(e, list.get(3));
        assertEquals(a, list.get(4));
    }
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that contains {@code null}
     * elements in ascending order. The {@code null}s should be at the end of the sorted collection.
     */
    @Test
    public void testSortAscNullsLast() {
        final Integer a = 13;
        final Integer b = null;
        final Integer c = 7;
        final Integer d = null;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(NullComparator.Order.ASC_NULLS_LAST));
        
        assertEquals(c, list.get(0));
        assertEquals(e, list.get(1));
        assertEquals(a, list.get(2));
        assertNull(list.get(3));
        assertNull(list.get(4));
    }
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that does not contain {@code null}
     * elements in descending order.
     */
    @Test
    public void testSortDescNoNulls() {
        final Integer a = 13;
        final Integer b = 17;
        final Integer c = 7;
        final Integer d = 19;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(NullComparator.Order.DESC_NULLS_FIRST));
        
        assertEquals(d, list.get(0));
        assertEquals(b, list.get(1));
        assertEquals(a, list.get(2));
        assertEquals(e, list.get(3));
        assertEquals(c, list.get(4));
    }
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that contains {@code null}
     * elements in descending order. The {@code null}s should be at the beginning of the sorted collection.
     */
    @Test
    public void testSortDescNullsFirst() {
        final Integer a = 13;
        final Integer b = null;
        final Integer c = 7;
        final Integer d = null;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(NullComparator.Order.DESC_NULLS_FIRST));
        
        assertNull(list.get(0));
        assertNull(list.get(1));
        assertEquals(a, list.get(2));
        assertEquals(e, list.get(3));
        assertEquals(c, list.get(4));
    }
    
    /**
     * Tests to ensure that a {@link NullComparator} can be used to sort a collection that contains {@code null}
     * elements in descending order. The {@code null}s should be at the end of the sorted collection.
     */
    @Test
    public void testSortDescNullsLast() {
        final Integer a = 13;
        final Integer b = null;
        final Integer c = 7;
        final Integer d = null;
        final Integer e = 11;
        final List<Integer> list = new ArrayList<>();
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Collections.sort(list, new IntegerNullComparator(NullComparator.Order.DESC_NULLS_LAST));
        
        assertEquals(a, list.get(0));
        assertEquals(e, list.get(1));
        assertEquals(c, list.get(2));
        assertNull(list.get(3));
        assertNull(list.get(4));
    }
    
    /* *************** */
    /* Utility Classes */
    /* *************** */
    
    /**
     * A comparator that can be used to test the functionality of the {@link NullComparator}.
     * 
     * @see NullComparatorTest
     * @see NullComparator
     * 
     * @since v0.1
     * @author eviljoe
     */
    private static class IntegerNullComparator extends NullComparator<Integer> {
        
        public IntegerNullComparator(NullComparator.Order order) {
            super(order);
        }
        
        /** {@inheritDoc} */
        @Override
        public int compareNonNulls(Integer a, Integer b) {
            return EMUtils.compare(a, b, getOrder());
        }
    }
}
