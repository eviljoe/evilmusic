package em.controllers.queue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid queue index")
public class InvalidQueueIndexException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public InvalidQueueIndexException() {
        super();
    }
    
    public InvalidQueueIndexException(String message) {
        super(message);
    }
    
    public InvalidQueueIndexException(Throwable cause) {
        super(cause);
    }
    
    public InvalidQueueIndexException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidQueueIndexException(int index) {
        this(index, null);
    }
    
    public InvalidQueueIndexException(int index, Throwable cause) {
        super(String.format("Invalid queue element index: ", index), cause);
    }
    
    public InvalidQueueIndexException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
