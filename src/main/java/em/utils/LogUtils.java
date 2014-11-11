package em.utils;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A utility class containing functions related to logging.
 * 
 * @since v0.1
 * @author eviljoe
 */
public class LogUtils {
    
    /* ************ */
    /* Constructors */
    /* ************ */
    
    /** Private constructor so this class cannot be instantiated or extended. */
    private LogUtils() {
        super();
    }
    
    /* ***************** */
    /* Utility Functions */
    /* ***************** */
    
    public static void createRESTCallEntry(Logger log, String url, RequestMethod method, String msg) {
        log.info(String.format("REST <%s:%s> %s", method.name(), url, msg));
    }
}
