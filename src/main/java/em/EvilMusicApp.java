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

package em;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.cli.ParseException;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import em.cli.EMCommandLine;
import em.model.EMPreferences;
import em.model.SongInfo;
import em.prefs.EMPreferencesManager;
import em.utils.EMUtils;
import em.utils.LibraryUtils;

/**
 * @since v0.1
 * @author eviljoe
 */
@ComponentScan
@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
public class EvilMusicApp {
    
    private static final Logger LOG = Logger.getLogger(EvilMusicApp.class.getName());
    
    // General Properties
    private static final String DEFAULT_EM_HOME = System.getProperty("user.home") + "/.evilmusic";
    
    // Spring Boot Properties
    private static final String SPRING_BOOT_SERVER_PORT_PROP = "server.port";
    private static final int DEFAULT_SPRING_BOOT_SERVER_PORT = 8080;
    private static final String SPRING_BOOT_LOG_FILE_PROP = "logging.file";
    private static final String DEFAULT_SPRING_BOOT_LOG_FILE = DEFAULT_EM_HOME + "/evilmusic.log";
    
    // Derby Properties
    private static final String DERBY_HOME_PROP = "derby.system.home";
    private static final String DEFAULT_DERBY_HOME = DEFAULT_EM_HOME + "/db";
    
    // Hibernate Properties
    private static final String HIBERNATE_AUTO_DDL_PROP = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_AUTO_DDL_UPDATE = "update";
    private static final String HIBERNATE_AUTO_DDL_CREATE_DROP = "create-drop";
    private static final String HIBERNATE_DIALECT_PROP = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    
    /* *********** */
    /* Main Method */
    /* *********** */
    
    public static void main(String[] args) throws IOException, URISyntaxException {
        final EMPreferences prefs;
        
        readCommandLineArgs(args);
        prefs = loadEMPreferences();
        configureSpringBoot(prefs);
        configureDerby(prefs);
        
        SpringApplication.run(EvilMusicApp.class, args);
    }
    
    /* ******************************** */
    /* Command Line Arguments Functions */
    /* ******************************** */
    
    private static void readCommandLineArgs(String[] args) {
        try {
            EMCommandLine.parseAndAct(args);
        } catch(ParseException e) {
            LOG.log(Level.SEVERE, "Exception while parsing command line arguments", e);
        }
    }
    
    /* ************************ */
    /* EM Preferences Functions */
    /* ************************ */
    
    private static EMPreferences loadEMPreferences() throws IOException {
        final EMPreferencesManager prefsMgr = EMPreferencesManager.getInstance();
        prefsMgr.loadPreferences();
        return prefsMgr.getPreferences();
    }
    
    /* *********************************** */
    /* Spring Boot Configuration Functions */
    /* *********************************** */
    
    private static void configureSpringBoot(EMPreferences prefs) {
        Integer port = prefs.getServerPort();
        String logFile = prefs.getLogFile();
        
        if(port == null) {
            port = DEFAULT_SPRING_BOOT_SERVER_PORT;
        }
        
        if(logFile == null) {
            logFile = DEFAULT_SPRING_BOOT_LOG_FILE;
        }
        
        System.setProperty(SPRING_BOOT_SERVER_PORT_PROP, port.toString());
        System.setProperty(SPRING_BOOT_LOG_FILE_PROP, logFile);
    }
    
    /* ******************************** */
    /* Database Configuration Functions */
    /* ******************************** */
    
    private static void configureDerby(EMPreferences prefs) throws URISyntaxException {
        String dbHome = prefs.getDatabaseHome();
        
        if(EMUtils.hasValues(dbHome)) {
            final File f = LibraryUtils.convertToFile(dbHome);
            dbHome = f == null ? DEFAULT_DERBY_HOME : f.getPath();
        } else {
            dbHome = DEFAULT_DERBY_HOME;
        }
        
        System.setProperty(DERBY_HOME_PROP, dbHome);
        
    }
    
    @Bean
    public DataSource dataSource() {
        final DataSource ds = new DataSource();
        
        ds.setDefaultAutoCommit(false);
        ds.setUrl("jdbc:derby:em;create=true");
        ds.setDriverClassName(EmbeddedDriver.class.getName());
        
        return ds;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        
        em.setPersistenceUnitName("derby");
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] {SongInfo.class.getPackage().getName()});
        
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalJPAProperties());
        
        return em;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    public Properties additionalJPAProperties() {
        final Properties properties = new Properties();
        
        // Setting this to "create-drop" configure hibernate to drop all created tables when the JVM dies.
        if(EMUtils.toBoolean(EMPreferencesManager.getInstance().getPreferences().getDatabaseRollback())) {
            properties.setProperty(HIBERNATE_AUTO_DDL_PROP, HIBERNATE_AUTO_DDL_CREATE_DROP);
        } else {
            properties.setProperty(HIBERNATE_AUTO_DDL_PROP, HIBERNATE_AUTO_DDL_UPDATE);
        }
        
        properties.setProperty(HIBERNATE_DIALECT_PROP, DerbyTenSevenDialect.class.getName());
        properties.setProperty(HIBERNATE_SHOW_SQL, Boolean.toString(false));
        
        return properties;
    }
}
