package em.utils;

/**
 * @since v0.1
 * @author eviljoe
 */
public class UnknownSortOrderException extends IllegalArgumentException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public UnknownSortOrderException() {
        super();
    }
    
    public UnknownSortOrderException(String msg) {
        super(msg);
    }
    
    public UnknownSortOrderException(Throwable cause) {
        super(cause);
    }
    
    public UnknownSortOrderException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
