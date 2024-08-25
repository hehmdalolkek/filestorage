package ru.hehmdalolkek.filestorage.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.hehmdalolkek.filestorage.model.Event;
import ru.hehmdalolkek.filestorage.model.File;
import ru.hehmdalolkek.filestorage.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class HibernateUtil {

    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName());

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(Event.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating SessionFactory.");
            throw new ExceptionInInitializerError(e);
        }
    }

    private HibernateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

}
