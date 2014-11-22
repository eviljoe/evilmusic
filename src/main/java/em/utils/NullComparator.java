package em.utils;

import java.util.Comparator;

/**
 * @param <T> The type of objects being compared.
 * 
 * @since v0.1
 * @author eviljoe
 */
public abstract class NullComparator<T> implements Comparator<T> {
    
    private final Order order;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public NullComparator() {
        this(null);
    }
    
    public NullComparator(Order order) {
        super();
        this.order = order == null ? Order.ASC_NULLS_LAST : order;
    }
    
    /* ******************** */
    /* Comparator Functions */
    /* ******************** */
    
    @Override
    @SuppressWarnings("unchecked")
    public int compare(T a, T b) {
        final int compare;
        
        if(a == null || b == null) {
            compare = EMUtils.compare((Comparable<T>)a, b, order);
        } else {
            compare = compareNonNulls(a, b);
        }
        
        return compare;
    }
    
    public abstract int compareNonNulls(T a, T b);
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    public Order getOrder() {
        return order;
    }
    
    /* ********** */
    /* Order Enum */
    /* ********** */
    
    public static enum Order {
        
        /**
         * Specifies that elements should be sorted in ascending order with {@code null} elements at the beginning of
         * the collection.
         */
        ASC_NULLS_FIRST,
        
        /**
         * Specifies that elements should be sorted in ascending order with {@code null} elements at the end of the
         * collection.
         */
        ASC_NULLS_LAST,
        
        /**
         * Specifies that elements should be sorted in descending order with {@code null} elements at the beginning of
         * the collection.
         */
        DESC_NULLS_FIRST,
        
        /**
         * Specifies that elements should be sorted in descending order with {@code null} elements at the end of the
         * collection.
         */
        DESC_NULLS_LAST,
    }
}
