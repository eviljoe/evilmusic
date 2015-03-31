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
