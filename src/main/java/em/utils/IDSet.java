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

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;

import em.model.Identifiable;

/**
 * A set optimized for storing {@link Identifiable}s.
 * 
 * @param <I> The type of <code>Identifiable</code> to be stored within the set.
 * 
 * @since v0.1
 * @author eviljoe
 * 
 * @see Set
 */
public class IDSet<I extends Identifiable> extends AbstractSet<I> {
    
    private final HashMap<Integer, I> map;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Creates an empty {@link IDSet}. */
    public IDSet() {
        this(null);
    }
    
    /**
     * Creates an {@link IDSet} that will initially contain the values from the given {@link Iterator}.
     * 
     * @param it The <code>Iterator</code> that contains the values that will be added to the set.
     * 
     * @see #addAll(Iterator)
     */
    public IDSet(Iterator<I> it) {
        super();
        
        map = new HashMap<>();
        addAll(it);
    }
    
    /* ***************** */
    /* Getters / Setters */
    /* ***************** */
    
    /**
     * Adds the given {@link Identifiable} to the set. If an <code>Identifiable</code> with the same ID is already
     * contained within the set, the already existing <code>Identifiable</code> will be overwritten.
     * 
     * @param i The element to be added to the set. If <code>null</code> or has a <code>null</code> ID, the element will
     *        not be added and <code>false</code> will be returned.
     * 
     * @return Returns <code>true</code> if the element was added to the set. Returns <code>false</code> otherwise.
     * 
     * @see Identifiable#getID()
     */
    @Override
    public boolean add(I i) {
        boolean added = false;
        
        if(i != null) {
            final Integer id = i.getID();
            
            if(id != null) {
                map.put(id, i);
                added = true;
            }
        }
        
        return added;
    }
    
    /**
     * Adds the elements from the given {@link Iterator} to the set.
     * 
     * @param it The iterator that contains values that should be added to the set.
     * 
     * @return Returns <code>true</code> if the set changed as a result of this call. Returns <code>false</code>
     *         otherwise.
     * 
     * @see #add(Identifiable)
     */
    public boolean addAll(Iterator<I> it) {
        boolean changed = false;
        
        if(it != null) {
            while(it.hasNext()) {
                changed = add(it.next()) && changed;
            }
        }
        
        return changed;
    }
    
    /**
     * Removes the given object from the set. An object will only be removed from the set if it is one of the following
     * types:
     * <ul>
     * <li>{@link Number} - The {@link Identifiable} within the set whose ID equals {@link Number#intValue()} will be
     * removed.</li>
     * <li>{@link Identifiable} - The <code>Identifiable</code> within the set whose ID will equals this
     * <code>Identifiable</code>'s ID will be removed.</li>
     * </ul>
     * 
     * @param o The object to be removed. If <code>null</code>, <code>false</code> will always be returned.
     * 
     * @return Returns <code>true</code> if an element was removed. Returns <code>false</code> otherwise.
     * 
     * @deprecated Deprecated because this function allows you to remove elements of types that are different from the
     *             type specified by the class.
     * 
     * @see #remove(Identifiable)
     * @see #remove(Integer)
     * @see Identifiable#getID()
     */
    @Override
    @Deprecated
    public boolean remove(Object o) {
        boolean removed = false;
        
        if(o != null) {
            if(o instanceof Number) {
                removed = remove(((Number)o).intValue()) != null;
            } else if(o instanceof Identifiable) {
                removed = remove(((Identifiable)o).getID()) != null;
            }
        }
        
        return removed;
    }
    
    /**
     * Removes the {@link Identifiable} contained within the set whose ID is equal to the given
     * <code>Identifiable</code>'s ID.
     * 
     * @param i The <code>Identifiable</code> whose ID will be used to remove a set element. If <code>null</code>,
     *        <code>false</code> will always be returned.
     * 
     * @return If an <code>Identifiable</code> is removed, <code>true</code> is returned. If nothing is removed,
     *         <code>false</code> is returned.
     * 
     * @see #remove(Integer)
     * @see Identifiable#getID()
     */
    public boolean remove(I i) {
        boolean removed = false;
        
        if(i != null) {
            removed = remove(i.getID()) != null;
        }
        
        return removed;
    }
    
