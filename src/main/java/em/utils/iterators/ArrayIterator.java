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

package em.utils.iterators;

import java.util.Iterator;

/**
 * An {@link Iterator} that can iterate over an array.
 * 
 * @param <E> The type of elements in the array that is being iterated over.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class ArrayIterator<E> implements Iterator<E>, Iterable<E> {
    
    private int index;
    
    /** The array that is being iterated over. */
    private final E[] array;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /**
     * Creates a new {@link ArrayIterator}.
     * 
     * @param array The array that is to be iterated over.
     */
    public ArrayIterator(E[] array) {
        super();
        this.array = array;
        this.index = 0;
    }
    
    /* ****************** */
    /* Iterator Functions */
    /* ****************** */
    
    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {
        return array == null ? false : index < array.length;
    }
    
    /** {@inheritDoc} */
    @Override
    public E next() {
        return array[index++];
    }
    
    /**
     * Not supported. Throws @ UnsupportedOperationException}.
     * 
     * @throws UnsupportedOperationException Thrown every time this function is called because this function is not
     *         supported.
     */
    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The " + ArrayIterator.class.getName()
                + ".remove() function is not supported");
    }
    
    /* ****************** */
    /* Iterable Functions */
    /* ****************** */
    
    /** {@inheritDoc} */
    @Override
    public Iterator<E> iterator() {
        return this;
    }
}
