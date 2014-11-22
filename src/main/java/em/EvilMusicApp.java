package em;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import em.model.SongInfo;
import em.prefs.EMPreferencesManager;
import em.repos.EqualizerRepository;
import em.repos.QueueRepository;
import em.repos.RepoManager;
import em.repos.SongInfoRepository;

/**
 * @since v0.1
 * @author eviljoe
 */
@ComponentScan
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
public class EvilMusicApp {
    
    /* *********** */
    /* Main Method */
    /* *********** */
    
    public static void main(String[] args) throws IOException {
        final ConfigurableApplicationContext context;
        
        loadEMPreferences();
        configureDerby();
        
        context = SpringApplication.run(EvilMusicApp.class, args);
        configureRepoManager(context);
    }
    
    /* *********************** */
    /* Configuration Functions */
    /* *********************** */
    
    private static void configureDerby() {
        System.setProperty("derby.system.home", "/home/joe/derbyhome");
    }
    
    private static void configureRepoManager(ConfigurableApplicationContext context) {
        final RepoManager rmgr = RepoManager.getInstance();
        
        rmgr.setSongInfo(context.getBean(SongInfoRepository.class));
        rmgr.setQueue(context.getBean(QueueRepository.class));
        rmgr.setEqualizer(context.getBean(EqualizerRepository.class));
    }
    
    @Bean
    public DataSource dataSource() {
        final DataSource ds = new DataSource();
        
        ds.setDefaultAutoCommit(false);
        ds.setUrl("jdbc:derby:directory/em;create=true");
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
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", DerbyTenSevenDialect.class.getName());
        
        return properties;
    }
    
    private static void loadEMPreferences() throws IOException {
        EMPreferencesManager.getInstance().loadPreferences();
    }
}
