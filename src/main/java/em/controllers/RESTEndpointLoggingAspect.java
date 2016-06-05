/*
 * EvilMusic - Web-Based Music Player Copyright (C) 2016 Joe Falascino
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
package em.controllers;

import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@Aspect
@Component
public class RESTEndpointLoggingAspect {
    
    private static final Logger LOG = Logger.getLogger(RESTEndpointLoggingAspect.class.getName());
    
    @Pointcut("execution(* em.controllers..*.*(..))")
    public void anyControllerMethod() {
    }
    
    @Pointcut(value = "@annotation(requestMapping)", argNames = "requestMapping")
    public void isRESTEndpoint(RequestMapping requestMapping) {
    }
    
    @Before(value = "anyControllerMethod() && isRESTEndpoint(requestMapping)", argNames = "requestMapping")
    public void logBeforeEndpoint(JoinPoint jp, RequestMapping requestMapping) {
        final HttpServletRequest req =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        final String[] urls = requestMapping.value();
        final String urlStr;
        
        if(urls == null || urls.length == 0) {
            urlStr = "UNKNOWN_URL";
        } else if(urls.length == 1) {
            urlStr = urls[0];
        } else {
            urlStr = Arrays.toString(urls);
        }
        
        LogUtils.restCall(LOG, urlStr, req.getMethod(), Arrays.toString(jp.getArgs()));
    }
    
    @AfterThrowing(value = "anyControllerMethod() && isRESTEndpoint(requestMapping)", throwing = "throwable",
            argNames = "requestMapping,throwable")
    public void logAfterEndpointThrowable(JoinPoint jp, RequestMapping requestMapping, Throwable throwable) {
        LogUtils.exception(LOG, throwable);
    }
    
}
