package em.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import em.model.Identifiable;

/**
 * A class containing useful utility functions.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class EMUtils {
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private EMUtils() {
        super();
    }
    
    /* ************************* */
    /* General Utility Functions */
    /* ************************* */
    
    /**
     * Checks the given {@link CharSequence} to see if it contains any characters.
     * 
     * @param s The character sequence to be checked. If <code>null</code> or empty, <code>false</code> will be
     *        returned.
     * 
     * @return Returns <code>true</code> if the given character sequence contains at least one character. Returns
     *         <code>false</code> otherwise.
     * 
     * @see CharSequence#length()
     */
    public static boolean hasValues(CharSequence s) {
        return s != null && s.length() > 0;
    }
    
    /**
     * Checks the given collection to see if it contains any elements.
     * 
     * @param c The collection to be checked. If <code>null</code> or empty, <code>false</code> will be returned.
     * 
     * @return Returns <code>true</code> if the given collection contains at least one element. Returns
     *         <code>false</code> otherwise.
     * 
     * @see Collection#size()
     */
    public static boolean hasValues(Collection<?> c) {
        return c != null && c.size() > 0;
    }
    
    /**
     * Checks the given map to see if it contains any entries.
     * 
     * @param m The map to be checked. If <code>null</code> or empty, <code>false</code> will be returned.
     * 
     * @return Returns <code>true</code> if the given map contains at least one entry. Returns <code>false</code>
     *         otherwise.
     * 
     * @see Map#size()
     */
    public static boolean hasValues(Map<?, ?> m) {
        return m != null && m.size() > 0;
    }
    
    /**
     * Converts the given collection to a list. The elements in the returned list will be ordered according to the order
     * of elements returned by the given collection's iterator.
     * 
     * @param c The collection to be converted to a list. If {@code null}, {@code null} will be returned.
     * 
     * @return Returns a list containing the given collection's elements. If the given collection is {@code null},
     *         {@code null} will be returned.
     */
    public static <E> List<E> toList(Collection<E> c) {
        final List<E> l;
        
        if(c == null) {
            l = null;
        } else {
            l = new ArrayList<E>(c.size());
            l.addAll(c);
        }
        
        return l;
    }
    
    /**
     * Adds the given collection to the given set.
     * 
     * @param c The collection to be converted to a set. If {@code null}, {@code null} will always be returned.
     * @param s The set that the collection's elements will be added to. If {@code null}, an exception will be thrown.
     * 
     * @return Returns the set that the elements are to be added to or {@code null} if the given collection was
     *         {@code null}.
     * 
     * @throws NullPointerException Thrown if set that the collection's elements will be added to is {@code null}.
     */
    public static <E, S extends Set<E>> S toSet(Collection<E> c, S s) throws NullPointerException {
        final S rset;
        
        if(c == null) {
            rset = null;
        } else if(s == null) {
            throw new NullPointerException("Cannot add elements to a null set.");
        } else {
            rset = s;
            rset.addAll(c);
        }
        
        return rset;
    }
    
    /**
     * Searches through the given collection to find an {@link Identifiable} whose ID is equal to the given ID.
     * 
     * @param c The collection that is to be searched. If {@code null}, {@code null} will be returned.
     * @param id The ID that is to be searched for. If {@code null}, an {@code Identifiable} with a {@code null} ID will
     *        be searched for.
     * 
     * @return Returns the {@code Identifiable} whose ID is equal to the given ID. If no {@code Identifiable} can be
     *         found, {@code null} will be returned.
     * 
     * @see Identifiable
     */
    public static <I extends Identifiable> I findByID(Collection<I> c, Integer id) {
        I identifiable = null;
        
        if(c != null) {
            final Iterator<I> it = c.iterator();
            
            while(it.hasNext() && identifiable == null) {
                final I i = it.next();
                
                if(i != null && Objects.equals(i.getID(), id)) {
                    identifiable = i;
                }
            }
        }
        
        return identifiable;
    }
    
    /* ******************** */
    /* Comparison Functions */
    /* ******************** */
    
    /**
     * Determines if the two given strings are equal (ignoring case). The following table describes how equality is
     * determined:
     * <p>
     * <table border="1">
     * <tr>
     * <th>{@code a = null}</th>
     * <th>{@code b = null}</th>
     * <th>Equal</th>
     * </tr>
     * <tr>
     * <td>{@code true}</td>
     * <td>{@code true}</td>
     * <td>{@code true}</td>
     * </tr>
     * <tr>
     * <td>{@code true}</td>
     * <td>{@code false}</td>
     * <td>{@code false}</td>
     * </tr>
     * <tr>
     * <td>{@code false}</td>
     * <td>{@code true}</td>
     * <td>{@code false}</td>
     * </tr>
     * <tr>
     * <td>{@code false}</td>
     * <td>{@code false}</td>
     * <td>{@code a.}{@link String#equalsIgnoreCase(String) equalsIgnoreCase}{@code (b)}</td>
     * </tr>
     * </table>
     * <p>
     * 
     * @param a The first string
     * @param b The second string
     * 
     * @return Returns {@code true} if the strings are equal (ignoring case). Returns {@code false} otherwise.
     * 
     * @see String#equalsIgnoreCase(String)
     * @see Objects#equals(Object, Object)
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (a != null && a.equalsIgnoreCase(b));
    }
    
    /**
     * Compares the given two objects. The result of the comparison is as follows:
     * <p>
     * <table border="1">
     * <tr>
     * <th>{@code a == null}</th>
     * <th>{@code b == null}</th>
     * <th>{@code result}</th>
     * </tr>
     * <tr>
     * <td>{@code true}</td>
     * <td>{@code true}</td>
     * <td>{@code 0}</td>
     * </tr>
     * <tr>
     * <td>{@code true}</td>
     * <td>{@code false}</td>
     * <td>{@code 0}</td>
     * </tr>
     * <tr>
     * <td>{@code false}</td>
     * <td>{@code true}</td>
     * <td>{@code 0}</td>
     * </tr>
     * <tr>
     * <td>{@code false}</td>
     * <td>{@code false}</td>
     * <td>{@code a.}{@link Compare#compareTo compareTo}{@code (b)}</td>
     * </tr>
     * </table>
     * 
     * @param a The first object.
     * @param b The seconds object.
     * 
     * @return Returns...
     *         <ul>
     *         <li>a positive number if {@code a} is greater than {@code b}</li> <li>a negative number if {@code a} is
     *         less than {@code b}</li> <li>{@code 0} if {@code a} is equal to {@code b}</li>
     *         </ul>
     * 
     * @see Comparator#compare
     */
    public static <B> int compare(Comparable<B> a, B b) {
        return compare(a, b, null);
    }
    
    /**
     * Compares the two objects based on the given order.
     * 
     * @param a The first object.
     * @param b The second object.
     * @param order The order to be enforced by the comparison. If {@code null},
     *        {@link NullComparator.Order#ASC_NULLS_LAST} will be used.
     * 
     * @return Returns the result of the comparison.
     */
    public static <B> int compare(Comparable<B> a, B b, NullComparator.Order order) {
        final boolean nullA;
        final boolean nullB;
        final int compare;
        boolean reverse;
        boolean nullsLast;
        
        if(order == null) {
            order = NullComparator.Order.ASC_NULLS_LAST;
            reverse = false;
        }
        
        switch(order) {
            case ASC_NULLS_FIRST:
                nullsLast = false;
                reverse = false;
                break;
            case ASC_NULLS_LAST:
                nullsLast = true;
                reverse = false;
                break;
            case DESC_NULLS_FIRST:
                nullsLast = false;
                reverse = true;
                break;
            case DESC_NULLS_LAST:
                nullsLast = true;
                reverse = true;
                break;
            default:
                throw new UnknownSortOrderException("Unknown sort order: " + order);
        }
        
        nullA = a == null;
        nullB = b == null;
        
        if(nullA && nullB) {
            compare = 0;
        } else if(!nullA && nullB) {
            compare = nullsLast ? -1 : 1;
        } else if(nullA && !nullB) {
            compare = nullsLast ? 1 : -1;
        } else {
            compare = reverse ? -(a.compareTo(b)) : a.compareTo(b);
        }
        
        return compare;
    }
    
    /* ********************** */
    /* File Utility Functions */
    /* ********************** */
    
    /**
     * Returns the extension of the given file. The extension is considered to be the characters after the last period
     * (.) that is after the last path separator. The following table shows examples of how the extension is determined.
     * <p>
     * <table border="1">
     * <tr>
     * <th>File</th>
     * <th>Extension</th>
     * </tr>
     * <tr>
     * <td>/home/user/foo.bar</td>
     * <td>bar</td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo.bar</td>
     * <td>bar</td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo.</td>
     * <td></td>
     * </tr>
     * </table>
     * <p>
     * <b>Note:</b> The above examples are using Linux file paths, but this function will work on Windows files too
     * (when run in a Windows JVM).
     * 
     * @param f The file whose extension is to be determined. If <code>null</code>, an empty string will be returned.
     * 
     * @return Returns the given file's extension. This function is guaranteed to never return <code>null</code>. If the
     *         given file is <code>null</code>, an empty string will be returned.
     * 
     * @see #getExtension(String)
     */
    public static String getExtension(File f) {
        return f == null ? "" : getExtension(f.getName());
    }
    
    /**
     * Returns the extension of the given file path. The extension is considered to be the characters after the last
     * period (.) that is after the last path separator. The following table shows examples of how the extension is
     * determined.
     * <p>
     * <table border="1">
     * <tr>
     * <th>File</th>
     * <th>Extension</th>
     * </tr>
     * <tr>
     * <td>/home/user/foo.bar</td>
     * <td>bar</td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo.bar</td>
     * <td>bar</td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo</td>
     * <td></td>
     * </tr>
     * <tr>
     * <td>/home/user.example/foo.</td>
     * <td></td>
     * </tr>
     * </table>
     * <p>
     * <b>Note:</b> The above examples are using Linux file paths, but this function will work on Windows file paths too
     * (when run in a Windows JVM).
     * 
     * @param s The file path whose extension is to be determined. If <code>null</code> or empty, an empty string will
     *        be returned.
     * 
     * @return Returns the given file path's extension. This function is guaranteed to never return <code>null</code>.
     *         If the given file path is <code>null</code>, an empty string will be returned.
     * 
     * @see #getExtension(File)
     */
    public static String getExtension(String s) {
        String ext;
        
        if(!hasValues(s)) {
            ext = "";
        } else {
            final int indexOfLastDot = s.lastIndexOf('.');
            
            if(indexOfLastDot < 0 || indexOfLastDot == s.length() - 1) {
                ext = "";
            } else {
                final int indexOfLastSep = s.lastIndexOf(File.separator);
                
                if(indexOfLastSep > indexOfLastDot) {
                    ext = "";
                } else {
                    ext = s.substring(indexOfLastDot + 1);
                }
            }
        }
        
        return ext;
    }
}
