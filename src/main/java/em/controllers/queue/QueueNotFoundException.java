package em.controllers.queue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @since v0.1
 * @author eviljoe
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Queue not found")
public class QueueNotFoundException extends RuntimeException {
    
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;
    
    public QueueNotFoundException() {
        super();
    }
    
    public QueueNotFoundException(String message) {
        super(message);
    }
    
    public QueueNotFoundException(Throwable cause) {
        super(cause);
    }
    
    public QueueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public QueueNotFoundException(int id, Throwable cause) {
        super(String.format("Could not find queue with ID, %d.", id), cause);
    }
    
    public QueueNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
