package kotiki.dao.dao;

import kotiki.dao.model.CatsEntity;
import kotiki.dao.session.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;

import java.util.List;

public abstract class AbstractDao<T> implements Dao<T>{
    protected SessionFactory sessionFactory;
    private Class<T> type;

    public AbstractDao(Class<T> type) {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        this.type = type;
    }

    @Override
    public T findById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        T t = session.get(type, id);
        transaction.commit();
        session.close();
        return t;
    }

    @Override
    public List<T> getAll() {
        Session session = sessionFactory.openSession();
        List<T> result = session.createQuery("from " + type.getSimpleName()).list();
        session.close();
        return result;
    }

    @Override
    public void save(T t) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T t) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
        session.close();
    }
}
