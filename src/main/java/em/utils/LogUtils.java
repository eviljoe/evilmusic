package em.utils;

import java.util.logging.Level;
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
    
    public static void restCall(Logger log, String url, RequestMethod method, String msg) {
        log.info(String.format("REST <%s:%s> %s", method == null ? "null" : method.name(), url, msg));
    }
    
    public static void restCall(Logger log, String url, String method, String msg) {
        log.info(String.format("REST <%s:%s> %s", method, url, msg));
    }
    
    public static void error(Logger log, String msg, Object... params) {
        log.log(Level.SEVERE, String.format(msg, params));
    }
    
    public static void exception(Logger log, Throwable e, String msg, Object... params) {
        log.log(Level.SEVERE, String.format(msg, params), e);
    }
}
