package em.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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
