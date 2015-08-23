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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
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
    
    public static void daoCall(Logger log, JoinPoint jp) {
        log.info(String.format("DAO %s %s", jp.getSignature().toShortString(), Arrays.toString(jp.getArgs())));
    }
    
    public static void error(Logger log, String msg, Object... params) {
        log.log(Level.SEVERE, String.format(msg, params));
    }
    
    public static void exception(Logger log, Throwable e) {
        log.log(Level.SEVERE, "", e);
    }
    
    public static void exception(Logger log, Throwable e, String msg, Object... params) {
        log.log(Level.SEVERE, String.format(msg, params), e);
    }
    
}
