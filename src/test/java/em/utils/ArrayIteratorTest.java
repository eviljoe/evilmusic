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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import em.utils.iterators.ArrayIterator;

/**
 * A class containing unit tests for {@link ArrayIterator}.
 * 
 * @see ArrayIterator
 * 
 * @since v0.1
 * @author eviljoe
 */
public class ArrayIteratorTest {
    
    /**
     * Tests to ensure that the {@link ArrayIterator#hasNext()} function return {@code false} when iterating over a
     * {@code null} array.
     */
    @Test
    public void testHasNext_NullArray() {
        final ArrayIterator<String> it = new ArrayIterator<String>(null);
        assertFalse(it.hasNext());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#hasNext()} function will return {@code false} when iterating over
     * an empty array.
     */
    @Test
    public void testHasNext_EmptyArray() {
        final ArrayIterator<String> it = new ArrayIterator<String>(new String[0]);
        assertFalse(it.hasNext());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#hasNext()} function will return {@true} when there are more
     * elements and {@code false} when there are no more elements.
     */
    @Test
    public void testHasNext_ArrayWithElements() {
        final String[] array = {"a", "b"};
        final ArrayIterator<String> it = new ArrayIterator<String>(array);
        
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#hasNext()} function will not throw an exception when iterating over
     * {@code null} elements.
     */
    @Test
    public void testHasNext_ArrayWithNullElements() {
        final String[] array = {"a", null, "c"};
        final ArrayIterator<String> it = new ArrayIterator<String>(array);
        
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#next()} function will throw a {@code NullPointerException} when
     * iterating over a {@code null} array.
     */
    @Test(expected = NullPointerException.class)
    public void testNext_NullArray() {
        final ArrayIterator<String> it = new ArrayIterator<String>(null);
        it.next();
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#next()} function will throw an
     * {@link ArrayIndexOutOfBoundsException} when called on an empty array.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testNext_EmptyArray() {
        final ArrayIterator<String> it = new ArrayIterator<String>(new String[0]);
        it.next();
    }
    
    /** Tests to ensure that the {@link ArrayIterator#next()} function will return the next element in the array. */
    @Test
    public void testNext_ArrayWithElements() {
        final String[] array = {"a", "b", "c"};
        final ArrayIterator<String> it = new ArrayIterator<String>(array);
        
        assertEquals("a", it.next());
        assertEquals("b", it.next());
        assertEquals("c", it.next());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#next()} function will return {@code null} elements without throwing
     * exception.
     */
    @Test
    public void testNext_ArrayWithNullElements() {
        final String[] array = {"a", null, "c"};
        final ArrayIterator<String> it = new ArrayIterator<String>(array);
        
        assertEquals("a", it.next());
        assertEquals(null, it.next());
        assertEquals("c", it.next());
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#iterator()} function will return an iterator that can iterate over
     * a {@code null} array.
     */
    @Test
    public void testIterator_NullArray() {
        final Iterator<String> it = new ArrayIterator<String>(null).iterator();
        String concatenated = "";
        
        while(it.hasNext()) {
            concatenated += it.next();
        }
        
        assertEquals("", concatenated);
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#iterator()} function will return an iterator that can iterate over
     * an empty array.
     */
    @Test
    public void testIterator_EmptyArray() {
        final String[] array = {};
        final Iterator<String> it = new ArrayIterator<String>(array).iterator();
        String concatenated = "";
        
        while(it.hasNext()) {
            concatenated += it.next();
        }
        
        assertEquals("", concatenated);
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#iterator()} function will return an iterator that can iterate over
     * an array that contains elements.
     */
    @Test
    public void testIterator_ArrayWithElements() {
        final String[] array = {"a", "b", "c"};
        final Iterator<String> it = new ArrayIterator<String>(array).iterator();
        String concatenated = "";
        
        while(it.hasNext()) {
            concatenated += it.next();
        }
        
        assertEquals("abc", concatenated);
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#iterator()} function will return an iterator that can iterate over
     * an array that contains {@code null} elements.
     */
    @Test
    public void testIterator_ArrayWithNullElements() {
        final String[] array = {"a", null, "c"};
        final Iterator<String> it = new ArrayIterator<String>(array).iterator();
        String concatenated = "";
        
        while(it.hasNext()) {
            concatenated += it.next();
        }
        
        assertEquals("anullc", concatenated);
    }
    
    /**
     * Tests to ensure that the {@link ArrayIterator#remove()} function will throw an
     * {@link UnsupportedOperationException}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        final Iterator<String> it = new ArrayIterator<String>(new String[] {"a", "b", "c"}).iterator();
        
        while(it.hasNext()) {
            it.remove();
        }
    }
}
