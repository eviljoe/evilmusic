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
package em.dao;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import em.utils.LogUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@Aspect
@Component
public class DAOLoggingAspect {
    
    private static final Logger LOG = Logger.getLogger(DAOLoggingAspect.class.getName());
    
    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
    }
    
    @Pointcut("target(em.dao.AbstractDAO)")
    public void anyDAOMethod() {
    }
    
    @Before("anyDAOMethod() && anyPublicMethod()")
    public void logBeforePublicDAOMethod(JoinPoint jp) {
        LogUtils.daoCall(LOG, jp);
    }
}
