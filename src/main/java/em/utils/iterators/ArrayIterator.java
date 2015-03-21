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
