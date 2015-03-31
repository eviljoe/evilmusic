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

package em.utils.iterators;

import java.util.Iterator;

/**
 * An {@link Iterator} that can iterate over an {@code int} array.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class IntArrayIterator implements Iterator<Integer>, Iterable<Integer> {
    
    private int index;
    
    /** The array that is being iterated over. */
    private final int[] array;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /**
     * Creates a new {@link IntArrayIterator}.
     * 
     * @param array The array that is to be iterated over.
     */
    public IntArrayIterator(int[] array) {
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
    public Integer next() {
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
        throw new UnsupportedOperationException("The " + IntArrayIterator.class.getName()
                + ".remove() function is not supported");
    }
    
    /* ****************** */
    /* Iterable Functions */
    /* ****************** */
    
    /** {@inheritDoc} */
    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
}