    /**
     * Removes the {@link Identifiable} contained within the set whose ID is equal to the given {@link Integer}.
     * 
     * @param id The ID of the <code>Identifiable</code> to be removed. If <code>null</code>, <code>false</code> will
     *        always be returned.
     * 
     * @return If an <code>Identifiable</code> matching the given ID is removed, that <code>Identifiable</code> is
     *         returned. If nothing is removed, <code>false</code> is returned.
     * 
     * @see #remove(Identifiable)
     * @see Identifiable#getID()
     */
    public I remove(Integer id) {
        I removed = null;
        
        if(id != null) {
            removed = map.remove(id);
        }
        
        return removed;
    }
    
    /**
     * Returns whether or not the given object is within the set. Objects of the following types will be considered to
     * be contained within the set:
     * <ul>
     * <li>{@link Number} - The {@link Identifiable} within the set whose ID equals {@link Number#intValue()} will be
     * checked for.</li>
     * <li>{@link Identifiable} - The <code>Identifiable</code> within the set whose ID will equals this
     * <code>Identifiable</code>'s ID will be checked for.</li>
     * </ul>
     * 
     * @param o The object to be checked for. If <code>null</code>, <code>false</code> will always be returned.
     * 
     * @return Returns <code>true</code> if the object is within the set. Returns <code>false</code> otherwise.
     * 
     * @deprecated Deprecated because this function will consider elements of different types to be within the set.
     * 
     * @see #remove(Identifiable)
     * @see #remove(Integer)
     * @see Identifiable#getID()
     */
    @Override
    @Deprecated
    public boolean contains(Object o) {
        boolean contains = false;
        
        if(o != null) {
            if(o instanceof Number) {
                contains = contains(((Number)o).intValue());
            } else if(o instanceof Identifiable) {
                contains = contains(((Identifiable)o).getID());
            }
        }
        
        return contains;
    }
    
    /**
     * Returns whether or not the set contains an {@link Identifiable} whose ID is equal to the ID of the given
     * <code>Identifiable</code>.
     * 
     * @param i The <code>Identifiable</code> that is to be checked for. If <code>null</code>, <code>false</code> will
     *        always be returned.
     * 
     * @return Returns <code>true</code> if the set contains an <code>Identifiable</code> whose ID is equal to the ID of
     *         the given <code>Identifiable</code>. If the given <code>Identifiable</code> is <code>null</code>,
     *         <code>false</code> will always be returned.
     * 
     * @see #contains(Integer)
     * @see Identifiable#getID()
     */
    public boolean contains(I i) {
        return i == null ? false : contains(i.getID());
    }
    
    /**
     * Returns whether or not the set contains an {@link Identifiable} whose ID equals the given {@link Integer}.
     * 
     * @param id The ID of the <code>Identifiable</code> that is to be checked for. If <code>null</code>,
     *        <code>false</code> will always be returned.
     * 
     * @return Returns <code>true</code> if the set contains an <code>Identifiable</code> whose ID is equal to the given
     *         <code>Integer</code>. Returns <code>false</code> otherwise. If the given ID is <code>null</code>,
     *         <code>false</code> will always be returned.
     * 
     * @see #contains(Identifiable)
     * @see Identifiable#getID()
     */
    public boolean contains(Integer id) {
        return id == null ? false : map.containsKey(id);
    }
    
    /** {@inheritDoc} */
    @Override
    public Iterator<I> iterator() {
        return map.values().iterator();
    }
    
    /** {@inheritDoc} */
    @Override
    public int size() {
        return map.size();
    }
    
    /** Removes all elements from the set. */
    @Override
    public void clear() {
        map.clear();
    }
}
