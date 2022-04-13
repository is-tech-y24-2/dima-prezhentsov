package kotiki.dao.session;

import kotiki.dao.model.CatsEntity;
import kotiki.dao.model.FriendsEntity;
import kotiki.dao.model.OwnersEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(CatsEntity.class);
                configuration.addAnnotatedClass(OwnersEntity.class);
                configuration.addAnnotatedClass(FriendsEntity.class);
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(standardServiceRegistryBuilder.build());
            }
            catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        return sessionFactory;
    }
}
