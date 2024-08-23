package ru.hehmdalolkek.filestorage.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import ru.hehmdalolkek.filestorage.util.HibernateUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class FlywayContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final String PROPERTIES_FILE = "flyway.properties";
        Properties properties = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error flyway migration.");
            throw new ExceptionInInitializerError(e);
        }

        Flyway flyway = Flyway.configure()
                .dataSource(
                        properties.getProperty("flyway.url"),
                        properties.getProperty("flyway.username"),
                        properties.getProperty("flyway.password")
                )
                .locations(properties.getProperty("flyway.locations"))
                .driver(properties.getProperty("flyway.driver"))
                .load();
        flyway.migrate();
    }
}
