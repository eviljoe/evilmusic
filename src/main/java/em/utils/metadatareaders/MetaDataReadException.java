package em.utils.metadatareaders;

/**
 * @since v0.1
 * @author eviljoe
 */
public class MetaDataReadException extends Exception {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    public MetaDataReadException() {
        super();
    }
    
    public MetaDataReadException(String msg) {
        super(msg);
    }
    
    public MetaDataReadException(Throwable cause) {
        super(cause);
    }
    
    public MetaDataReadException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
