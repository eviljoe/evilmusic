package em.test.rest.config;

/**
 * @since v0.1
 * @author eviljoe
 */
public class RESTTestConfigIOException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public RESTTestConfigIOException() {
        super();
    }
    
    public RESTTestConfigIOException(String msg) {
        super(msg);
    }
    
    public RESTTestConfigIOException(Throwable cause) {
        super(cause);
    }
    
    public RESTTestConfigIOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
